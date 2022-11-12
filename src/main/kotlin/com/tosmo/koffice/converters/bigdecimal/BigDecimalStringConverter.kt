package com.tosmo.koffice.converters.bigdecimal

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import java.math.BigDecimal
import kotlin.reflect.KClass

internal object BigDecimalStringConverter : Converter<BigDecimal> {
    
    override val kotlinTypeKey: KClass<BigDecimal>
        get() = BigDecimal::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): BigDecimal {
        return property.numberFormatProperty?.parseBigDecimal(ktCell.stringValue)
            ?: BigDecimal(ktCell.stringValue)
    }
}