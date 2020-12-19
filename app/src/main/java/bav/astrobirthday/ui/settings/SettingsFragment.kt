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
// Или вообще перенести это в настройки и отображать диалогом, и при тапе на дату скрывать диалог с показом Toast
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val preferences: Preferences by inject()

    private val onDateChangeListener: DatePicker.OnDateChangedListener =
        DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            preferences.setBirthday(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences.birthdayDate.observe(viewLifecycleOwner, {
            it?.let { date ->
                birthday_date.text = date.toString()
                setupDatePicker(date)
            }
        })
        setupDatePicker(LocalDate.now())
    }

    private fun setupDatePicker(date: LocalDate) {
        date_picker.init(date.year, date.month.ordinal, date.dayOfMonth, onDateChangeListener)
    }
}