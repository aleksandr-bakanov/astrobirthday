package bav.astrobirthday.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.planetToPlanetDescription
import java.time.LocalDate

class FavoritesViewModel(
    private val preferences: UserPreferences,
    private val database: PlanetDao
) : ViewModel() {

    val planetsList: LiveData<PagedList<PlanetDescription>> = database.getFavoritePlanets().map {
        planetToPlanetDescription(it, preferences.userBirthday ?: LocalDate.now())
    }.toLiveData(
        pageSize = 20
    )
}