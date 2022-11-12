package com.tosmo.koffice.converters.date

import com.tosmo.koffice.converters.Converter
import java.util.Date
import kotlin.reflect.KClass

internal object DateDateConverter: Converter<Date> {
    
    override val kotlinTypeKey: KClass<Date>
        get() = Date::class
    
}