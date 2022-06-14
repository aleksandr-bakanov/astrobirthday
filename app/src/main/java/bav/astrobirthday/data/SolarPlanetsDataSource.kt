package bav.astrobirthday.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import bav.astrobirthday.domain.model.PlanetUserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate as jtLocalDate

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("solar_planets_info")

class SolarPlanetsDataSource(
    private val context: Context
) {

    val planetsInfoFlow: Flow<List<PlanetUserInfo>> = context.dataStore.data
        .map { preferences ->
            preferences[PLANETS_KEY]?.let { serializedPlanets ->
                Json.decodeFromString<List<PlanetUserInfoSerializable>?>(
                    serializedPlanets
                )?.map { it.toDomain() }
            } ?: emptyList()
        }

    suspend fun setPlanets(planets: List<PlanetUserInfo>) {
        context.dataStore.edit { settings ->
            settings[PLANETS_KEY] = Json.encodeToString(planets.map { it.toSerializable() })
        }
    }

    suspend fun setFavorite(name: String, isFavorite: Boolean) {
        context.dataStore.edit { settings ->
            settings[PLANETS_KEY] = Json.encodeToString(
                planetsInfoFlow.firstOrNull()
                    ?.map { if (it.name == name) it.copy(isFavorite = isFavorite) else it }
            )
        }
    }

    companion object {
        private val PLANETS_KEY = stringPreferencesKey("planets")
    }

    @Serializable
    private class PlanetUserInfoSerializable(
        val name: String,
        val isFavorite: Boolean,
        val ageOnPlanet: Double? = null,
        val nearestBirthday: LocalDate? = null
    )

    private fun PlanetUserInfo.toSerializable(): PlanetUserInfoSerializable {
        return PlanetUserInfoSerializable(name, isFavorite, ageOnPlanet, nearestBirthday?.let {
            LocalDate(it.year, it.monthValue, it.dayOfMonth)
        })
    }

    private fun PlanetUserInfoSerializable.toDomain(): PlanetUserInfo {
        return PlanetUserInfo(name, isFavorite, ageOnPlanet, nearestBirthday?.let {
            jtLocalDate.of(it.year, it.monthNumber, it.dayOfMonth)
        })
    }
}