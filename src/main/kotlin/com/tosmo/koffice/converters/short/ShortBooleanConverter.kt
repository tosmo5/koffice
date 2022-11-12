package com.tosmo.koffice.converters.short

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object ShortBooleanConverter : Converter<Short> {
    
    private const val ONE = 1.toShort()
    private const val ZERO = 0.toShort()
    
    override val kotlinTypeKey: KClass<Short>
        get() = Short::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Short {
        return if (ktCell.booleaValue) ONE else ZERO
    }
}