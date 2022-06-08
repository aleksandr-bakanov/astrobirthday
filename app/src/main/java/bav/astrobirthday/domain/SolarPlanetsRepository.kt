package bav.astrobirthday.domain

import bav.astrobirthday.data.entities.PlanetAndInfoDTO
import kotlinx.coroutines.flow.Flow

interface SolarPlanetsRepository {
    val planetsFlow: Flow<List<PlanetAndInfoDTO>>
    suspend fun setFavorite(name: String, isFavorite: Boolean)
}