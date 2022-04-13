package bav.astrobirthday.ui.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bav.astrobirthday.common.Formatters
import java.time.LocalDate

class SetupUiState(val date: String, val isDateValid: Boolean)

class SetupViewModel() : ViewModel() {

    private val _state = MutableLiveData(getDefaultUiState())
    val state: LiveData<SetupUiState> = _state

    fun setDate(value: String) {
        try {
            val date = Formatters.parseIsoLocalDate(value)
            _state.value = SetupUiState(value, true)
        } catch (t: Throwable) {
            _state.value = SetupUiState(_state.value?.date!!, false)
        }
    }

    fun submitDate() {

    }

    private fun parseDate(value: String) {
        // -999 999 999
    }

    private fun getDefaultUiState(): SetupUiState {
        return SetupUiState(
            date = Formatters.formatLocalDate(LocalDate.now()),
            isDateValid = true
        )
    }
}