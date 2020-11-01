package bav.astrobirthday.ui.planet

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import bav.astrobirthday.R
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate

class PlanetFragment : Fragment(R.layout.fragment_planet) {

    private val planetViewModel: PlanetViewModel by viewModel()

    private val onDateChangeListener: DatePicker.OnDateChangedListener =
        DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            planetViewModel.setBirthday(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planetViewModel.birthdayDate.observe(viewLifecycleOwner, Observer {
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