package bav.astrobirthday.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import bav.astrobirthday.R
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.utils.localDateToString
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

// TODO: Отображать всплывашку над bottom navigation или Toast "Birthday set to: <date>"
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val preferences: UserPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            preferences.birthdayFlow.collect {
                it?.let { date ->
                    birthday_date.text = requireContext().localDateToString(date)
                }
            }
        }

        birthday_date.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(parentFragmentManager, "datePicker")
        }
    }
}