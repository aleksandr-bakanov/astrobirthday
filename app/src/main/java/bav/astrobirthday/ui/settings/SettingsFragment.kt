package bav.astrobirthday.ui.settings

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : ComposeFragment() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent {
            AstroBirthdayTheme {
                SettingsScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is SettingsViewModel.Event.OpenPicker -> findNavController().navigate(R.id.action_nav_settings_to_setup)
                else -> Unit
            }
        }
    }
}