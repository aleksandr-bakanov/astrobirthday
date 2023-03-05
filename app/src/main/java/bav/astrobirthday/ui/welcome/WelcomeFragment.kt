package bav.astrobirthday.ui.welcome

import androidx.compose.ui.platform.ComposeView
import bav.astrobirthday.ui.common.ComposeFragment

class WelcomeFragment : ComposeFragment() {

    override fun ComposeView.setContent() {
        setContent { WelcomeScreen() }
    }

}