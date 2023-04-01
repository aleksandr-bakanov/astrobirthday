package bav.astrobirthday.data

import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.domain.model.PlanetUserInfo
import bav.astrobirthday.utils.getPlanetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.runBlocking

class SolarPlanetsRepositoryImpl(
    private val solarPlanetsDataSource: SolarPlanetsDataSource,
    private val birthdayUpdater: BirthdayUpdater
) : SolarPlanetsRepository {

    override val planetsFlow: Flow<List<PlanetAndInfo>> =
        solarPlanetsDataSource.planetsInfoFlow.mapLatest { planetUserInfos ->
            planetUserInfos.mapNotNull { userInfo ->
                Config.solarPlanets.find { it.planetName == userInfo.name }?.let { planet ->
                    PlanetAndInfo(
                        planet = planet,
                        isFavorite = userInfo.isFavorite,
                        ageOnPlanet = userInfo.ageOnPlanet,
                        nearestBirthday = userInfo.nearestBirthday,
                        planetType = getPlanetType(userInfo.name)
                    )
                }
            }
        }

    init {
        // TODO: this works buggy and doesn't update screen on the first start
        //   after change of Config.solarPlanets. Probably something with flow.
        val planets = runBlocking { planetsFlow.firstOrNull() }
        if (planets?.size != Config.solarPlanets.size) {
            birthdayUpdater.updateBirthdays()
        }
    }

    override suspend fun setPlanets(planets: List<PlanetUserInfo>) {
        solarPlanetsDataSource.setPlanets(planets)
    }

    override suspend fun setFavorite(name: String, isFavorite: Boolean) {
        solarPlanetsDataSource.setFavorite(name, isFavorite)
    }
}