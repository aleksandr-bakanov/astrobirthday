package bav.astrobirthday.ui.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.databinding.FragmentSettingsBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.utils.localDateToString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

// TODO: Отображать всплывашку над bottom navigation или Toast "Birthday set to: <date>"
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val preferences: UserPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            lifecycleScope.launch {
                preferences.birthdayFlow.collect {
                    it?.let { date ->
                        birthdayDate.text = requireContext().localDateToString(date)
                    }
                }
            }

            birthdayDate.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(parentFragmentManager, "datePicker")
            }
        }
    }
}