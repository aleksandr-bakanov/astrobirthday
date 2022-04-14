package bav.astrobirthday.ui.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bav.astrobirthday.common.Formatters
import bav.astrobirthday.domain.exception.DateInFuture
import bav.astrobirthday.domain.exception.DateNotParsed
import bav.astrobirthday.domain.exception.YearExceedMinValue
import java.time.DateTimeException
import java.time.LocalDate

class SetupUiState(val date: String, val dateState: DateState)

sealed class DateState {
    object Valid : DateState()
    object ExceedMinValue : DateState()
    object InFuture : DateState()
    object NotFilled : DateState()
    object WrongDate : DateState()
}

class SetupViewModel(
    private val dateParseUseCase: DateParseUseCase
) : ViewModel() {

    private val _state = MutableLiveData(getDefaultUiState())
    val state: LiveData<SetupUiState> = _state

    fun setDate(value: String) {
        _state.value = SetupUiState(value, _state.value?.dateState!!)
    }

    fun submitDate() {
        try {
            val date = dateParseUseCase.parseDate(_state.value?.date!!)
            _state.value = getUiState(state = DateState.Valid)
        } catch (t: DateNotParsed) {
            _state.value = getUiState(state = DateState.NotFilled)
        } catch (t: YearExceedMinValue) {
            _state.value = getUiState(state = DateState.ExceedMinValue)
        } catch (t: DateTimeException) {
            _state.value = getUiState(state = DateState.WrongDate)
        } catch (t: DateInFuture) {
            _state.value = getUiState(state = DateState.InFuture)
        }
    }

    private fun getDefaultUiState(): SetupUiState {
        return SetupUiState(
            date = Formatters.formatLocalDate(LocalDate.now()),
            dateState = DateState.Valid
        )
    }

    private fun getUiState(state: DateState): SetupUiState {
        return SetupUiState(
            date = _state.value!!.date,
            dateState = state
        )
    }
}