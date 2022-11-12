package com.tosmo.koffice.proxy

import com.tosmo.koffice.metadata.GlobalConfiguration.autoTrim
import com.tosmo.koffice.metadata.propety.SheetProperty
import org.apache.poi.ss.usermodel.Row
import kotlin.reflect.KClass
import kotlin.reflect.KParameter


class KtRow<T : Any> internal constructor(
    val ktSheet: KtSheet<T>,
    internal val rawRow: Row,
    val kClass: KClass<T>
) {

    constructor(ktSheet: KtSheet<T>, rowIndex: Int, kClass: KClass<T>) : this(
        ktSheet, ktSheet.rawSheet.getRow(rowIndex), kClass
    )

    /**
     * 行坐标
     */
    var rowIndex: Int
        get() = rawRow.rowNum
        set(value) {
            rawRow.rowNum = value
        }

    /**
     * 整行的单元格样式
     */
    var style: KtCellStyle? = rawRow.rowStyle?.let { KtCellStyle(it) }

    /**
     * 以[T]返回一行数据
     */
    @Suppress("UNCHECKED_CAST")
    fun createBean(): T {
        val args = mutableMapOf<KParameter, Any?>()
        val sheetProp = SheetProperty[kClass]
        sheetProp.paramsMap.forEach { (index, param) ->
            val ktCell = KtCell(this, index)
            if (ktCell.data != null) { // 从excel取到的值不为空时
                val converter = SheetProperty.allocConverter(param, ktCell)
                val prop = sheetProp.contentProperty[param]!!
                converter?.convertToKtData(ktCell, prop)?.let {
                    args[param] = if (it is String && autoTrim) it.trim() else it
                } ?: kotlin.run {
                    // 当没有转换器时，需要有默认值
                    require(param.isOptional) { "${param.name}没有转换器又没有默认值" }
                }
            } else { // 从excel取到空值时
                // 需要参数有默认值或可为空
                require(param.isOptional || param.type.isMarkedNullable) {
                    "${param.name}不可为空又没有默认值，但取到了空值"
                }
                if (!param.isOptional) {
                    args[param] = null
                }
            }
        }
        return sheetProp.constructor.callBy(args) as T
    }

    operator fun get(colIx: Int): KtCell {
        return KtCell(this, colIx)
    }

    fun ktCellIterator(): MutableIterator<KtCell> {
        return object : MutableIterator<KtCell> {
            private val iter = rawRow.cellIterator()
            override fun hasNext(): Boolean = iter.hasNext()
            override fun next(): KtCell = KtCell(this@KtRow, iter.next())
            override fun remove() = iter.remove()
        }
    }
}