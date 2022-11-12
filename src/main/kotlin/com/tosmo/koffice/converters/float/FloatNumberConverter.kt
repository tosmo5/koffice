package com.tosmo.koffice.converters.float

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import kotlin.reflect.KClass

internal object FloatNumberConverter : Converter<Float> {
    
    override val kotlinTypeKey: KClass<Float>
        get() = Float::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.NUMBER
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Float {
        return ktCell.numberValue.toFloat()
    }
    
}