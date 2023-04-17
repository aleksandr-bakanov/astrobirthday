package bav.astrobirthday.domain

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface UserRepository {
    suspend fun setBirthday(value: LocalDate)
    val birthdayFlow: Flow<LocalDate?>

    suspend fun setSortSolarPlanetsByDate(value: Boolean)
    val sortSolarPlanetsByDateFlow: Flow<Boolean>

    suspend fun setNotificationsEnabled(value: Boolean)
    val notificationsEnabledFlow: Flow<Boolean?>
}