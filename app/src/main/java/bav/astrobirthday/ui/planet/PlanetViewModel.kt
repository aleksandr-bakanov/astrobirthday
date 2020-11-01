package bav.astrobirthday.ui.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bav.astrobirthday.common.Preferences
import java.time.LocalDate

class PlanetViewModel(
    private val preferences: Preferences
) : ViewModel() {

    private val _birthdayDate = MutableLiveData<LocalDate?>().apply {
        value = preferences.userBirthday
    }
    val birthdayDate: LiveData<LocalDate?> = _birthdayDate

    fun setBirthday(date: LocalDate) {
        _birthdayDate.postValue(date)
        preferences.userBirthday = date
    }
}