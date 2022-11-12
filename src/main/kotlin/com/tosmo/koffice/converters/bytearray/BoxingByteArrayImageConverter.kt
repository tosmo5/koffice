package com.tosmo.koffice.converters.bytearray

import com.tosmo.koffice.converters.Converter
import kotlin.reflect.KClass

internal object BoxingByteArrayImageConverter : Converter<ByteArray> {
    
    override val kotlinTypeKey: KClass<ByteArray>
        get() = ByteArray::class
    
}