package com.tosmo.koffice.metadata.propety

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


data class NumberFormatPorperty(
    var pattern: String,
    /**
     * @see RoundingMode
     */
    var roundingMode: RoundingMode
) {
    fun parseBigDecimal(string: String): BigDecimal {
        return if (pattern.isEmpty()) {
            BigDecimal(string)
        } else {
            val decimalFormat = DecimalFormat(pattern)
            decimalFormat.roundingMode = roundingMode
            decimalFormat.isParseBigDecimal = true
            BigDecimal("${decimalFormat.parse(string)}")
        }
    }
    
    fun format(number: Number): String {
        return if (pattern.isEmpty())
            number.toString()
        else DecimalFormat(pattern).let {
            it.roundingMode = roundingMode
            it.format(number)
        }
    }
}