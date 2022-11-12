package com.tosmo.koffice.converters.byte

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object ByteStringConverter : Converter<Byte> {
    
    override val kotlinTypeKey: KClass<Byte>
        get() = Byte::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Byte {
        return property.numberFormatProperty?.parseBigDecimal(ktCell.stringValue)?.toByte()
            ?: ktCell.numberValue.toInt().toByte()
    }
    
}