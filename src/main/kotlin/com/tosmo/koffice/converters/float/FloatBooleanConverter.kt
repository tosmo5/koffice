package com.tosmo.koffice.converters.float

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object FloatBooleanConverter: Converter<Float> {
    
    private const val ONE = 1f
    private const val ZERO = 0f
    
    override val kotlinTypeKey: KClass<Float>
        get() = Float::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Float {
        return if (ktCell.booleaValue) ONE else ZERO
    }
    
}