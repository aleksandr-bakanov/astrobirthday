package bav.astrobirthday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.domain.UserRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: PlanetDao,
    private val repository: UserRepository
) : ViewModel() {

    private val _solarPlanets: MutableLiveData<List<PlanetAndInfo>> = MutableLiveData()
    val solarPlanets: LiveData<List<PlanetAndInfo>> = _solarPlanets

    private val solarPlanetsFlow: Flow<List<PlanetAndInfo>> =
        database.getByNames(Config.solarPlanets)
            .map { planets -> planets.map { it.toDomain() } }

    init {
        viewModelScope.launch {
            solarPlanetsFlow.combine(repository.sortSolarPlanetsByDateFlow) { solarPlanets, isByDate ->
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