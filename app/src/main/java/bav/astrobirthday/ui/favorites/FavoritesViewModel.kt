package bav.astrobirthday.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.data.repository.PlanetRepository

class FavoritesViewModel(
    private val database: PlanetDao
) : ViewModel() {

    val planetsList: LiveData<PagedList<Planet>> = database.getFavoritePlanets().toLiveData(
        pageSize = 20
    )
}