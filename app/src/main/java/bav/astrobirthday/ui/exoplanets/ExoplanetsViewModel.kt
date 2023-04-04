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
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.PlanetFilters
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.isDefault
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.common.ViewEvent
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel.ExoplanetsEvent.ScrollTo
import bav.astrobirthday.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach

class ExoplanetsViewModel(
    private val getExoplanets: GetExoplanets
) : ViewModel() {

    val filterIcon: LiveData<Int>
        get() = _filterIcon
    private val _filterIcon = MutableLiveData<Int>()

    val sorting = MutableStateFlow(PlanetSorting())
    private val searchRequest = MutableStateFlow("")
    val filtering = MutableStateFlow(PlanetFilters())

    val events: LiveData<ExoplanetsEvent>
        get() = _events
    private val _events = MutableLiveData<ExoplanetsEvent>()

    private val config = PagingConfig(
        pageSize = 100,
        enablePlaceholders = true,
        jumpThreshold = 2000
    )

    private val filtersflow = filtering.onEach { filterBy ->
        if (filterBy.isDefault) {
            _filterIcon.value = R.drawable.sort_icon
        } else {
            _filterIcon.value = R.drawable.sort_icon
        }
    }

    val planetsList: Flow<PagingData<PlanetAndInfo>> =
        combine(sorting, searchRequest, filtersflow) { sortBy, search, filter ->
            Pager(config) {
                getExoplanets.getSource(sortBy, search, filter)
            }
        }
            .flatMapLatest { it.flow }
            .mapLatest { data ->
                data.map { it.toDomain() }
            }
            .onEach { _events.value = ScrollTo(0) }
            .cachedIn(viewModelScope)

    fun setSearchRequest(request: String) {
        searchRequest.value = request
    }

    fun updateParameters(sortBy: PlanetSorting, filterBy: PlanetFilters) {
        sorting.value = sortBy
        filtering.value = filterBy
    }

    sealed class ExoplanetsEvent : ViewEvent() {
        class ScrollTo(val position: Int) : ExoplanetsEvent()
    }
}