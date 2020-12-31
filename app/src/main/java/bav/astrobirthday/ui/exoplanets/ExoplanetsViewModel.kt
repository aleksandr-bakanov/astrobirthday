package bav.astrobirthday.ui.exoplanets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.toPlanetDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExoplanetsViewModel(
    private val preferences: UserPreferences,
    private val database: PlanetDao
) : ViewModel() {

    val planetsList: Flow<PagingData<PlanetDescription>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        database.planetsByUidOrder()
    }.flow
        .map { data ->
            data.map {
                it.toPlanetDescription(preferences.userBirthday!!)
            }
        }
        .cachedIn(viewModelScope)
}