package bav.astrobirthday.ui.favorites

import androidx.lifecycle.ViewModel
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull

class FavoritesViewModel(
    private val solarPlanetsRepository: SolarPlanetsRepository,
    private val getFavorites: GetFavorites,
    private val database: PlanetDao
) : ViewModel() {

    val planetsList: Flow<List<PlanetAndInfo>> = combine(
        getFavorites.getSource(),
        solarPlanetsRepository.planetsFlow
    ) { exoplanets, solar ->
        solar.filter { it.isFavorite }.plus(exoplanets.map { it.toDomain() })
    }

    suspend fun countFavorites(): Int {
        val exoplanetsAmount = database.countFavoritePlanets()
        val solarAmount = solarPlanetsRepository.planetsFlow.firstOrNull()
            ?.filter { it.isFavorite }?.size ?: 0
        return exoplanetsAmount + solarAmount
    }

}