package bav.astrobirthday.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class FavoritesUiState(
    val planets: List<PlanetAndInfo>
)

class FavoritesViewModel(
    private val solarPlanetsRepository: SolarPlanetsRepository,
    private val getFavorites: GetFavorites,
) : ViewModel() {

    private val _uiState: MutableLiveData<FavoritesUiState> = MutableLiveData()
    val uiState: LiveData<FavoritesUiState> = _uiState

    val events: LiveData<Event>
        get() = _events
    private val _events = SingleLiveEvent<Event>()

    private val planetsList: Flow<List<PlanetAndInfo>> = combine(
        getFavorites.getSource(),
        solarPlanetsRepository.planetsFlow
    ) { exoplanets, solar ->
        solar.filter { it.isFavorite }.plus(exoplanets.map { it.toDomain() })
    }

    init {
        viewModelScope.launch {
            planetsList.collect {
                _uiState.value = FavoritesUiState(it)
            }
        }
    }

    fun goToPlanet(planetName: String) {
        _events.value = Event.GoToPlanet(planetName)
    }

    sealed class Event {
        class GoToPlanet(val planetName: String) : Event()
    }

}