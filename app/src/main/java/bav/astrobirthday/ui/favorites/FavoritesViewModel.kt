package bav.astrobirthday.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import bav.astrobirthday.data.entities.Column
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

class FavoritesViewModel(
    private val getFavorites: GetFavorites,
    private val database: PlanetDao
) : ViewModel() {

    private val sorting = MutableStateFlow(PlanetSorting(Column.NAME, SortOrder.ASC))

    val planetsList: Flow<PagingData<PlanetAndInfo>> = sorting
        .flatMapLatest { sortBy ->
            Pager(PagingConfig(pageSize = 20)) {
                getFavorites.getSource(sortBy)
            }.flow
        }.mapLatest { data ->
            data.map { it.toDomain() }
        }
        .cachedIn(viewModelScope)

    suspend fun countFavorites(): Int {
        return database.countFavoritePlanets()
    }

    fun changeSorting(sortBy: PlanetSorting) {
        sorting.value = sortBy
    }
}