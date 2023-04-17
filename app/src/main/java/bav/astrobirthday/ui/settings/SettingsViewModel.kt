package bav.astrobirthday.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.domain.UserRepository
import bav.astrobirthday.utils.NotificationHelper
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.LocalDate

class SettingsViewState(
    val birthday: LocalDate,
    val isNotificationsEnabled: Boolean
)

class SettingsViewModel(
    private val repository: UserRepository,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    private val birthdayFlow = repository.birthdayFlow.filterNotNull()

    val events: LiveData<Event>
        get() = _events
    private val _events = SingleLiveEvent<Event>()

    val state: LiveData<SettingsViewState> = combine(
        birthdayFlow,
        repository.notificationsEnabledFlow
    ) { birthday, userNotificationSetting ->
        SettingsViewState(
            birthday = birthday,
            isNotificationsEnabled = isNotificationsEnabled(
                userSetting = userNotificationSetting,
                systemSetting = notificationHelper.areNotificationsEnabled()
            )
        )
    }.asLiveData()

    private fun isNotificationsEnabled(
        userSetting: Boolean?,
        systemSetting: Boolean
    ): Boolean {
        return when {
            userSetting == null -> systemSetting
            !systemSetting -> false
            else -> userSetting
        }
    }

    fun pickBirthday() {
        _events.value = Event.OpenPicker
    }

    fun toggleNotificationEnabled() {
        state.value?.let {
            if (it.isNotificationsEnabled) {
                viewModelScope.launch {
                    repository.setNotificationsEnabled(false)
                }
            } else {
                if (notificationHelper.areNotificationsEnabled()) {
                    viewModelScope.launch {
                        repository.setNotificationsEnabled(true)
                    }
                } else {
                    _events.value = Event.CheckNotificationPermission
                }
            }
        }
    }

    fun notificationPermissionGranted() {
        viewModelScope.launch {
            repository.setNotificationsEnabled(true)
        }
    }

    fun notificationPermissionDenied() {
        viewModelScope.launch {
            repository.setNotificationsEnabled(false)
        }
    }

    sealed class Event {
        object OpenPicker : Event()
        object CheckNotificationPermission : Event()
    }
}