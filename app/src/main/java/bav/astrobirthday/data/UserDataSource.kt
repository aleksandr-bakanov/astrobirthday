package bav.astrobirthday.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import bav.astrobirthday.common.Formatters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class UserDataSource(
    private val dataStore: DataStore<Preferences>
) {

    private val BIRTHDAY_KEY = stringPreferencesKey("birthday")
    private val SORT_SOLAR_PLANETS_BY_DATE_KEY = booleanPreferencesKey("sortSolarPlanetsByDate")

    val birthdayFlow: Flow<LocalDate?> = dataStore.data
        .map { preferences ->
            preferences[BIRTHDAY_KEY]?.let { Formatters.stringToDate(it) }
        }

    suspend fun setBirthday(value: LocalDate) {
        dataStore.edit { settings ->
            settings[BIRTHDAY_KEY] = Formatters.dateToString(value)
        }
    }

    val sortSolarPlanetsByDateFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SORT_SOLAR_PLANETS_BY_DATE_KEY] ?: false
        }

    suspend fun setSortSolarPlanetsByDate(value: Boolean) {
        dataStore.edit { settings ->
            settings[SORT_SOLAR_PLANETS_BY_DATE_KEY] = value
        }
    }
}