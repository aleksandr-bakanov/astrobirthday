package bav.astrobirthday.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.domain.UserRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.LocalDate

class SettingsViewState(val birthday: LocalDate, val sortSolarPlanetsByDate: Boolean)

class SettingsViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val birthdayFlow = repository.birthdayFlow.filterNotNull()
    private val sortByDateFlow = repository.sortSolarPlanetsByDateFlow

    val events: LiveData<Event>
        get() = _events
    private val _events = SingleLiveEvent<Event>()

    val state: LiveData<SettingsViewState> =
        combine(birthdayFlow, sortByDateFlow, ::SettingsViewState)
            .asLiveData()

    fun pickBirthday() {
        _events.value = Event.OpenPicker
    }

    fun toggleSortSolarPlanetsByDate() {
        viewModelScope.launch {
            state.value?.sortSolarPlanetsByDate?.let {
                repository.setSortSolarPlanetsByDate(it.not())
            }
        }
    }

    sealed class Event {
        object OpenPicker : Event()
    }
}