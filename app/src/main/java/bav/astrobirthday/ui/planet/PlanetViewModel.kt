package bav.astrobirthday.ui.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.toDomain
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlanetViewModel(
    private val solarPlanetsRepository: SolarPlanetsRepository,
    private val database: PlanetDao,
    private val planetName: String
) : ViewModel() {

    private val _planet = MutableLiveData<PlanetAndInfo>()
    val planet: LiveData<PlanetAndInfo> = _planet

    init {
        val neighboursFlow = if (planetName in Config.solarPlanetNames)
            solarPlanetsRepository.planetsFlow
        else
            database.getByNamesLike("${planetName.substringBeforeLast(" ")} %")
                .map { list -> list.map { it.toDomain() } }
        combine(
            if (planetName in Config.solarPlanetNames) {
                solarPlanetsRepository.planetsFlow.mapLatest { list -> list.find { it.planet.planetName == planetName }!! }
            } else {
                database.getByName(planetName).map { it.toDomain() }
            },
            neighboursFlow
        ) { planetAndInfo, neighbours ->
            planetAndInfo.copy(neighbours = neighbours.map { it.planet })
        }
            .onEach(_planet::setValue)
            .launchIn(viewModelScope)
    }

    fun toggleFavorite() = viewModelScope.launch {
        val desc = planet.value ?: return@launch
        if (desc.planet.planetName in Config.solarPlanetNames) {
            solarPlanetsRepository.setFavorite(desc.planet.planetName, !desc.isFavorite)
        } else {
            database.setFavorite(desc.planet.planetName, !desc.isFavorite)
        }
    }
}