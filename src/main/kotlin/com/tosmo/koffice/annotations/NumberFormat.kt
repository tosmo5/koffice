package com.tosmo.koffice.annotations

import java.math.RoundingMode


annotation class NumberFormat(
    /**
     * 格式
     */
    val pattern: String = "",

    /**
     * @see RoundingMode
     */
    val roundingMode: RoundingMode = RoundingMode.HALF_UP
)
