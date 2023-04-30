package bav.astrobirthday.utils

import androidx.core.text.BidiFormatter

fun String.unicodeWrap(): String {
    val bidiFormatter = BidiFormatter.getInstance()
    return bidiFormatter.unicodeWrap(this)
}