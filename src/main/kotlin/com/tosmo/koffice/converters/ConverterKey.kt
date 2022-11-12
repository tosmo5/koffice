package com.tosmo.koffice.converters

import com.tosmo.koffice.enums.CellDataType
import kotlin.reflect.KClass

internal data class ConverterKey(
    /**
     * @see Converter.kotlinTypeKey
     */
    val kotlinTypeKey: KClass<*>,
    /**
     * @see Converter.excelTypeKey
     */
    val excelTypeKey: CellDataType
) {
    companion object {
        private val KEY_MAP = mutableMapOf<KClass<*>, MutableMap<CellDataType, ConverterKey>>()
        
        operator fun get(kClass: KClass<*>, cellDataType: CellDataType): ConverterKey {
            return KEY_MAP.getOrPut(kClass) { mutableMapOf() }.getOrPut(cellDataType) {
                ConverterKey(kClass, cellDataType)
            }
        }
        
        operator fun get(converter: Converter<*>): ConverterKey {
            return get(converter.kotlinTypeKey, converter.excelTypeKey)
        }
    }
}
