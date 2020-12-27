package bav.astrobirthday.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import bav.astrobirthday.R
import bav.astrobirthday.common.Preferences
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import java.time.LocalDate

// TODO: Отображать всплывашку над bottom navigation или Toast "Birthday set to: <date>"
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val preferences: Preferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences.birthdayDate.observe(viewLifecycleOwner, {
            it?.let { date ->
                birthday_date.text = date.toString()
            }
        })
        birthday_date.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(parentFragmentManager, "datePicker")
        }
    }
}