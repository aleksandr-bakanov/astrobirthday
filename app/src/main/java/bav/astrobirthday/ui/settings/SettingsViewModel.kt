package bav.astrobirthday.ui.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import bav.astrobirthday.common.UserPreferences
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
    private val context: Context,
    private val preferences: UserPreferences
) : ViewModel() {

    private val birthdayFlow = preferences.birthdayFlow.filterNotNull()
    private val sortByDateFlow = preferences.sortSolarPlanetsByDateFlow

    val events: LiveData<SettingsEvents>
        get() = _events
    private val _events = MutableLiveData<SettingsEvents>()

    val state: LiveData<SettingsViewState> = combine(birthdayFlow, sortByDateFlow, ::SettingsViewState)
        .asLiveData()

    fun pickBirthday() = viewModelScope.launch {
        val date = preferences.birthdayFlow.firstOrNull() ?: LocalDate.now()
        val millis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        _events.value = SettingsEvents.OpenPicker(millis)
    }

    fun onDateSelected(millis: Long) = viewModelScope.launch {
        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
        preferences.setBirthday(date)

        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<BirthdayUpdateWorker>().build()
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)

        enqueuePeriodicBirthdayUpdateWorker(context)
    }

    fun toggleSortSolarPlanetsByDate() {
        preferences.setSortSolarPlanetsByDate(preferences.getSortSolarPlanetsByDate().not())
    }

    fun getSortSolarPlanetsByDate(): Boolean = preferences.getSortSolarPlanetsByDate()

    fun onBackClick() {
        _events.value = SettingsEvents.Close()
    }

    sealed class SettingsEvents : ViewEvent() {
        class OpenPicker(val millis: Long) : SettingsEvents()
        class Close() : SettingsEvents()
    }
}