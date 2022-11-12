package com.tosmo.koffice.converters.string

import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object StringStringConverter : Converter<String> {
    override val kotlinTypeKey: KClass<String>
        get() = String::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): String {
        return ktCell.stringValue
    }
}