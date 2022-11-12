package com.tosmo.koffice.metadata.format

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.*
import java.util.*
import kotlin.math.abs
import kotlin.math.floor


internal class ExcelGeneralNumberFormat(locale: Locale, useScientificFormat: Boolean) :
    Format() {
    private val decimalSymbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance(locale)
    private val integerFormat: DecimalFormat = DecimalFormat("#", decimalSymbols)
    private val decimalFormat: DecimalFormat = DecimalFormat("#.##########", decimalSymbols)
    private var scientificFormat: DecimalFormat = if (useScientificFormat) {
        DecimalFormat("0.#####E0", decimalSymbols)
    } else {
        DecimalFormat("#", decimalSymbols)
    }

    init {
        org.apache.poi.ss.usermodel.DataFormatter.setExcelStyleRoundingMode(scientificFormat)
        org.apache.poi.ss.usermodel.DataFormatter.setExcelStyleRoundingMode(integerFormat)
        DataFormatter.setExcelStyleRoundingMode(decimalFormat)
    }

    override fun format(
        number: Any?,
        toAppendTo: StringBuffer?,
        pos: FieldPosition?
    ): StringBuffer {
        val value: Double
        if (number is Number) {
            value = number.toDouble()
            if (value.isInfinite() || value.isNaN()) {
                return integerFormat.format(number, toAppendTo, pos)
            }
        } else {
            // testBug54786 gets here with a date, so retain previous behaviour
            return integerFormat.format(number, toAppendTo, pos)
        }
        val abs = abs(value)
        if (abs >= 1E11 || abs <= 1E-10 && abs > 0) {
            return scientificFormat.format(number, toAppendTo, pos)
        } else if (floor(value) == value || abs >= 1E10) {
            // integer, or integer portion uses all 11 allowed digits
            return integerFormat.format(number, toAppendTo, pos)
        }
        // Non-integers of non-scientific magnitude are formatted as "up to 11
        // numeric characters, with the decimal point counting as a numeric
        // character". We know there is a decimal point, so limit to 10 digits.
        // https://support.microsoft.com/en-us/kb/65903
        val rounded: Double = BigDecimal(value).round(TO_10_SF).toDouble()
        return decimalFormat.format(rounded, toAppendTo, pos)
    }

    override fun parseObject(source: String?, pos: ParsePosition?): Any {
        throw UnsupportedOperationException()
    }

    companion object {
        private const val serialVersionUID = 1L
        private val TO_10_SF: MathContext = MathContext(10, RoundingMode.HALF_UP)
    }
}
