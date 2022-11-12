package com.tosmo.koffice.converters.date

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import com.tosmo.ktils.DateUtils
import java.util.*
import kotlin.reflect.KClass

internal object DateStringConverter : Converter<Date> {
    
    override val kotlinTypeKey: KClass<Date>
        get() = Date::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Date {
        return DateUtils.parseDate(
            ktCell.stringValue, property.dateTimeFormatProperty?.format ?: ""
        )
    }
    
}