package com.tosmo.koffice.converters.double

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object DoubleStringConverter : Converter<Double> {
    
    override val kotlinTypeKey: KClass<Double>
        get() = Double::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): Double {
        return property.numberFormatProperty?.parseBigDecimal(ktCell.stringValue)?.toDouble()
            ?: ktCell.numberValue
    }
    
}