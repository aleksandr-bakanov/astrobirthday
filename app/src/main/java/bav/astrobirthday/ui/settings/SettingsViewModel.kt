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
import java.time.ZoneId

class SettingsViewModel(private val preferences: UserPreferences) : ViewModel() {

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
        val millis = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        _events.value = PickerEvent.OpenPicker(millis)
    }

    fun onDateSelected(millis: Long) = viewModelScope.launch {
        preferences.setBirthday(
            Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
        )
    }

    sealed class PickerEvent : ViewEvent() {
        class OpenPicker(val millis: Long) : PickerEvent()
    }
}