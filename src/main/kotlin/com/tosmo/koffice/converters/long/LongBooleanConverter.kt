package com.tosmo.koffice.converters.long

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object LongBooleanConverter: Converter<Long> {
    
    private const val ONE = 1L
    private const val ZERO = 0L
    
    override val kotlinTypeKey: KClass<Long>
        get() = Long::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Long {
        return if (ktCell.booleaValue) ONE else ZERO
    }
}