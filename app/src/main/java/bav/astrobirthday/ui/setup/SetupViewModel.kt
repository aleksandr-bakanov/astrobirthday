package bav.astrobirthday.ui.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.data.BirthdayUpdater
import bav.astrobirthday.domain.UserRepository
import bav.astrobirthday.domain.exception.DateInFuture
import bav.astrobirthday.domain.exception.DateNotParsed
import bav.astrobirthday.domain.exception.YearExceedMinValue
import bav.astrobirthday.ui.setup.SetupUiState.DateState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.DateTimeException
import java.time.LocalDate

class SetupUiState(val date: String, val dateState: DateState) {
    sealed class DateState {
        object Valid : DateState()
        object ExceedMinValue : DateState()
        object InFuture : DateState()
        object NotFilled : DateState()
        object WrongDate : DateState()
    }
}

class SetupViewModel(
    private val dateParseUseCase: DateParseUseCase,
    private val userRepository: UserRepository,
    private val birthdayUpdater: BirthdayUpdater
) : ViewModel() {

    private val _state = MutableLiveData(getDefaultUiState())
    val state: LiveData<SetupUiState> = _state

    private val _events = SingleLiveEvent<Event>()
    val events: LiveData<Event> = _events

    init {
        viewModelScope.launch {
            userRepository.birthdayFlow.firstOrNull()?.let { date ->
                setDate(dateParseUseCase.dateToString(date))
            }
        }
    }

    fun setDate(value: String) {
        _state.value = SetupUiState(value, _state.value?.dateState!!)
    }

    fun submitDate() {
        try {
            val date = dateParseUseCase.stringToDate(_state.value?.date!!)
            _state.value = getUiState(dateState = DateState.Valid)
            viewModelScope.launch {
                userRepository.setBirthday(date)
            }
            birthdayUpdater.updateBirthdays()
            _events.value = Event.Close
        } catch (t: Throwable) {
            _state.value = getUiState(
                dateState = when (t) {
                    is DateNotParsed -> DateState.NotFilled
                    is YearExceedMinValue -> DateState.ExceedMinValue
                    is DateTimeException -> DateState.WrongDate
                    is DateInFuture -> DateState.InFuture
                    else -> DateState.Valid
                }
            )
        }
    }

    private fun getDefaultUiState(): SetupUiState {
        return SetupUiState(
            date = dateParseUseCase.dateToString(LocalDate.now()),
            dateState = DateState.Valid
        )
    }

    private fun getUiState(dateState: DateState): SetupUiState {
        return SetupUiState(
            date = _state.value!!.date,
            dateState = dateState
        )
    }

    sealed class Event {
        object Close : Event()
    }
}