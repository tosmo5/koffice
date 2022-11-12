package com.tosmo.koffice.converters

import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

interface Converter<T : Any> {
    val kotlinTypeKey: KClass<T>
        get() {
            throw UnsupportedOperationException("不支持的操作")
        }
    
    val excelTypeKey: CellDataType
        get() {
            throw UnsupportedOperationException("不支持的操作")
        }
    
    fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): T {
        throw UnsupportedOperationException("不支持的操作")
    }
}
