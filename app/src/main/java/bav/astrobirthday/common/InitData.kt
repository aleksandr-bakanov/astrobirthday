package bav.astrobirthday.common

import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.local.PlanetDao
import kotlinx.coroutines.flow.*

class InitData(private val preferences: UserPreferences, private val planetDao: PlanetDao) {

    suspend fun waitForInit(): Boolean {
        val isBirthdayAlreadySetup = preferences.birthdayFlow.firstOrNull() != null
        return if (isBirthdayAlreadySetup) {
            false
        } else {
            combine(
                preferences.birthdayFlow.filterNotNull(),
                planetDao.getByNames(Config.solarPlanets)
            ) { _, planets -> planets }
                .filter { planets -> planets.all { it.info != null } }
                .first()
            true
        }
    }
}