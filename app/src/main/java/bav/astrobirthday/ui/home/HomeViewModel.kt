package bav.astrobirthday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import bav.astrobirthday.utils.getPlanetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class HomeViewModel(
    private val preferences: UserPreferences,
    private val database: PlanetDao
) : ViewModel() {

    private val solarPlanetsFlow: Flow<List<PlanetDescription>> =
        preferences.birthdayFlow.filterNotNull()
            .combine(database.getByNames(Config.solarPlanets)) { birthday, planets ->
                planets.map {
                    PlanetDescription(
                        planet = it,
                        ageOnPlanet = getAgeOnPlanet(birthday, it.pl_orbper),
                        nearestBirthday = getNearestBirthday(birthday, it.pl_orbper),
                        planetType = getPlanetType(it.pl_name) ?: PlanetType.values().random()
                    )
                }
            }

    init {
        viewModelScope.launch {
            solarPlanetsFlow.collect { _solarPlanets.value = it }
        }
    }

    private val _solarPlanets: MutableLiveData<List<PlanetDescription>> = MutableLiveData()
    val solarPlanets: LiveData<List<PlanetDescription>> = _solarPlanets
}