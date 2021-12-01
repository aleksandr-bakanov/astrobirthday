package bav.astrobirthday.common

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface UserPreferences {
    suspend fun setBirthday(value: LocalDate?)
    val birthdayFlow: Flow<LocalDate?>
    val userBirthday: LocalDate?

    fun getImmediate(): Boolean
    fun setImmediate(value: Boolean)

    fun getFlexible(): Boolean
    fun setFlexible(value: Boolean)
}