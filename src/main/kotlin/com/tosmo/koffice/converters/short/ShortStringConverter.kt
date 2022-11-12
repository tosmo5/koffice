package com.tosmo.koffice.converters.short

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object ShortStringConverter : Converter<Short> {
    
    override val kotlinTypeKey: KClass<Short>
        get() = Short::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Short {
        return property.numberFormatProperty?.parseBigDecimal(ktCell.stringValue)?.toShort()
            ?: ktCell.numberValue.toInt().toShort()
    }
    
}