package bav.astrobirthday.utils

import android.app.Activity
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.ui.common.NavUiConfigurator

fun Fragment.setupToolbar(toolbar: Toolbar) {
    (requireActivity() as NavUiConfigurator).setupToolbar(toolbar)
}

fun RecyclerView.smoothSnapToPosition(
    position: Int,
    snapMode: Int = LinearSmoothScroller.SNAP_TO_START
) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}

fun Activity.hideKeyboard() {
    (this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        this.currentFocus?.windowToken, 0
    )
}

fun EditText.updateText(updated: CharSequence?) {
    val s: Editable? = text
    if (isFocused) {
        return
    }
    if (s == null || updated.isNullOrBlank()) {
        setText(updated)
    } else if (s.toString() != updated) {
        s.replace(0, s.length, updated)
    }
}

fun TextView.setHtml(value: String) {
    text = HtmlCompat.fromHtml(value, HtmlCompat.FROM_HTML_MODE_LEGACY)
}
