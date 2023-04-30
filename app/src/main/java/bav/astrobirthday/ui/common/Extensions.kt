package bav.astrobirthday.ui.common

import android.icu.text.DecimalFormat

private val significantDecimalFormat = DecimalFormat().apply {
    isScientificNotation = false
    isGroupingUsed = false
    maximumSignificantDigits = 2
}

private val fractionDecimalFormat = DecimalFormat().apply {
    isScientificNotation = false
    isGroupingUsed = false
    maximumFractionDigits = 2
}

private val integerDecimalFormat = DecimalFormat()

fun Double.toFixedSignificant(): String {
    return when {
        this < 1.0 -> significantDecimalFormat.format(this)
        else -> fractionDecimalFormat.format(this)
    }
}

fun Int.toDecimalFormat(): String {
    return integerDecimalFormat.format(this)
}