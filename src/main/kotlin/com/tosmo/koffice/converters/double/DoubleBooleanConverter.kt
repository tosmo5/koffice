package com.tosmo.koffice.converters.double

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object DoubleBooleanConverter: Converter<Double> {
    
    private const val ONE = 1.0
    private const val ZERE = 0.0
    
    override val kotlinTypeKey: KClass<Double>
        get() = Double::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): Double {
        return if (ktCell.booleaValue) ONE else ZERE
    }
    
}