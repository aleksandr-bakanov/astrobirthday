package bav.astrobirthday.ui.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import bav.astrobirthday.utils.getPlanetType
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlanetViewModel(
    private val preferences: UserPreferences,
    private val database: PlanetDao,
    private val planetName: String
) : ViewModel() {

    private val _planet = MutableLiveData<PlanetDescription>()
    val planet: LiveData<PlanetDescription> = _planet

    init {
        val neighboursFlow = if (planetName in Config.solarPlanets)
            database.getByNames(Config.solarPlanets)
        else
            database.getByNamesLike("${planetName.substringBeforeLast(" ")}%")
        combine(
            database.getByName(planetName),
            neighboursFlow,
            preferences.birthdayFlow.filterNotNull()
        ) { planet, neighbours, userBirthday ->
            PlanetDescription(
                planet = planet,
                ageOnPlanet = getAgeOnPlanet(userBirthday, planet.pl_orbper),
                nearestBirthday = getNearestBirthday(userBirthday, planet.pl_orbper),
                planetType = getPlanetType(planet.pl_name),
                neighbours = neighbours
            )
        }
            .onEach(_planet::setValue)
            .launchIn(viewModelScope)
    }

    fun toggleFavorite() = viewModelScope.launch {
        val p = planet.value?.planet ?: return@launch
        database.setFavorite(p.pl_name!!, !p.is_favorite)
    }
}