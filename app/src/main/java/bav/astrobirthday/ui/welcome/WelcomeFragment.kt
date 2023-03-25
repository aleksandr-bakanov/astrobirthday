package bav.astrobirthday.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : ComposeFragment() {

    private val viewModel: WelcomeViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent {
            AstroBirthdayTheme {
                WelcomeScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is WelcomeViewModel.Event.Close -> findNavController().navigate(R.id.action_nav_welcome_to_homeFragment)
            }
        }
    }

}