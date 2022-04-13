package bav.astrobirthday.ui.setup

import androidx.compose.ui.platform.ComposeView
import bav.astrobirthday.ui.common.ComposeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetupFragment : ComposeFragment() {

    private val viewModel: SetupViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent { SetupScreen(viewModel) }
    }
}