package bav.astrobirthday.ui.settings

import android.os.Bundle
import android.view.View
import bav.astrobirthday.databinding.FragmentSettingsBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.settings.SettingsViewModel.PickerEvent
import bav.astrobirthday.utils.localDateToString
import bav.astrobirthday.utils.openDatePicker
import bav.astrobirthday.utils.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)
            viewModel.birthday.observe(viewLifecycleOwner) {
                birthdayDate.text = requireContext().localDateToString(it)
            }
            setBirthday.setOnClickListener {
                viewModel.pickBirthday()
            }
            viewModel.events.observe(viewLifecycleOwner) { events ->
                events.peek { event ->
                    when (event) {
                        is PickerEvent.OpenPicker -> openDatePicker(
                            millis = event.millis,
                            onDateSelected = viewModel::onDateSelected
                        )
                    }
                    true
                }
            }
        }
    }
}