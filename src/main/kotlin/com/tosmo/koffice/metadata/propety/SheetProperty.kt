package com.tosmo.koffice.metadata.propety

import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.converters.AutoConverter
import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.converters.ConverterKey
import com.tosmo.koffice.converters.DefaultConverterLoader
import com.tosmo.koffice.annotations.*
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

/**
 * sheet的信息
 */
class SheetProperty(val kClass: KClass<*>) {
    
    /**
     * @see Sheet.name
     */
    val sheetName: String
    
    /**
     * @see Sheet.headRowNum
     */
    val headRowNum: Int
    
    /**
     * 适用的构造函数
     */
    val constructor: KFunction<*>
    
    /**
     * 参数及索引映射
     */
    val paramsMap: Map<Int, KParameter>
    
    val contentProperty: Map<KParameter, ExcelContentProperty>
    
    /**
     * @see Sheet.autoTrim
     */
    var autoTrim: Boolean
    
    /**
     * @see Sheet.use1904windowing
     */
    var use1904windowing: Boolean
    
    /**
     * @see Sheet.useScientificFormat
     */
    var useScientificFormat: Boolean
    
    var locale: Locale = Locale.getDefault()
    
    init {
        val sheetAnnotation = kClass.findAnnotation<Sheet>()
        requireNotNull(sheetAnnotation) { "$kClass 未被标记为Sheet" }
        require(sheetAnnotation.headRowNum >= 0) { "${kClass}表头行/列数不可小于0" }
        
        sheetName = sheetAnnotation.name
        headRowNum = sheetAnnotation.headRowNum
        autoTrim = sheetAnnotation.autoTrim
        use1904windowing = sheetAnnotation.use1904windowing
        useScientificFormat = sheetAnnotation.useScientificFormat
        
        constructor = kClass.constructors.filter { it.hasAnnotation<DataBuilder>() }.let {
            if (it.isEmpty()) kClass.primaryConstructor!!
            else it.single()
        }
        
        val params = buildList {
            val waitParams = sortedMapOf<Int, KParameter>()
            constructor.parameters.forEach { p ->
                if (p.hasAnnotation<Ignore>()) {
                    require(p.isOptional) { "${constructor}的${p.name}需要有默认值" }
                } else {
                    p.findAnnotation<Column>()?.let {
                        if (it.index < 0) add(p)
                        else waitParams.put(it.index, p)
                    } ?: add(p)
                }
            }
            waitParams.forEach { (index, param) ->
                add(index, param)
            }
        }
        paramsMap = buildMap {
            for (i in params.indices) {
                put(i, params[i])
            }
        }
        contentProperty = parseContentProp(params)
    }
    
    companion object {
        internal val SHEET_INFO_MAP = mutableMapOf<KClass<*>, SheetProperty>()
        
        fun getSheetInfo(kClass: KClass<*>): SheetProperty {
            return SHEET_INFO_MAP.getOrPut(kClass) {
                SheetProperty(kClass)
            }
        }
        
        operator fun get(kClass: KClass<*>): SheetProperty = getSheetInfo(kClass)
        
        /**
         * 为[param]分配读取转换器
         */
        fun allocConverter(param: KParameter, ktCell: KtCell): Converter<*>? {
            return param.findAnnotation<Column>()?.let { anno ->
                if (anno.readConverter != AutoConverter::class) {
                    anno.readConverter.constructors.find { it.parameters.isEmpty() }.let {
                        requireNotNull(it) { "${anno.readConverter}需要一个无参构造函数" }
                        it.call()
                    }
                } else null
            } ?: run {
                val key = ConverterKey[param.type.jvmErasure, ktCell.type]
                DefaultConverterLoader.defaultReadConverters[key]
            }
        }
    }
    
    private fun parseContentProp(params: Collection<KParameter>): Map<KParameter, ExcelContentProperty> {
        return buildMap {
            params.forEach { param ->
                val dateProp = param.findAnnotation<DateTimeFormat>()?.let {
                    DateTimeFormatProperty(it.parttern, it.use1904windowing)
                }
                val numProp = param.findAnnotation<NumberFormat>()?.let {
                    NumberFormatPorperty(it.pattern, it.roundingMode)
                }
                put(param, ExcelContentProperty(dateProp, numProp))
            }
        }
    }
}

