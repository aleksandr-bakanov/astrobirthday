package bav.astrobirthday

import androidx.lifecycle.ViewModel
import bav.astrobirthday.ui.setup.SetupUseCase

class MainViewModel(
    private val setupUseCase: SetupUseCase
) : ViewModel() {

    suspend fun isBirthdaySet(): Boolean = setupUseCase.isBirthdaySet()
}