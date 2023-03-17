package bav.astrobirthday.ui.welcome

import androidx.compose.ui.platform.ComposeView
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.theme.AstroBirthdayTheme

class WelcomeFragment : ComposeFragment() {

    override fun ComposeView.setContent() {
        setContent {
            AstroBirthdayTheme {
                WelcomeScreen()
            }
        }
    }

}