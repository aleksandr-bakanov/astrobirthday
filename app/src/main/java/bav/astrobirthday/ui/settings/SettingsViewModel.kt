package bav.astrobirthday.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.domain.UserRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import java.time.LocalDate

class SettingsViewState(val birthday: LocalDate)

class SettingsViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val birthdayFlow = repository.birthdayFlow.filterNotNull()

    val events: LiveData<Event>
        get() = _events
    private val _events = SingleLiveEvent<Event>()

    val state: LiveData<SettingsViewState> =
        birthdayFlow.mapLatest { SettingsViewState(it) }.asLiveData()

    fun pickBirthday() {
        _events.value = Event.OpenPicker
    }

    sealed class Event {
        object OpenPicker : Event()
    }
}