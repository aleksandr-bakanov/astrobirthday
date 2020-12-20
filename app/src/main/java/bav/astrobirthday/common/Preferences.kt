package bav.astrobirthday.common

import androidx.lifecycle.LiveData
import java.time.LocalDate

interface Preferences {
    var userBirthday: LocalDate?
    fun setBirthday(value: LocalDate?)
    val birthdayDate: LiveData<LocalDate?>

    val appBarTitleResId: LiveData<String>
    fun setAppBarTitle(title: String)
}