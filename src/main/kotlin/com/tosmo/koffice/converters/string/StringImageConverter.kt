package com.tosmo.koffice.converters.string

import com.tosmo.koffice.converters.Converter
import kotlin.reflect.KClass

internal object StringImageConverter: Converter<String> {
    
    override val kotlinTypeKey: KClass<String>
        get() = String::class
}