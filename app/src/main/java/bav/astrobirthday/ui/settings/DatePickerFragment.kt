package bav.astrobirthday.ui.settings

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import bav.astrobirthday.common.Preferences
import org.koin.android.ext.android.inject
import java.time.LocalDate

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val preferences: Preferences by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = preferences.userBirthday ?: LocalDate.now()
        return DatePickerDialog(requireContext(), this, date.year, date.month.ordinal, date.dayOfMonth)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        preferences.setBirthday(LocalDate.of(year, month + 1, day))
    }
}
