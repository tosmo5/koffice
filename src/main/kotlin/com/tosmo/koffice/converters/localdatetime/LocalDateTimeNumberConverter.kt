package com.tosmo.koffice.converters.localdatetime

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.GlobalConfiguration
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import org.apache.poi.ss.usermodel.DateUtil
import java.time.LocalDateTime
import kotlin.reflect.KClass

internal object LocalDateTimeNumberConverter : Converter<LocalDateTime> {
    
    override val kotlinTypeKey: KClass<LocalDateTime>
        get() = LocalDateTime::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.NUMBER
    
    override fun convertToKtData(
        ktCell: KtCell, property: ExcelContentProperty
    ): LocalDateTime {
        return DateUtil.getLocalDateTime(
            ktCell.numberValue, property.dateTimeFormatProperty?.use1904windowing
                ?: GlobalConfiguration.use1904windowing
        )
    }
}