package com.tosmo.koffice.metadata.format

import com.tosmo.ktils.DateUtils
import org.apache.poi.ss.format.CellFormat
import org.apache.poi.ss.format.CellFormatResult
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter
import org.apache.poi.ss.usermodel.FractionFormat
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.max


internal class DataFormatter(
    
    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * default is false
     */
    var use1904windowing: Boolean = false,
    
    /**
     * stores the locale valid it the last formatting call
     */
    var locale: Locale = Locale.getDefault(),
    /**
     * Whether to use scientific Format.
     *
     * default is false
     */
    useScientificFormat: Boolean = false
) {
    
    /**
     * The decimal symbols of the locale used for formatting values.
     */
    var decimalSymbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance(locale)
    
    /**
     * The date symbols of the locale used for formatting values.
     */
    var dateSymbols: DateFormatSymbols = DateFormatSymbols.getInstance(locale)
    
    /**
     * A default format to use when a number pattern cannot be parsed.
     */
    var defaultNumFormat: Format? = null
    
    /**
     * A map to cache formats. Map<String></String>,Format> formats
     */
    val formats: MutableMap<String, Format> = mutableMapOf()
    
    private fun getFormat(data: Double, dataFormat: Int, dataFormatString: String): Format {
        
        // Might be better to separate out the n p and z formats, falling back to p when n and z are not set.
        // That however would require other code to be re factored.
        // String[] formatBits = formatStrIn.split(";");
        // int i = cellValue > 0.0 ? 0 : cellValue < 0.0 ? 1 : 2;
        // String formatStr = (i < formatBits.length) ? formatBits[i] : formatBits[0];
        
        // Excel supports 2+ part conditional data formats, e.g. positive/negative/zero,
        //  or (>1000),(>0),(0),(negative). As Java doesn't handle these kinds
        //  of different formats for different ranges, just +ve/-ve, we need to
        //  handle these ourselves in a special way.
        // For now, if we detect 2+ parts, we call out to CellFormat to handle it
        // to do Going forward, we should really merge the logic between the two classes
        if (dataFormatString.contains(";") &&
            (dataFormatString.indexOf(';') != dataFormatString.lastIndexOf(';')
                    || rangeConditionalPattern.matcher(dataFormatString).matches())
        ) {
            try {
                // Ask CellFormat to get a formatter for it
                val cfmt = CellFormat.getInstance(locale, dataFormatString)
                // CellFormat requires callers to identify date vs not, so do so
                var cellValueO: Any? = data
                if (DateUtil.isADateFormat(
                        dataFormat, dataFormatString
                    ) &&  // don't try to handle Date value 0, let a 3 or 4-part format take care of it
                    data != 0.0
                ) {
                    cellValueO = DateUtil.getJavaDate(data, use1904windowing)
                }
                // Wrap and return (non-cachable - CellFormat does that)
                return CellFormatResultWrapper(cfmt.apply(cellValueO))
            } catch (e: Exception) {
                println("at ${this::class} Formatting failed for format $dataFormatString, falling back, $e")
            }
        }
        
        // See if we already have it cached
        var format: Format? = formats[dataFormatString]
        if (format != null) {
            return format
        }
        
        // Is it one of the special built in types, General or @?
        if ("General".equals(dataFormatString, ignoreCase = true) || "@" == dataFormatString) {
            format = defaultFormat
            addFormat(dataFormatString, format)
            return format
        }
        
        // Build a formatter, and cache it
        format = createFormat(dataFormat, dataFormatString)
        addFormat(dataFormatString, format)
        return format
    }
    
    private fun createFormat(dataFormat: Int, dataFormatString: String): Format {
        var formatStr: String = dataFormatString
        val format: Format? = checkSpecialConverter(formatStr)
        if (format != null) {
            return format
        }
        
        // Remove colour formatting if present
        var colourM: Matcher = colorPattern.matcher(formatStr)
        while (colourM.find()) {
            val colour: String = colourM.group()
            
            // Paranoid replacement...
            val at = formatStr.indexOf(colour)
            if (at == -1) {
                break
            }
            val nFormatStr = formatStr.substring(0, at) + formatStr.substring(at + colour.length)
            if (nFormatStr == formatStr) {
                break
            }
            
            // Try again in case there's multiple
            formatStr = nFormatStr
            colourM = colorPattern.matcher(formatStr)
        }
        
        // Strip off the locale information, we use an instance-wide locale for everything
        var m: Matcher = localePatternGroup.matcher(formatStr)
        while (m.find()) {
            val match: String = m.group()
            var symbol = match.substring(match.indexOf('$') + 1, match.indexOf('-'))
            if (symbol.indexOf('$') > -1) {
                symbol = symbol.substring(0, symbol.indexOf('$')) + '\\' + symbol.substring(
                    symbol.indexOf('$')
                )
            }
            formatStr = m.replaceAll(symbol)
            m = localePatternGroup.matcher(formatStr)
        }
        
        // Check for special cases
        if (formatStr.trim { it <= ' ' }.isEmpty()) {
            return defaultFormat
        }
        if ("General".equals(formatStr, ignoreCase = true) || "@" == formatStr) {
            return defaultFormat
        }
        if (DateUtils.isADateFormat(dataFormat, formatStr)) {
            return createDateFormat(formatStr)
        }
        // Excel supports fractions in format strings, which Java doesn't
        if (formatStr.contains("#/") || formatStr.contains("?/")) {
            val chunks = formatStr.split(";").toTypedArray()
            for (chunk1: String in chunks) {
                var chunk = chunk1.replace("\\?".toRegex(), "#")
                val matcher: Matcher = fractionStripper.matcher(chunk)
                chunk = matcher.replaceAll(" ")
                chunk = chunk.replace(" +".toRegex(), " ")
                val fractionMatcher: Matcher = fractionPattern.matcher(chunk)
                // take the first match
                if (fractionMatcher.find()) {
                    val wholePart =
                        if (fractionMatcher.group(1) == null) "" else defaultFractionWholePartFormat
                    return FractionFormat(wholePart, fractionMatcher.group(3))
                }
            }
            
            // Strip custom text in quotes and escaped characters for now as it can cause performance problems in
            // fractions.
            // String strippedFormatStr = formatStr.replaceAll("\\\\ ", " ").replaceAll("\\\\.",
            // "").replaceAll("\"[^\"]*\"", " ").replaceAll("\\?", "#");
            return FractionFormat(defaultFractionWholePartFormat, defaultFractionFractionPartFormat)
        }
        return if (numPattern.matcher(formatStr).find()) {
            createNumberFormat(formatStr)
        } else defaultFormat
    }
    
    private fun checkSpecialConverter(dataFormatString: String?): Format? {
        if ("00000\\-0000" == dataFormatString || "00000-0000" == dataFormatString) {
            return ZipPlusFourFormat()
        }
        if ("[<=9999999]###\\-####;\\(###\\)\\ ###\\-####" == dataFormatString || "[<=9999999]###-####;(###) ###-####" == dataFormatString || "###\\-####;\\(###\\)\\ ###\\-####" == dataFormatString || "###-####;(###) ###-####" == dataFormatString) {
            return PhoneFormat()
        }
        return if (("000\\-00\\-0000" == dataFormatString) || ("000-00-0000" == dataFormatString)) {
            SSNFormat()
        } else null
    }
    
    private fun createDateFormat(pFormatStr: String): Format {
        var formatStr = pFormatStr
        formatStr = formatStr.replace("\\\\-".toRegex(), "-")
        formatStr = formatStr.replace("\\\\,".toRegex(), ",")
        formatStr = formatStr.replace("\\\\\\.".toRegex(), ".") // . is a special regexp char
        formatStr = formatStr.replace("\\\\ ".toRegex(), " ")
        formatStr = formatStr.replace("\\\\/".toRegex(), "/") // weird: m\\/d\\/yyyy
        formatStr = formatStr.replace(";@".toRegex(), "")
        // "/" is escaped for no reason in: mm"/"dd"/"yyyy
        formatStr = formatStr.replace("\"/\"".toRegex(), "/")
        // replace Excel quoting with Java style quoting
        formatStr = formatStr.replace("\"\"", "'")
        // Quote the T is iso8601 style dates
        formatStr = formatStr.replace("\\\\T".toRegex(), "'T'")
        formatStr = formatStr.replace("\"", "")
        var hasAmPm = false
        var amPmMatcher: Matcher = amPmPattern.matcher(formatStr)
        while (amPmMatcher.find()) {
            formatStr = amPmMatcher.replaceAll("@")
            hasAmPm = true
            amPmMatcher = amPmPattern.matcher(formatStr)
        }
        formatStr = formatStr.replace("@".toRegex(), "a")
        val dateMatcher: Matcher = daysAsText.matcher(formatStr)
        if (dateMatcher.find()) {
            val match: String =
                (dateMatcher.group(0) ?: "").uppercase(Locale.ROOT).replace("D".toRegex(), "E")
            formatStr = dateMatcher.replaceAll(match)
        }
        
        // Convert excel date format to SimpleDateFormat.
        // Excel uses lower and upper case 'm' for both minutes and months.
        // From Excel help:
        /*
            The "m" or "mm" code must appear immediately after the "h" or"hh"
            code or immediately before the "ss" code; otherwise, Microsoft
            Excel displays the month instead of minutes."
          */
        val sb = StringBuilder()
        val chars = formatStr.toCharArray()
        var mIsMonth = true
        val ms: MutableList<Int> = ArrayList()
        var isElapsed = false
        var j = 0
        while (j < chars.size) {
            var c = chars[j]
            if (c == '\'') {
                sb.append(c)
                j++
                
                // skip until the next quote
                while (j < chars.size) {
                    c = chars[j]
                    sb.append(c)
                    if (c == '\'') {
                        break
                    }
                    j++
                }
            } else if (c == '[' && !isElapsed) {
                isElapsed = true
                mIsMonth = false
                sb.append(c)
            } else if (c == ']' && isElapsed) {
                isElapsed = false
                sb.append(c)
            } else if (isElapsed) {
                when (c) {
                    'h', 'H' -> sb.append('H')
                    'm', 'M' -> sb.append('m')
                    's', 'S' -> sb.append('s')
                    else -> sb.append(c)
                }
            } else if (c == 'h' || c == 'H') {
                mIsMonth = false
                if (hasAmPm) {
                    sb.append('h')
                } else {
                    sb.append('H')
                }
            } else if (c == 'm' || c == 'M') {
                if (mIsMonth) {
                    sb.append('M')
                    ms.add(Integer.valueOf(sb.length - 1))
                } else {
                    sb.append('m')
                }
            } else if (c == 's' || c == 'S') {
                sb.append('s')
                // if 'M' precedes 's' it should be minutes ('m')
                for (index: Int in ms) {
                    if (sb[index] == 'M') {
                        sb.replace(index, index + 1, "m")
                    }
                }
                mIsMonth = true
                ms.clear()
            } else if (Character.isLetter(c)) {
                mIsMonth = true
                ms.clear()
                when (c) {
                    'y', 'Y' -> sb.append('y')
                    'd', 'D' -> sb.append('d')
                    else -> sb.append(c)
                }
            } else {
                if (Character.isWhitespace(c)) {
                    ms.clear()
                }
                sb.append(c)
            }
            j++
        }
        formatStr = sb.toString()
        return try {
            ExcelStyleDateFormatter(formatStr, dateSymbols)
        } catch (iae: IllegalArgumentException) {
            // the pattern could not be parsed correctly,
            // so fall back to the default number format
            defaultFormat
        }
    }
    
    private fun cleanFormatForNumber(formatStr: String): String {
        val sb = StringBuilder(formatStr)
        // If they requested spacers, with "_",
        // remove those as we don't do spacing
        // If they requested full-column-width
        // padding, with "*", remove those too
        run {
            var i = 0
            while (i < sb.length) {
                val c: Char = sb[i]
                if (c == '_' || c == '*') {
                    if (i > 0 && sb[(i - 1)] == '\\') {
                        // It's escaped, don't worry
                        i++
                        continue
                    }
                    if (i < sb.length - 1) {
                        // Remove the character we're supposed
                        // to match the space of / pad to the
                        // column width with
                        sb.deleteCharAt(i + 1)
                    }
                    // Remove the _ too
                    sb.deleteCharAt(i)
                    i--
                }
                i++
            }
        }
        
        // Now, handle the other aspects like
        // quoting and scientific notation
        var i = 0
        while (i < sb.length) {
            val c = sb[i]
            // remove quotes and back slashes
            if (c == '\\' || c == '"') {
                sb.deleteCharAt(i)
                i--
                
                // for scientific/engineering notation
            } else if (c == '+' && i > 0 && sb[i - 1] == 'E') {
                sb.deleteCharAt(i)
                i--
            }
            i++
        }
        return sb.toString()
    }
    
    private class InternalDecimalFormatWithScale(pattern: String, symbols: DecimalFormatSymbols?) :
        Format() {
        private var divider: BigDecimal? = null
        private val df: DecimalFormat = DecimalFormat(trimTrailingCommas(pattern), symbols)
        
        init {
            setExcelStyleRoundingMode(df)
            val endsWithCommasMatcher: Matcher = endsWithCommas.matcher(pattern)
            if (endsWithCommasMatcher.find()) {
                val commas: String = endsWithCommasMatcher.group(1) ?: ""
                var temp: BigDecimal = BigDecimal.ONE
                repeat(commas.length) {
                    temp = temp.multiply(ONE_THOUSAND)
                }
                divider = temp
            } else {
                divider = null
            }
        }
        
        private fun scaleInput(obj: Any): Any {
            return divider?.let {
                when (obj) {
                    is BigDecimal -> obj.divide(divider, RoundingMode.HALF_UP)
                    is Double -> obj / divider!!.toDouble()
                    else -> throw UnsupportedOperationException()
                }
            } ?: obj
        }
        
        override fun format(
            obj: Any,
            toAppendTo: StringBuffer?,
            pos: FieldPosition?
        ): StringBuffer {
            return df.format(scaleInput(obj), toAppendTo, pos)
        }
        
        override fun parseObject(source: String?, pos: ParsePosition?): Any {
            throw UnsupportedOperationException()
        }
        
        companion object {
            private val endsWithCommas: Pattern = Pattern.compile("(,+)$")
            private val ONE_THOUSAND: BigDecimal = BigDecimal(1000)
            private fun trimTrailingCommas(s: String): String {
                return s.replace(",+$".toRegex(), "")
            }
        }
    }
    
    private fun createNumberFormat(formatStr: String): Format {
        var format = cleanFormatForNumber(formatStr)
        var symbols: DecimalFormatSymbols = decimalSymbols
        
        // Do we need to change the grouping character?
        // e.g. for a format like #'##0 which wants 12'345 not 12,345
        val agm: Matcher = alternateGrouping.matcher(format)
        if (agm.find()) {
            val grouping: Char = agm.group(2)!!.first()
            // Only replace the grouping character if it is not the default
            // grouping character for the US locale (',') in order to enable
            // correct grouping for non-US locales.
            if (grouping != ',') {
                symbols = DecimalFormatSymbols.getInstance(locale)
                symbols.groupingSeparator = grouping
                val oldPart: String = agm.group(1) ?: ""
                val newPart = oldPart.replace(grouping, ',')
                format = format.replace(oldPart, newPart)
            }
        }
        return try {
            InternalDecimalFormatWithScale(format, symbols)
        } catch (iae: IllegalArgumentException) {
            // the pattern could not be parsed correctly,
            // so fall back to the default number format
            defaultFormat
        }
    }// otherwise use general format
    
    // for numeric cells try user supplied default
    private val defaultFormat: Format = ExcelGeneralNumberFormat(locale, useScientificFormat)
    
    /**
     * Performs Excel-style date formatting, using the supplied Date and format
     */
    private fun performDateFormatting(d: Date, dateFormat: Format?): String {
        val df: Format = dateFormat ?: defaultFormat
        return df.format(d)
    }
    
    /**
     * Returns the formatted value of an Excel date as a <tt>String</tt> based on the cell's `DataFormat`.
     * i.e. "Thursday, January 02, 2003" , "01/02/2003" , "02-Jan" , etc.
     *
     *
     * If any conditional format rules apply, the highest priority with a number format is used. If no rules contain a
     * number format, or no rules apply, the cell's style format is used. If the style does not have a format, the
     * default date format is applied.
     *
     * @param data             to format
     * @param dataFormat
     * @param dataFormatString
     * @return Formatted value
     */
    private fun getFormattedDateString(
        data: Double,
        dataFormat: Int,
        dataFormatString: String
    ): String {
        val dateFormat: Format = getFormat(data, dataFormat, dataFormatString)
        if (dateFormat is ExcelStyleDateFormatter) {
            // Hint about the raw excel value
            (dateFormat as ExcelStyleDateFormatter?)!!.setDateToBeFormatted(data)
        }
        return performDateFormatting(DateUtil.getJavaDate(data, use1904windowing), dateFormat)
    }
    
    /**
     * Returns the formatted value of an Excel number as a <tt>String</tt> based on the cell's `DataFormat`.
     * Supported formats include currency, percents, decimals, phone number, SSN, etc.: "61.54%", "$100.00", "(800)
     * 555-1234".
     *
     *
     * Format comes from either the highest priority conditional format rule with a specified format, or from the cell
     * style.
     *
     * @param data             to format
     * @param dataFormat
     * @param dataFormatString
     * @return a formatted number string
     */
    private fun getFormattedNumberString(
        data: BigDecimal,
        dataFormat: Int,
        dataFormatString: String
    ): String {
        val numberFormat: Format = getFormat(data.toDouble(), dataFormat, dataFormatString)
        return E_NOTATION_PATTERN.matcher(numberFormat.format(data)).replaceFirst("E+$1")
    }
    
    /**
     * Format data.
     *
     * @param data
     * @param dataFormat
     * @param dataFormatString
     * @return
     */
    fun format(data: BigDecimal, dataFormat: Int, dataFormatString: String): String {
        return if (DateUtils.isADateFormat(dataFormat, dataFormatString)) {
            getFormattedDateString(data.toDouble(), dataFormat, dataFormatString)
        } else getFormattedNumberString(data, dataFormat, dataFormatString)
    }
    
    /**
     *
     *
     * Sets a default number format to be used when the Excel format cannot be parsed successfully. **Note:** This is
     * a fall back for when an error occurs while parsing an Excel number format pattern. This will not affect cells
     * with the *General* format.
     *
     *
     *
     * The value that will be passed to the Format's format method (specified by `java.text.Format#format`)
     * will be a double value from a numeric cell. Therefore the code in the format method should expect a
     * `Number` value.
     *
     *
     * @param format A Format instance to be used as a default
     * @see Format.format
     */
    fun setDefaultNumberFormat(format: Format) {
        for (entry in formats.entries) {
            if (entry.value == defaultNumFormat) {
                entry.setValue(format)
            }
        }
        defaultNumFormat = format
    }
    
    /**
     * Adds a new format to the available formats.
     *
     *
     * The value that will be passed to the Format's format method (specified by `java.text.Format#format`)
     * will be a double value from a numeric cell. Therefore the code in the format method should expect a
     * `Number` value.
     *
     *
     * @param excelFormatStr The data format string
     * @param format         A Format instance
     */
    fun addFormat(excelFormatStr: String, format: Format) {
        formats[excelFormatStr] = format
    }
    
    /**
     * Format class for Excel's SSN format. This class mimics Excel's built-in SSN formatting.
     *
     * @author James May
     */
    class SSNFormat : Format() {
        override fun format(
            obj: Any?,
            toAppendTo: StringBuffer,
            pos: FieldPosition?
        ): StringBuffer {
            return toAppendTo.append(format(obj as Number?))
        }
        
        override fun parseObject(source: String?, pos: ParsePosition?): Any {
            return df.parseObject(source, pos)
        }
        
        companion object {
            private val df: DecimalFormat = createIntegerOnlyFormat("000000000")
            
            /**
             * Format a number as an SSN
             */
            fun format(num: Number?): String {
                val result: String = df.format(num)
                return result.substring(0, 3) + '-' + result.substring(
                    3, 5
                ) + '-' + result.substring(5, 9)
            }
        }
    }
    
    /**
     * Format class for Excel Zip + 4 format. This class mimics Excel's built-in formatting for Zip + 4.
     *
     * @author James May
     */
    class ZipPlusFourFormat : Format() {
        override fun format(
            obj: Any?,
            toAppendTo: StringBuffer,
            pos: FieldPosition?
        ): StringBuffer {
            return toAppendTo.append(format(obj as Number?))
        }
        
        override fun parseObject(source: String?, pos: ParsePosition?): Any {
            return df.parseObject(source, pos)
        }
        
        companion object {
            private val df: DecimalFormat = createIntegerOnlyFormat("000000000")
            
            /**
             * Format a number as Zip + 4
             */
            fun format(num: Number?): String {
                val result: String = df.format(num)
                return result.substring(0, 5) + '-' + result.substring(5, 9)
            }
        }
    }
    
    /**
     * Format class for Excel phone number format. This class mimics Excel's built-in phone number formatting.
     *
     * @author James May
     */
    class PhoneFormat : Format() {
        override fun format(
            obj: Any?,
            toAppendTo: StringBuffer,
            pos: FieldPosition?
        ): StringBuffer {
            return toAppendTo.append(format(obj as Number?))
        }
        
        override fun parseObject(source: String?, pos: ParsePosition?): Any {
            return df.parseObject(source, pos)
        }
        
        companion object {
            private val df: DecimalFormat = createIntegerOnlyFormat("##########")
            
            /**
             * Format a number as a phone number
             */
            fun format(num: Number?): String {
                val result: String = df.format(num)
                val sb = StringBuilder()
                val seg1: String
                val seg2: String
                val seg3: String
                val len = result.length
                if (len <= 4) {
                    return result
                }
                seg3 = result.substring(len - 4, len)
                seg2 = result.substring(max(0, len - 7), len - 4)
                seg1 = result.substring(max(0, len - 10), max(0, len - 7))
                if (seg1.trim { it <= ' ' }.isNotEmpty()) {
                    sb.append('(').append(seg1).append(") ")
                }
                if (seg2.trim { it <= ' ' }.isNotEmpty()) {
                    sb.append(seg2).append('-')
                }
                sb.append(seg3)
                return sb.toString()
            }
        }
    }
    
    /**
     * Workaround until we merge [org.apache.poi.ss.usermodel.DataFormatter] with [CellFormat]. Constant,
     * non-cachable wrapper around a
     * [CellFormatResult]
     */
    inner class CellFormatResultWrapper(val result: CellFormatResult) :
        Format() {
        override fun format(
            obj: Any?,
            toAppendTo: StringBuffer,
            pos: FieldPosition?
        ): StringBuffer {
            return toAppendTo.append(result.text.trim { it <= ' ' })
        }
        
        override fun parseObject(source: String?, pos: ParsePosition?): Any? {
            return null // Not supported
        }
    }
    
    companion object {
        /**
         * For logging any problems we find
         */
        private const val defaultFractionWholePartFormat = "#"
        private const val defaultFractionFractionPartFormat = "#/##"
        
        /**
         * Pattern to find a number format: "0" or "#"
         */
        private val numPattern: Pattern = Pattern.compile("[0#]+")
        
        /**
         * Pattern to find days of week as text "ddd...."
         */
        private val daysAsText: Pattern = Pattern.compile("(d{3,})", Pattern.CASE_INSENSITIVE)
        
        /**
         * Pattern to find "AM/PM" marker
         */
        private val amPmPattern: Pattern =
            Pattern.compile("(([AP])[M/P]*)|(([上下])[午/下]*)", Pattern.CASE_INSENSITIVE)
        
        /**
         * Pattern to find formats with condition ranges e.g. [>=100]
         */
        private val rangeConditionalPattern: Pattern =
            Pattern.compile(".*\\[\\s*(>|>=|<|<=|=)\\s*[0-9]*\\.*[0-9].*")
        
        /**
         * A regex to find locale patterns like [$$-1009] and [$?-452]. Note that we don't currently process these into
         * locales
         */
        private val localePatternGroup: Pattern = Pattern.compile("(\\[\\$[^-\\]]*-[0-9A-Z]+])")
        
        /**
         * A regex to match the colour formattings rules. Allowed colours are: Black, Blue, Cyan, Green, Magenta, Red,
         * White, Yellow, "Color n" (1<=n<=56)
         */
        private val colorPattern: Pattern = Pattern.compile(
            "(\\[BLACK])|(\\[BLUE])|(\\[CYAN])|(\\[GREEN])|" + "(\\[MAGENTA])|(\\[RED])|(\\[WHITE])|(\\[YELLOW])|"
                    + "(\\[COLOR\\s*\\d])|(\\[COLOR\\s*[0-5]\\d])|(\\[DBNum([123])])|(\\[\\$-\\d{0,3}])",
            Pattern.CASE_INSENSITIVE
        )
        
        /**
         * A regex to identify a fraction pattern. This requires that replaceAll("\\?", "#") has already been called
         */
        private val fractionPattern: Pattern =
            Pattern.compile("(?:([#\\d]+)\\s+)?(#+)\\s*/\\s*([#\\d]+)")
        
        /**
         * A regex to strip junk out of fraction formats
         */
        private val fractionStripper: Pattern = Pattern.compile("(\"[^\"]*\")|([^ ?#\\d/]+)")
        
        /**
         * A regex to detect if an alternate grouping character is used in a numeric format
         */
        private val alternateGrouping: Pattern = Pattern.compile("([#0]([^.#0])[#0]{3})")
        private val E_NOTATION_PATTERN: Pattern = Pattern.compile("E(\\d)")
        
        /**
         * Cells formatted with a date or time format and which contain invalid date or time values show 255 pound signs
         * ("#").
         */
        var invalidDateTimeString: String
        
        init {
            val buf = StringBuilder()
            for (i in 0..254) {
                buf.append('#')
            }
            invalidDateTimeString = buf.toString()
        }
        // Some custom formats
        /**
         * @return a <tt>DecimalFormat</tt> with parseIntegerOnly set `true`
         */
        private fun createIntegerOnlyFormat(fmt: String): DecimalFormat {
            val dsf: DecimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.ROOT)
            val result = DecimalFormat(fmt, dsf)
            result.isParseIntegerOnly = true
            return result
        }
        
        /**
         * Enables excel style rounding mode (round half up) on the Decimal Format given.
         */
        fun setExcelStyleRoundingMode(format: DecimalFormat) {
            setExcelStyleRoundingMode(format, RoundingMode.HALF_UP)
        }
        
        /**
         * Enables custom rounding mode on the given Decimal Format.
         *
         * @param format       DecimalFormat
         * @param roundingMode RoundingMode
         */
        fun setExcelStyleRoundingMode(format: DecimalFormat, roundingMode: RoundingMode) {
            format.roundingMode = roundingMode
        }
    }
}
