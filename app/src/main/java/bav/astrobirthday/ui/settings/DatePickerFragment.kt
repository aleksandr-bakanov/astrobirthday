package bav.astrobirthday.ui.settings

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import bav.astrobirthday.R
import bav.astrobirthday.data.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import java.time.LocalDate

class DatePickerFragment(
    private val viewModel: SettingsViewModel
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val userRepository: UserRepository by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = runBlocking { userRepository.birthdayFlow.firstOrNull() ?: LocalDate.now() }
        val dialog = DatePickerDialog(
            requireContext(),
            this,
            date.year,
            date.month.ordinal,
            date.dayOfMonth
        )
        dialog.datePicker.maxDate = System.currentTimeMillis()
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "") { _, _ -> }
        dialog.setMessage(getString(R.string.set_your_birthday))
        isCancelable = false
        return dialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        viewModel.setBirthday(LocalDate.of(year, month + 1, day))
    }
}
