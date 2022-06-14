package bav.astrobirthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.ui.setup.SetupUseCase
import bav.astrobirthday.utils.SolarPlanetsUpdateUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val setupUseCase: SetupUseCase,
    private val solarPlanetsUpdateUseCase: SolarPlanetsUpdateUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            solarPlanetsUpdateUseCase.check()
        }
    }

    suspend fun isBirthdaySet(): Boolean = setupUseCase.isBirthdaySet()
}