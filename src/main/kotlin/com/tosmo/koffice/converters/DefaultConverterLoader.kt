package com.tosmo.koffice.converters

import com.tosmo.koffice.converters.bigdecimal.BigDecimalBooleanConverter
import com.tosmo.koffice.converters.bigdecimal.BigDecimalNumberConverter
import com.tosmo.koffice.converters.bigdecimal.BigDecimalStringConverter
import com.tosmo.koffice.converters.biginteger.BigIntegerBooleanConverter
import com.tosmo.koffice.converters.biginteger.BigIntegerNumberConverter
import com.tosmo.koffice.converters.biginteger.BigIntegerStringConverter
import com.tosmo.koffice.converters.boolean.BooleanBooleanConverter
import com.tosmo.koffice.converters.boolean.BooleanNumberConverter
import com.tosmo.koffice.converters.boolean.BooleanStringConverter
import com.tosmo.koffice.converters.byte.ByteBooleanConverter
import com.tosmo.koffice.converters.byte.ByteNumberConverter
import com.tosmo.koffice.converters.byte.ByteStringConverter
import com.tosmo.koffice.converters.date.DateNumberConverter
import com.tosmo.koffice.converters.date.DateStringConverter
import com.tosmo.koffice.converters.double.DoubleBooleanConverter
import com.tosmo.koffice.converters.double.DoubleNumberConverter
import com.tosmo.koffice.converters.double.DoubleStringConverter
import com.tosmo.koffice.converters.float.FloatBooleanConverter
import com.tosmo.koffice.converters.float.FloatNumberConverter
import com.tosmo.koffice.converters.float.FloatStringConverter
import com.tosmo.koffice.converters.int.IntBooleanConverter
import com.tosmo.koffice.converters.int.IntNumberConverter
import com.tosmo.koffice.converters.int.IntStringConverter
import com.tosmo.koffice.converters.long.LongBooleanConverter
import com.tosmo.koffice.converters.long.LongNumberConverter
import com.tosmo.koffice.converters.long.LongStringConverter
import com.tosmo.koffice.converters.short.ShortBooleanConverter
import com.tosmo.koffice.converters.short.ShortNumberConverter
import com.tosmo.koffice.converters.short.ShortStringConverter
import com.tosmo.koffice.converters.string.StringBooleanConverter
import com.tosmo.koffice.converters.string.StringErrorConverter
import com.tosmo.koffice.converters.string.StringNumberConverter
import com.tosmo.koffice.converters.string.StringStringConverter


internal object DefaultConverterLoader {
    private val mDefaultWriteConverter: MutableMap<ConverterKey, Converter<*>> = mutableMapOf()
    private val mAllConverter: MutableMap<ConverterKey, Converter<*>> = mutableMapOf()
    
    val defaultReadConverters: Map<ConverterKey, Converter<*>> = mAllConverter
    
    val allConverters: Map<ConverterKey, Converter<*>> = mAllConverter
    
    init {
//        initDefaultWriteConverter()
        initAllConverter()
    }
    
    private fun initAllConverter() {
        putAllConverter(BigDecimalBooleanConverter)
        putAllConverter(BigDecimalNumberConverter)
        putAllConverter(BigDecimalStringConverter)
        putAllConverter(BigIntegerBooleanConverter)
        putAllConverter(BigIntegerNumberConverter)
        putAllConverter(BigIntegerStringConverter)
        putAllConverter(BooleanBooleanConverter)
        putAllConverter(BooleanNumberConverter)
        putAllConverter(BooleanStringConverter)
        putAllConverter(ByteBooleanConverter)
        putAllConverter(ByteNumberConverter)
        putAllConverter(ByteStringConverter)
        putAllConverter(DateNumberConverter)
        putAllConverter(DateStringConverter)
//        putAllConverter(LocalDateNumberConverter())
//        putAllConverter(LocalDateTimeStringConverter())
        putAllConverter(DoubleBooleanConverter)
        putAllConverter(DoubleNumberConverter)
        putAllConverter(DoubleStringConverter)
        putAllConverter(FloatBooleanConverter)
        putAllConverter(FloatNumberConverter)
        putAllConverter(FloatStringConverter)
//        putAllConverter(IntegerBooleanConverter())
//        putAllConverter(IntegerNumberConverter())
//        putAllConverter(IntegerStringConverter())
        putAllConverter(IntBooleanConverter)
        putAllConverter(IntNumberConverter)
        putAllConverter(IntStringConverter)
        putAllConverter(LongBooleanConverter)
        putAllConverter(LongNumberConverter)
        putAllConverter(LongStringConverter)
        putAllConverter(ShortBooleanConverter)
        putAllConverter(ShortNumberConverter)
        putAllConverter(ShortStringConverter)
        putAllConverter(StringBooleanConverter)
        putAllConverter(StringNumberConverter)
        putAllConverter(StringStringConverter)
        putAllConverter(StringErrorConverter)
        putAllConverter(BigIntegerStringConverter)
    }
    
    private fun putWriteStringConverter(converter: Converter<*>) {
        mDefaultWriteConverter[ConverterKey[converter]] = converter
    }
    
    /**
     * Load default read converter
     *
     * @return
     */
    fun loadDefaultReadConverter(): Map<ConverterKey, Converter<*>> {
        return loadAllConverter()
    }
    
    /**
     * Load all converter
     *
     * @return
     */
    fun loadAllConverter(): Map<ConverterKey, Converter<*>> {
        return mAllConverter
    }
    
    private fun putAllConverter(converter: Converter<*>) {
        mAllConverter[ConverterKey[converter]] = converter
    }
}
