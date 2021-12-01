package bav.astrobirthday.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.coroutines.flow.Flow
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
    private val FLEXIBLE_KEY = booleanPreferencesKey("flexible")
    private val birthdayStoreFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[BIRTHDAY_KEY]
        }
    override val birthdayFlow: Flow<LocalDate?> =
        birthdayStoreFlow.map { it?.let { localDateAdapter.fromJson(it) } }

    override val userBirthday: LocalDate?
        get() = runBlocking { birthdayFlow.firstOrNull() }

    override suspend fun setBirthday(value: LocalDate?) {
        dataStore.edit { settings ->
            settings[BIRTHDAY_KEY] = localDateAdapter.toJson(value) ?: ""
        }
    }

    override fun setImmediate(value: Boolean) {
        runBlocking {
            dataStore.edit { settings ->
                settings[IMMEDIATE_KEY] = value
            }
        }
    }

    override fun getImmediate(): Boolean {
        return runBlocking { dataStore.data.firstOrNull()?.get(IMMEDIATE_KEY) ?: false }
    }

    override fun setFlexible(value: Boolean) {
        runBlocking {
            dataStore.edit { settings ->
                settings[FLEXIBLE_KEY] = value
            }
        }
    }

    override fun getFlexible(): Boolean {
        return runBlocking { dataStore.data.firstOrNull()?.get(FLEXIBLE_KEY) ?: false }
    }

    inner class LocalDateAdapter {
        @FromJson
        fun fromJson(value: String?) =
            value?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

        @ToJson
        fun toJson(value: LocalDate?) =
            value?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}