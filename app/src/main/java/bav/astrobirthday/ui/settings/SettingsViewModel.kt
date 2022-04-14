package bav.astrobirthday.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import bav.astrobirthday.data.BirthdayUpdater
import bav.astrobirthday.data.UserRepository
import bav.astrobirthday.ui.common.ViewEvent
import bav.astrobirthday.utils.enqueuePeriodicBirthdayUpdateWorker
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class SettingsViewState(val birthday: LocalDate, val sortSolarPlanetsByDate: Boolean)

class SettingsViewModel(
    private val repository: UserRepository,
    private val worker: BirthdayUpdater,
) : ViewModel() {

    private val birthdayFlow = repository.birthdayFlow.filterNotNull()
    private val sortByDateFlow = repository.sortSolarPlanetsByDateFlow

    val events: LiveData<SettingsEvents>
        get() = _events
    private val _events = MutableLiveData<SettingsEvents>()

    val state: LiveData<SettingsViewState> =
        combine(birthdayFlow, sortByDateFlow, ::SettingsViewState)
            .asLiveData()

    fun pickBirthday() = viewModelScope.launch {
        val date = repository.birthdayFlow.firstOrNull() ?: LocalDate.now()
        val millis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        _events.value = SettingsEvents.OpenPicker(millis)
    }

    fun onDateSelected(millis: Long) = viewModelScope.launch {
        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
        repository.setBirthday(date)
        worker.updateBirthdays()
    }

    fun toggleSortSolarPlanetsByDate() {
        viewModelScope.launch {
            state.value?.sortSolarPlanetsByDate?.let {
                repository.setSortSolarPlanetsByDate(it.not())
            }
        }
    }

    fun onBackClick() {
        _events.value = SettingsEvents.Close()
    }

    sealed class SettingsEvents : ViewEvent() {
        class OpenPicker(val millis: Long) : SettingsEvents()
        class Close() : SettingsEvents()
    }
}