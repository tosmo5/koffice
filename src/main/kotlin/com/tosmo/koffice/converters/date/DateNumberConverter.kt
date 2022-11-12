package com.tosmo.koffice.converters.date

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.GlobalConfiguration
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import org.apache.poi.ss.usermodel.DateUtil
import java.util.*
import kotlin.reflect.KClass

internal object DateNumberConverter : Converter<Date> {
    
    override val kotlinTypeKey: KClass<Date>
        get() = Date::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.NUMBER
    
    override fun convertToKtData(ktCell: KtCell, property: ExcelContentProperty): Date {
        return DateUtil.getJavaDate(
            ktCell.numberValue,
            property.dateTimeFormatProperty?.use1904windowing
                ?: GlobalConfiguration.use1904windowing,
            null
        )
    }
    
}