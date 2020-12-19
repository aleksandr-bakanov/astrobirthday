package bav.astrobirthday.common

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface Preferences {
    var userBirthday: LocalDate?
    fun setBirthday(value: LocalDate?)
    val birthdayDate: LiveData<LocalDate?>
}