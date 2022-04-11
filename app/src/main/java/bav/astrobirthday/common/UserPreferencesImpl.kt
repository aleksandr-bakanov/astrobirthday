package bav.astrobirthday.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {

    private val localDateAdapter = LocalDateAdapter()

    private val BIRTHDAY_KEY = stringPreferencesKey("birthday")
    private val IMMEDIATE_KEY = booleanPreferencesKey("immediate")
    private val SORT_SOLAR_PLANETS_BY_DATE_KEY = booleanPreferencesKey("sortSolarPlanetsByDate")

    private val birthdayStoreFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[BIRTHDAY_KEY]
        }
    override val birthdayFlow: Flow<LocalDate?> =
        birthdayStoreFlow.map { it?.let { localDateAdapter.fromJson(it) } }

    override suspend fun setBirthday(value: LocalDate?) {
        dataStore.edit { settings ->
            settings[BIRTHDAY_KEY] = localDateAdapter.toJson(value) ?: ""
        }
    }

    override fun setSortSolarPlanetsByDate(value: Boolean) {
        _sortSolarPlanetsByDateFlow.value = value
        runBlocking {
            dataStore.edit { settings ->
                settings[SORT_SOLAR_PLANETS_BY_DATE_KEY] = value
            }
        }
    }

    override fun getSortSolarPlanetsByDate(): Boolean {
        return runBlocking {
            dataStore.data.firstOrNull()?.get(SORT_SOLAR_PLANETS_BY_DATE_KEY) ?: false
        }
    }

    private val _sortSolarPlanetsByDateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(getSortSolarPlanetsByDate())
    override val sortSolarPlanetsByDateFlow: Flow<Boolean> = _sortSolarPlanetsByDateFlow

    inner class LocalDateAdapter {
        @FromJson
        fun fromJson(value: String?) =
            value?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

        @ToJson
        fun toJson(value: LocalDate?) =
            value?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}