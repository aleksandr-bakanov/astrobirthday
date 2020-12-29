package bav.astrobirthday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.ui.common.ViewEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class MainActivityViewModel(
    preferences: UserPreferences,
) : ViewModel() {

    val state: LiveData<MainViewState>
        get() = _state
    private val _state = MutableLiveData<MainViewState>()

    val events: LiveData<MainViewEvent>
        get() = _events
    private val _events = MutableLiveData<MainViewEvent>()

    init {
        _state.value = MainViewState()
        viewModelScope.launch {
            val isBirthdayAlreadySetup = preferences.birthdayFlow.firstOrNull() != null
            if (isBirthdayAlreadySetup) {
                _state.value = MainViewState(barsVisible = true)
            } else {
                preferences.birthdayFlow
                    .filterNotNull()
                    .take(1)
                    .collect {
                        _state.value = MainViewState(barsVisible = true)
                        _events.value = AnimateBars()
                    }
            }
        }
    }

    data class MainViewState(
        val barsVisible: Boolean = false
    )

    sealed class MainViewEvent : ViewEvent() {
        class AnimateBars : MainViewEvent()
    }
}