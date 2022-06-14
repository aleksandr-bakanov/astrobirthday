package bav.astrobirthday.domain

import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.domain.model.PlanetUserInfo
import kotlinx.coroutines.flow.Flow

interface SolarPlanetsRepository {
    val planetsFlow: Flow<List<PlanetAndInfo>>
    suspend fun setPlanets(planets: List<PlanetUserInfo>)
    suspend fun setFavorite(name: String, isFavorite: Boolean)
}