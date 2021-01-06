package bav.astrobirthday.utils

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import bav.astrobirthday.ui.common.NavUiConfigurator

fun Fragment.setupToolbar(toolbar: Toolbar) {
    (requireActivity() as NavUiConfigurator).setupToolbar(toolbar)
}