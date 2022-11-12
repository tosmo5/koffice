package com.tosmo.koffice.converters.biginteger

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import java.math.BigInteger
import kotlin.reflect.KClass

internal object BigIntegerNumberConverter : Converter<BigInteger> {
    
    override val kotlinTypeKey: KClass<BigInteger>
        get() = BigInteger::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.NUMBER
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): BigInteger {
        return ktCell.stringValue.toBigInteger()
    }
}