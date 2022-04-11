package bav.astrobirthday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.toPlanetDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: PlanetDao,
    private val preferences: UserPreferences
) : ViewModel() {

    private val _solarPlanets: MutableLiveData<List<PlanetDescription>> = MutableLiveData()
    val solarPlanets: LiveData<List<PlanetDescription>> = _solarPlanets

    private val solarPlanetsFlow: Flow<List<PlanetDescription>> =
        database.getByNames(Config.solarPlanets)
            .map { planets -> planets.map { it.toPlanetDescription() } }

    init {
        viewModelScope.launch {
            solarPlanetsFlow.combine(preferences.sortSolarPlanetsByDateFlow) { solarPlanets, isByDate ->
                if (isByDate)
                    solarPlanets.sortedBy { description -> description.nearestBirthday }
                else
                    solarPlanets
            }.collect {
                _solarPlanets.value = it
            }
        }
    }
}