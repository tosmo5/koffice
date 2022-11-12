package com.tosmo.koffice.converters.int

import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object IntBooleanConverter: Converter<Int> {
    override val kotlinTypeKey: KClass<Int>
        get() = Int::class
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Int {
        return if (ktCell.booleaValue) 1 else 0
    }
}