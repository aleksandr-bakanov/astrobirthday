package bav.astrobirthday.utils

import android.content.Context

fun Int.toDp(context: Context): Float {
    return (this / context.resources.displayMetrics.density)
}

fun String?.orNa(): String = if (this.isNullOrBlank()) "n/a" else this
fun Int?.orNa(): String = this?.toString() ?: "n/a"
fun Double?.orNa(): String = this?.toString() ?: "n/a"