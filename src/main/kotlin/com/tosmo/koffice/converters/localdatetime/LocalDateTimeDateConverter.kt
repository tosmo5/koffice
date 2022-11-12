package com.tosmo.koffice.converters.localdatetime

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import java.time.LocalDateTime
import kotlin.reflect.KClass

internal object LocalDateTimeDateConverter: Converter<LocalDateTime> {
    
    override val kotlinTypeKey: KClass<LocalDateTime>
        get() = LocalDateTime::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.DATE
}