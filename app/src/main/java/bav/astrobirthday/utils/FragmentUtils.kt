package bav.astrobirthday.utils

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import bav.astrobirthday.ui.common.NavigationController

fun Fragment.setupToolbar(toolbar: Toolbar) {
    (requireActivity() as NavigationController).setupToolbar(toolbar)
}