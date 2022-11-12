package com.tosmo.koffice.converters.file

import com.tosmo.koffice.converters.Converter
import java.io.File
import kotlin.reflect.KClass

internal object FileImageConverter: Converter<File> {
    
    override val kotlinTypeKey: KClass<File>
        get() = File::class
}