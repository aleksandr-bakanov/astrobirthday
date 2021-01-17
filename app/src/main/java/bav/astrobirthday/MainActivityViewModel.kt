package bav.astrobirthday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.common.InitData
import bav.astrobirthday.ui.common.ViewEvent
import kotlinx.coroutines.launch

class MainActivityViewModel(
    initData: InitData
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
            initData.waitForInit()
            _state.value = MainViewState(barsVisible = true)
            _events.value = AnimateBars()
        }
    }

    data class MainViewState(
        val barsVisible: Boolean = false
    )

    sealed class MainViewEvent : ViewEvent() {
        class AnimateBars : MainViewEvent()
    }
}