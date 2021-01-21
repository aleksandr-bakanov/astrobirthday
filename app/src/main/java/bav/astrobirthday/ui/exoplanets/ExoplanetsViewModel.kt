package bav.astrobirthday.ui.exoplanets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.entities.PlanetFilter
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.data.entities.SortableColumn
import bav.astrobirthday.ui.common.ViewEvent
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel.ExoplanetsEvent.ScrollTo
import bav.astrobirthday.utils.toPlanetDescription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach

class ExoplanetsViewModel(
    private val getExoplanets: GetExoplanets
) : ViewModel() {

    val sorting = MutableStateFlow(PlanetSorting(SortableColumn.NAME, SortOrder.ASC))
    private val searchRequest = MutableStateFlow("")
    val filtering = MutableStateFlow(PlanetFilter())

    val events: LiveData<ExoplanetsEvent>
        get() = _events
    private val _events = MutableLiveData<ExoplanetsEvent>()

    val planetsList: Flow<PagingData<PlanetDescription>> =
        combine(sorting, searchRequest, filtering) { sortBy, search, filter ->
            Pager(PagingConfig(pageSize = 20)) {
                getExoplanets.getSource(sortBy, search, filter)
            }
        }
            .flatMapLatest { it.flow }
            .mapLatest { data ->
                data.map { it.toPlanetDescription() }
            }
            .onEach { _events.value = ScrollTo(0) }
            .cachedIn(viewModelScope)

    fun changeSorting(sortBy: PlanetSorting) {
        sorting.value = sortBy
    }

    fun setSearchRequest(request: String) {
        searchRequest.value = request
    }

    fun setFilter(filterBy: PlanetFilter) {
        filtering.value = filterBy
    }

    sealed class ExoplanetsEvent : ViewEvent() {
        class ScrollTo(val position: Int) : ExoplanetsEvent()
    }
}