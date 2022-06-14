package bav.astrobirthday.data

import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.domain.model.PlanetUserInfo
import bav.astrobirthday.utils.getPlanetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class SolarPlanetsRepositoryImpl(
    private val solarPlanetsDataSource: SolarPlanetsDataSource
) : SolarPlanetsRepository {

    override val planetsFlow: Flow<List<PlanetAndInfo>> =
        solarPlanetsDataSource.planetsInfoFlow.mapLatest { planetUserInfos ->
            planetUserInfos.map { userInfo ->
                PlanetAndInfo(
                    planet = Config.solarPlanets.find { it.planetName == userInfo.name }!!,
                    isFavorite = userInfo.isFavorite,
                    ageOnPlanet = userInfo.ageOnPlanet,
                    nearestBirthday = userInfo.nearestBirthday,
                    planetType = getPlanetType(userInfo.name)
                )
            }
        }

    override suspend fun setPlanets(planets: List<PlanetUserInfo>) {
        solarPlanetsDataSource.setPlanets(planets)
    }

    override suspend fun setFavorite(name: String, isFavorite: Boolean) {
        solarPlanetsDataSource.setFavorite(name, isFavorite)
    }
}