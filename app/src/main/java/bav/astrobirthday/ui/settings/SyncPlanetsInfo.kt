package bav.astrobirthday.ui.settings

import bav.astrobirthday.data.UserRepository
import bav.astrobirthday.data.entities.PlanetUserInfo
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class SyncPlanetsInfo(
    private val userRepository: UserRepository,
    private val planetDao: PlanetDao
) {

    suspend fun sync() {
        val birthday = userRepository.birthdayFlow.firstOrNull() ?: return
        val count = planetDao.countPlanets()
        for (i in 0..count step BATCH_SIZE) {
            sync(birthday, i, BATCH_SIZE)
        }
        sync(birthday, count % BATCH_SIZE, BATCH_SIZE)
    }

    private suspend fun sync(birthday: LocalDate, start: Int, count: Int) {
        val planets = planetDao.getPlanetsChunked(start, count)
        planetDao.syncInfo(
            updates = planets.map { planet ->
                PlanetUserInfo(
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