package bav.astrobirthday.ui.planet

import androidx.lifecycle.*
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.getAgeOnPlanet
import bav.astrobirthday.utils.getNearestBirthday
import bav.astrobirthday.utils.getPlanetType
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate

class PlanetViewModel(
    private val preferences: Preferences,
    private val database: PlanetDao,
) : ViewModel() {

    private val _planetName = MutableLiveData<String>()

    private val _planet = _planetName.switchMap { name ->
        liveData(Dispatchers.IO) {
            emit(database.sGetByName(name))
        }
    }.map { planet ->
        val userBirthday = preferences.userBirthday ?: LocalDate.now()
        PlanetDescription(
            planet = planet,
            ageOnPlanet = getAgeOnPlanet(userBirthday, planet.pl_orbper),
            nearestBirthday = getNearestBirthday(userBirthday, planet.pl_orbper),
            planetType = getPlanetType(planet.pl_name)
        )
    }

    val planet: LiveData<PlanetDescription> = _planet

    fun start(name: String) {
        _planetName.value = name
    }
}