package bav.astrobirthday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.UserRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(
    private val solarPlanetsRepository: SolarPlanetsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _solarPlanets: MutableLiveData<List<PlanetAndInfo>> = MutableLiveData()
    val solarPlanets: LiveData<List<PlanetAndInfo>> = _solarPlanets

    init {
        viewModelScope.launch {
            solarPlanetsRepository.planetsFlow.combine(userRepository.sortSolarPlanetsByDateFlow) { solarPlanets, isByDate ->
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