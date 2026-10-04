package com.voyager177.core.ui

import kotlin.math.abs
import kotlin.math.round

private const val ROUNDING_FACTOR = 10_000_000_000.0

internal fun formatNumber(number: Double): String {
    if (!number.isFinite()) return number.toString()

    val value = if (abs(number) < 1_000_000_000_000.0) {
        round(number * ROUNDING_FACTOR) / ROUNDING_FACTOR
    } else number

    val integerValue = value.toLong()
    return if (value == integerValue.toDouble()) integerValue.toString()
    else value.toString()
}