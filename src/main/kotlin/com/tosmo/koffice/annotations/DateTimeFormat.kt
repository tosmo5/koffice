package com.tosmo.koffice.annotations

import com.tosmo.koffice.converters.string.StringNumberConverter
import java.text.SimpleDateFormat

/**
 * 日期时间格式，默认优先级：DateTimeFormat > NumberFormat
 *
 * @author Thomas Miao
 * @see StringNumberConverter.convertToKtData
 */
annotation class DateTimeFormat(
    /**
     * 日期时间格式
     * @see SimpleDateFormat
     */
    val parttern: String = "",
    val use1904windowing: Boolean = false
)
