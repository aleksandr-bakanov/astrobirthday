package bav.astrobirthday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bav.astrobirthday.common.Preferences
import java.time.LocalDate

class HomeViewModel(
    private val preferences: Preferences
) : ViewModel() {

    private val _birthdayDate = MutableLiveData<LocalDate?>().apply {
        value = preferences.userBirthday
    }
    val birthdayDate: LiveData<LocalDate?> = _birthdayDate
}