package com.tosmo.koffice.converters.int

import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object IntStringConverter : Converter<Int> {
    override val kotlinTypeKey: KClass<Int>
        get() = Int::class
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Int {
        return ktCell.numberValue.toInt()
    }
}