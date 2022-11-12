package com.tosmo.koffice.proxy

import com.tosmo.koffice.enums.ExcelType
import com.tosmo.koffice.listener.ReadListener
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass

abstract class KtWorkbook(inputStream: InputStream, val type: ExcelType) {
    
    constructor(filePath: String, type: ExcelType = ExcelType.valueOfFileName(filePath)) : this(
        File(filePath), type
    )
    
    constructor(file: File, type: ExcelType = ExcelType.valueOf(file)) : this(
        file.inputStream(), type
    )
    
    /**
     * sheet的实体类集合
     */
    abstract val entities: List<KClass<*>>
    
    internal val rawBook = when (type) {
        ExcelType.XLS -> HSSFWorkbook(inputStream)
        ExcelType.XLSX -> XSSFWorkbook(inputStream)
    }.apply { missingCellPolicy = MissingCellPolicy.CREATE_NULL_AS_BLANK }
    
    internal val evaluator = when (type) {
        ExcelType.XLS -> HSSFFormulaEvaluator(rawBook as HSSFWorkbook)
        ExcelType.XLSX -> XSSFFormulaEvaluator(rawBook as XSSFWorkbook)
    }
    
    /**
     * 判断此工作薄存在[kClass]
     */
    private fun assertHasSheet(kClass: KClass<*>) {
        require(contains(kClass)) { "${kClass}不是${this::class}中的内容" }
    }
    
    /**
     * 根据[kClass]找出工作薄中的sheet并读取其所有的数据，每次读取到数据，都调用一次[readListener]，
     * 以[T]集合的形式返回
     */
    fun <T : Any> read(kClass: KClass<T>, readListener: ReadListener<T>? = null): List<T> {
        return get(kClass).read(readListener)
    }
    
    operator fun <T : Any> get(kClass: KClass<T>): KtSheet<T> {
        assertHasSheet(kClass)
        return KtSheet(this, kClass)
    }
    
    operator fun <T : Any> get(kClass: KClass<T>, rowIx: Int): KtRow<T> {
        return KtRow(get(kClass), rowIx, kClass)
    }
    
    operator fun get(kClass: KClass<*>, rowIx: Int, colIx: Int): KtCell {
        return KtCell(get(kClass, rowIx), colIx)
    }
    
    operator fun contains(kClass: KClass<*>): Boolean {
        return kClass in entities
    }
    
    fun close() {
        return rawBook.close()
    }
    
    fun write(stream: OutputStream) {
        return rawBook.write(stream)
    }
}