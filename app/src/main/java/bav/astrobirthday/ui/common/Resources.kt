package bav.astrobirthday.ui.common

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

sealed class Resources<R> {
    sealed class String : Resources<CharSequence>() {
        class Res(@StringRes val resId: Int, vararg val formatArgs: Any) : String() {
            override fun resolve(context: Context) = context.getString(resId, *formatArgs)
        }

        class FormatRes(@StringRes val formatRes: Int, @StringRes vararg val resIds: Int) : String() {
            override fun resolve(context: Context): CharSequence {
                val strings = resIds.map { context.getString(it) }
                return context.getString(formatRes, *strings.toTypedArray())
            }
        }
    }

    class Drawable(@DrawableRes val resId: Int) : Resources<android.graphics.drawable.Drawable?>() {
        override fun resolve(context: Context) = ContextCompat.getDrawable(context, resId)
    }

    abstract fun resolve(context: Context): R
}