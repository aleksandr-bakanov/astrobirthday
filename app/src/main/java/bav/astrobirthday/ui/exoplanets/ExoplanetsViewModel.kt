package bav.astrobirthday.ui.exoplanets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.repository.PlanetRepository

class ExoplanetsViewModel(
    private val preferences: UserPreferences,
    private val planetRepository: PlanetRepository
) : ViewModel() {

    val planetsList: LiveData<PagedList<Planet>> = planetRepository.getPlanets()
}