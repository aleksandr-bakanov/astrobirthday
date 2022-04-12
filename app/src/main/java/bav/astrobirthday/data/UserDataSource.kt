package bav.astrobirthday.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserDataSource(
    private val dataStore: DataStore<Preferences>
) : UserRepository {

    private val localDateAdapter = LocalDateAdapter()

    private val BIRTHDAY_KEY = stringPreferencesKey("birthday")
    private val SORT_SOLAR_PLANETS_BY_DATE_KEY = booleanPreferencesKey("sortSolarPlanetsByDate")

    override val birthdayFlow: Flow<LocalDate?> = dataStore.data
        .map { preferences ->
            preferences[BIRTHDAY_KEY]?.let { localDateAdapter.fromJson(it) }
        }

    override suspend fun setBirthday(value: LocalDate?) {
        dataStore.edit { settings ->
            settings[BIRTHDAY_KEY] = localDateAdapter.toJson(value) ?: ""
        }
    }

    override val sortSolarPlanetsByDateFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SORT_SOLAR_PLANETS_BY_DATE_KEY] ?: false
        }

    override suspend fun setSortSolarPlanetsByDate(value: Boolean) {
        dataStore.edit { settings ->
            settings[SORT_SOLAR_PLANETS_BY_DATE_KEY] = value
        }
    }

    class LocalDateAdapter {
        @FromJson
        fun fromJson(value: String?) =
            value?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

        @ToJson
        fun toJson(value: LocalDate?) =
            value?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}