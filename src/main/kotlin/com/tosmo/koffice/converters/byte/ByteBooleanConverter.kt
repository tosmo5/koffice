package com.tosmo.koffice.converters.byte

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object ByteBooleanConverter : Converter<Byte> {
    
    private const val ONE: Byte = 1
    private const val ZERO: Byte = 0
    
    override val kotlinTypeKey: KClass<Byte>
        get() = Byte::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Byte {
        return if (ktCell.booleaValue) ONE else ZERO
    }
    
}