package bav.astrobirthday.ui.settings

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import bav.astrobirthday.ui.common.ComposeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : ComposeFragment() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent { SettingsScreen(viewModel) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is SettingsViewModel.Event.OpenPicker -> {
                    val datePickerFragment = DatePickerFragment(viewModel)
                    datePickerFragment.show(parentFragmentManager, "datePicker")
                }
                else -> Unit
            }
        }
    }
}