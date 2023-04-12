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

fun Double.toFixedSignificant(): String {
    return when {
        this < 1.0 -> significantDecimalFormat.format(this)
        else -> fractionDecimalFormat.format(this)
    }
}