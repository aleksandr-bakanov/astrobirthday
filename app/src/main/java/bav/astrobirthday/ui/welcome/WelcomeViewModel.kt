package bav.astrobirthday.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.data.BirthdayUpdater
import bav.astrobirthday.domain.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate

sealed class WelcomeUiState {
    object Loading : WelcomeUiState()
    object Welcome : WelcomeUiState()
    class Calendar(val currentDate: LocalDate) : WelcomeUiState()
}

class WelcomeViewModel(
    private val userRepository: UserRepository,
    private val birthdayUpdater: BirthdayUpdater
) : ViewModel() {

    private val _state: MutableLiveData<WelcomeUiState> = MutableLiveData(WelcomeUiState.Loading)
    val state: LiveData<WelcomeUiState> = _state

    private val _events = SingleLiveEvent<Event>()
    val events: LiveData<Event> = _events

    init {
        viewModelScope.launch {
            if (userRepository.birthdayFlow.firstOrNull() == null) {
                _state.value = WelcomeUiState.Welcome
            } else {
                _events.value = Event.Close
            }
        }
    }

    fun letsGo() {
        _state.value = WelcomeUiState.Calendar(currentDate = LocalDate.now())
    }

    fun setDate(value: LocalDate) {
        _state.value = WelcomeUiState.Calendar(currentDate = value)
    }

    fun submitDate() {
        val date = (_state.value as WelcomeUiState.Calendar).currentDate
        _state.value = WelcomeUiState.Loading
        viewModelScope.launch {
            userRepository.setBirthday(date)
            userRepository.birthdayFlow
                .filter { it != null }
                .collect {
                    birthdayUpdater.updateBirthdays()

                    viewModelScope.launch {
                        while (true) {
                            val data =
                                birthdayUpdater.getBirthdayUpdateWorkerStateObserver().get()[0]
                            delay(500)
                            if (data.state == WorkInfo.State.SUCCEEDED)
                                break
                        }
                        _events.value = Event.Close
                    }

                }
        }
    }

    sealed class Event {
        object Close : Event()
    }

}