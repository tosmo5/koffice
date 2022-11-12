package com.tosmo.koffice.converters.long

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object LongNumberConverter : Converter<Long> {
    
    override val kotlinTypeKey: KClass<Long>
        get() = Long::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.NUMBER
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Long {
        return ktCell.numberValue.toLong()
    }
    
}