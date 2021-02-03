package bav.astrobirthday.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.data.entities.Column
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.toPlanetDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

class FavoritesViewModel(
    private val getFavorites: GetFavorites,
    private val database: PlanetDao
) : ViewModel() {

    private val sorting = MutableStateFlow(PlanetSorting(Column.NAME, SortOrder.ASC))

    val planetsList: Flow<PagingData<PlanetDescription>> = sorting
        .flatMapLatest { sortBy ->
            Pager(PagingConfig(pageSize = 20)) {
                getFavorites.getSource(sortBy)
            }.flow
        }.mapLatest { data ->
            data.map { it.toPlanetDescription() }
        }
        .cachedIn(viewModelScope)

    suspend fun countFavorites(): Int {
        return database.countFavoritePlanets()
    }

    fun changeSorting(sortBy: PlanetSorting) {
        sorting.value = sortBy
    }
}