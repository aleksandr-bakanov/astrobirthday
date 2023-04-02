package bav.astrobirthday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.UserRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeUiState(
    val planets: List<PlanetAndInfo>,
    val isByDate: Boolean
)

class HomeViewModel(
    private val solarPlanetsRepository: SolarPlanetsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<HomeUiState> = MutableLiveData()
    val uiState: LiveData<HomeUiState> = _uiState

    val events: LiveData<Event>
        get() = _events
    private val _events = SingleLiveEvent<Event>()

    init {
        viewModelScope.launch {
            solarPlanetsRepository.planetsFlow.combine(userRepository.sortSolarPlanetsByDateFlow) { solarPlanets, isByDate ->
                HomeUiState(
                    planets = if (isByDate)
                        solarPlanets.sortedBy { description -> description.nearestBirthday }
                    else
                        solarPlanets,
                    isByDate = isByDate
                )
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun goToPlanet(planetName: String) {
        _events.value = Event.GoToPlanet(planetName)
    }

    fun toggleSortSolarPlanetsByDate() {
        viewModelScope.launch {
            _uiState.value?.isByDate?.let {
                userRepository.setSortSolarPlanetsByDate(it.not())
            }
        }
    }

    sealed class Event {
        class GoToPlanet(val planetName: String) : Event()
    }
}