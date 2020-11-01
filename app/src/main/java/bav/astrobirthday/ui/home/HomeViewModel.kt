package bav.astrobirthday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bav.astrobirthday.common.PlanetDescription
import bav.astrobirthday.common.PlanetType
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.db.PlanetDao
import java.time.LocalDate

class HomeViewModel(
    private val preferences: Preferences,
    private val planetDao: PlanetDao
) : ViewModel() {

    private val _birthdayDate = MutableLiveData<LocalDate?>().apply {
        value = preferences.userBirthday
    }
    val birthdayDate: LiveData<LocalDate?> = _birthdayDate

    private val _solarPlanets = MutableLiveData<List<PlanetDescription>>().apply {
        value = preferences.userBirthday?.let {
            listOf(
                PlanetDescription("Mercury", 90, it.plusDays(9), PlanetType.MERCURY),
                PlanetDescription("Venus", 80, it.plusDays(8), PlanetType.VENUS),
                PlanetDescription("Earth", 70, it.plusDays(7), PlanetType.EARTH),
                PlanetDescription("Mars", 60, it.plusDays(6), PlanetType.MARS),
                PlanetDescription("Jupiter", 50, it.plusDays(5), PlanetType.JUPITER),
                PlanetDescription("Saturn", 40, it.plusDays(4), PlanetType.SATURN),
                PlanetDescription("Uranus", 30, it.plusDays(3), PlanetType.URANUS),
                PlanetDescription("Neptune", 20, it.plusDays(2), PlanetType.NEPTUNE),
                PlanetDescription("Pluto", 10, it.plusDays(1), PlanetType.PLUTO)
            )
        } ?: emptyList()
    }
    val solarPlanets: LiveData<List<PlanetDescription>> = _solarPlanets
}