package bav.astrobirthday.utils

import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker

fun Fragment.openDatePicker(
    millis: Long,
    endDate: Long = System.currentTimeMillis(),
    onDateSelected: (Long) -> Unit
) {
    val picker = MaterialDatePicker.Builder.datePicker()
        .setSelection(millis)
        .setCalendarConstraints(
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.before(endDate))
                .build()
        )
        .build()
    picker.addOnPositiveButtonClickListener(onDateSelected)
    picker.show(parentFragmentManager, "datePicker")
}