package com.tosmo.koffice.converters.boolean

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object BooleanBooleanConverter: Converter<Boolean> {
    
    override val kotlinTypeKey: KClass<Boolean>
        get() = Boolean::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): Boolean {
        return ktCell.booleaValue
    }
    
}