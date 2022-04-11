package bav.astrobirthday.common

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface UserPreferences {
    suspend fun setBirthday(value: LocalDate?)
    val birthdayFlow: Flow<LocalDate?>

    fun getSortSolarPlanetsByDate(): Boolean
    fun setSortSolarPlanetsByDate(value: Boolean)
    val sortSolarPlanetsByDateFlow: Flow<Boolean>
}