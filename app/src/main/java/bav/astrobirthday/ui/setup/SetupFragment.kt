package bav.astrobirthday.ui.setup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentSetupBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.settings.DatePickerFragment
import bav.astrobirthday.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetupFragment : BaseFragment<FragmentSetupBinding>(FragmentSetupBinding::inflate) {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePickerFragment = DatePickerFragment(viewModel)
        datePickerFragment.show(parentFragmentManager, "datePicker")

        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is SettingsViewModel.Event.ClosePicker -> {
                    findNavController().popBackStack(R.id.nav_home, false)
                }
                else -> Unit
            }
        }
    }
}