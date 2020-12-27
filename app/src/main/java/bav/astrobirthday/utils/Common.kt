package bav.astrobirthday.utils

import android.content.Context

fun Int.toDp(context: Context): Float {
    return (this / context.resources.displayMetrics.density)
}