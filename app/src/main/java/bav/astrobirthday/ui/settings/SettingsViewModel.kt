package bav.astrobirthday.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.ui.common.ViewEvent
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class SettingsViewModel(
    private val preferences: UserPreferences,
    private val syncPlanetsInfo: SyncPlanetsInfo
) : ViewModel() {

    val birthday: LiveData<LocalDate>
        get() = _birthday
    private val _birthday = MutableLiveData<LocalDate>()

    val events: LiveData<PickerEvent>
        get() = _events
    private val _events = MutableLiveData<PickerEvent>()

    init {
        preferences.birthdayFlow
            .filterNotNull()
            .onEach(_birthday::setValue)
            .launchIn(viewModelScope)
    }

    fun pickBirthday() = viewModelScope.launch {
        val date = preferences.birthdayFlow.firstOrNull() ?: LocalDate.now()
        val millis = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        _events.value = PickerEvent.OpenPicker(millis)
    }

    fun onDateSelected(millis: Long) = viewModelScope.launch {
        val date = Instant.ofEpochMilli(millis).atOffset(ZoneOffset.UTC).toLocalDate()
        preferences.setBirthday(date)
        syncPlanetsInfo.sync()
    }

    sealed class PickerEvent : ViewEvent() {
        class OpenPicker(val millis: Long) : PickerEvent()
    }
}