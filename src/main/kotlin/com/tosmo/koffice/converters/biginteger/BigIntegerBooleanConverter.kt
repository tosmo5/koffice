package com.tosmo.koffice.converters.biginteger

import com.tosmo.koffice.converters.Converter
import com.tosmo.koffice.enums.CellDataType
import com.tosmo.koffice.proxy.KtCell
import com.tosmo.koffice.metadata.propety.ExcelContentProperty
import java.math.BigInteger
import kotlin.reflect.KClass

internal object BigIntegerBooleanConverter : Converter<BigInteger> {
    
    override val kotlinTypeKey: KClass<BigInteger>
        get() = BigInteger::class
    
    override val excelTypeKey: CellDataType
        get() = CellDataType.BOOLEAN
    
    override fun convertToKtData(
        ktCell: KtCell,
        property: ExcelContentProperty
    ): BigInteger {
        return if (ktCell.booleaValue) BigInteger.ONE else BigInteger.ZERO
    }
    
}