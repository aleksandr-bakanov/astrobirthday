package bav.astrobirthday.data

import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.entities.PlanetUserInfoDTO
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.UserRepository
import bav.astrobirthday.domain.model.PlanetUserInfo
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class SyncPlanetsInfo(
    private val userRepository: UserRepository,
    private val solarPlanetsRepository: SolarPlanetsRepository,
    private val planetDao: PlanetDao
) {

    suspend fun sync() {
        val birthday = userRepository.birthdayFlow.firstOrNull() ?: return

        syncSolarPlanets(birthday)

        val count = planetDao.countPlanets()
        for (i in 0..count step BATCH_SIZE) {
            sync(birthday, i, BATCH_SIZE)
        }
        sync(birthday, count % BATCH_SIZE, BATCH_SIZE)
    }

    private suspend fun syncSolarPlanets(birthday: LocalDate) {
        val currentInfo = solarPlanetsRepository.planetsFlow.firstOrNull()
        solarPlanetsRepository.setPlanets(
            Config.solarPlanets.map { planet ->
                val infoFromSharedPrefs =
                    currentInfo?.find { it.planet.planetName == planet.planetName }
                val infoFromDB = planetDao.getByName(planet.planetName).firstOrNull()
                PlanetUserInfo(
                    name = planet.planetName,
                    isFavorite = infoFromSharedPrefs?.isFavorite ?: infoFromDB?.info?.is_favorite
                    ?: false,
                    ageOnPlanet = getAgeOnPlanet(birthday, planet.planetOrbitalPeriod),
                    nearestBirthday = getNearestBirthday(
                        birthday,
                        planet.planetName,
                        planet.planetOrbitalPeriod
                    )
                )
            }
        )
    }

    private suspend fun sync(birthday: LocalDate, start: Int, count: Int) {
        val planets = planetDao.getPlanetsChunked(start, count)
        planetDao.syncInfo(
            updates = planets.map { planet ->
                PlanetUserInfoDTO(
                    planet.pl_name,
                    planet.is_favorite ?: false,
                    getAgeOnPlanet(birthday, planet.pl_orbper),
                    getNearestBirthday(birthday, planet.pl_name, planet.pl_orbper)
                )
            }
        )
    }

    private companion object {
        const val BATCH_SIZE = 256
    }
}