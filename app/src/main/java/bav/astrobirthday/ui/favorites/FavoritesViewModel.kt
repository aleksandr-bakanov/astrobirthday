package bav.astrobirthday.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.local.PlanetDao

class FavoritesViewModel(
    private val database: PlanetDao
) : ViewModel() {

    val planetsList: LiveData<PagedList<Planet>> = database.getFavoritePlanets().toLiveData(
        pageSize = 20
    )
}