package com.tosmo.koffice.converters.localdatetime

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.metadata.GlobalConfiguration
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import com.tosmo.ktils.DateUtils
import java.time.LocalDateTime
import kotlin.reflect.KClass

internal object LocalDateTimeStringConvter : Converter<LocalDateTime> {
    
    override val kotlinTypeKey: KClass<LocalDateTime>
        get() = LocalDateTime::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.STRING
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): LocalDateTime {
        return DateUtils.parseLocalDateTime(
            ktCell.stringValue, property.dateTimeFormatProperty?.format ?: "",
            GlobalConfiguration.locale
        )
    }
}