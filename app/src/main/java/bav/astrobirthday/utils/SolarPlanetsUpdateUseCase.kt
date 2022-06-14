package bav.astrobirthday.utils

import bav.astrobirthday.data.BirthdayUpdater
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.SolarPlanetsRepository
import kotlinx.coroutines.flow.firstOrNull

class SolarPlanetsUpdateUseCase(
    private val repository: SolarPlanetsRepository,
    private val birthdayUpdater: BirthdayUpdater
) {

    suspend fun check() {
        val planets = repository.planetsFlow.firstOrNull()
        if (planets?.size != Config.solarPlanets.size) {
            birthdayUpdater.updateBirthdays()
        }
    }
}