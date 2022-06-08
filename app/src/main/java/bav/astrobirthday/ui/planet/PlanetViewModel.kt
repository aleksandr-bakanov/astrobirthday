package bav.astrobirthday.ui.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.entities.toDomain
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.getPlanetType
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlanetViewModel(
    private val database: PlanetDao,
    private val planetName: String
) : ViewModel() {

    private val _planet = MutableLiveData<PlanetAndInfo>()
    val planet: LiveData<PlanetAndInfo> = _planet

    init {
        val neighboursFlow = if (planetName in Config.solarPlanets)
            database.getByNames(Config.solarPlanets)
        else
            database.getByNamesLike("${planetName.substringBeforeLast(" ")} %")
        combine(
            database.getByName(planetName),
            neighboursFlow
        ) { planetAndInfo, neighbours ->
            val (planet, info) = planetAndInfo
            PlanetAndInfo(
                planet = planet.toDomain(),
                isFavorite = info?.is_favorite ?: false,
                ageOnPlanet = info?.age,
                nearestBirthday = info?.birthday,
                planetType = getPlanetType(planet.pl_name),
                neighbours = neighbours.map { it.planet.toDomain() }
            )
        }
            .onEach(_planet::setValue)
            .launchIn(viewModelScope)
    }

    fun toggleFavorite() = viewModelScope.launch {
        val desc = planet.value ?: return@launch
        database.setFavorite(desc.planet.planetName, !desc.isFavorite)
    }
}