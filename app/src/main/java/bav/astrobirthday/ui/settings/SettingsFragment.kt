package bav.astrobirthday.ui.settings

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.settings.SettingsViewModel.SettingsEvents
import bav.astrobirthday.utils.openDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : ComposeFragment() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent { SettingsScreen(viewModel) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events.observe(viewLifecycleOwner) { events ->
            events.peek { event ->
                when (event) {
                    is SettingsEvents.OpenPicker -> openDatePicker(
                        millis = event.millis,
                        onDateSelected = viewModel::onDateSelected
                    )
                    is SettingsEvents.Close -> findNavController().navigateUp()
                }
                true
            }
        }
    }
}