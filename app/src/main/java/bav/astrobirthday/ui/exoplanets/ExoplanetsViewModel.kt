package bav.astrobirthday.ui.exoplanets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.data.entities.SortableColumn
import bav.astrobirthday.utils.toPlanetDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

class ExoplanetsViewModel(
    private val getExoplanets: GetExoplanets
) : ViewModel() {

    private val sorting = MutableStateFlow(PlanetSorting(SortableColumn.ID, SortOrder.ASC))
    private val searchRequest = MutableStateFlow("")

    val planetsList: Flow<PagingData<PlanetDescription>> =
        combine(sorting, searchRequest) { sortBy, search ->
            Pager(PagingConfig(pageSize = 20)) {
                getExoplanets.getSource(sortBy, search)
            }
        }
            .flatMapLatest { it.flow }
            .mapLatest { data ->
                data.map { it.toPlanetDescription() }
            }
            .cachedIn(viewModelScope)

    fun changeSorting(sortBy: PlanetSorting) {
        sorting.value = sortBy
    }

    fun setSearchRequest(request: String) {
        searchRequest.value = request
    }
}