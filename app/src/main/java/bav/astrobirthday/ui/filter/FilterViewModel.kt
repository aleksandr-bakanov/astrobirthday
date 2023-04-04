package bav.astrobirthday.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.common.SingleLiveEvent
import bav.astrobirthday.data.entities.Column
import bav.astrobirthday.data.entities.PlanetFilter.FilterFromTo
import bav.astrobirthday.data.entities.PlanetFilters
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.ui.common.ViewEvent
import bav.astrobirthday.ui.filter.FilterViewModel.FilterEvent.ApplyChanges
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach

data class FilterViewState(
    val items: List<PlanetSorting>,
    val selectIndex: Int
)

class FilterViewModel(
    initialFilter: PlanetFilters,
    initialSorting: PlanetSorting
) : ViewModel() {

    val state: LiveData<FilterViewState>
        get() = _state
    private val _state = MutableLiveData<FilterViewState>()

    val events: LiveData<FilterEvent>
        get() = _events
    private val _events = SingleLiveEvent<FilterEvent>()

    private val filtersFlow = MutableStateFlow(initialFilter)
    private val sortingFlow = MutableStateFlow(initialSorting)

    init {
        _state.value = getState(initialSorting)
        combine(filtersFlow, sortingFlow) { filterBy, sortBy -> filterBy to sortBy }
            .mapLatest { (filterBy, sortBy) ->
                getState(sortBy)
            }
            .onEach(_state::setValue)
            .launchIn(viewModelScope)
    }

    private fun getState(
        sortBy: PlanetSorting,
    ): FilterViewState {
        var selectedIndex = 0
        val items = Column.values().mapIndexed { index, column ->
            if (column.columnName == sortBy.column.columnName) {
                selectedIndex = index
                sortBy
            } else {
                PlanetSorting(column, SortOrder.ASC)
            }
        }
        return FilterViewState(
            items = items,
            selectIndex = selectedIndex
        )
    }

    fun onFromToChanged(column: Column, filter: FilterFromTo) {
        val filters = filtersFlow.value.filters.toMutableMap()
        filters[column] = filter
        filtersFlow.value = PlanetFilters(filters)
    }

    fun onSelectSorting(selection: PlanetSorting) {
        sortingFlow.value = selection
        _events.value = ApplyChanges(sortingFlow.value, filtersFlow.value)
    }

    fun onApplyClicked() {
        _events.value = ApplyChanges(sortingFlow.value, filtersFlow.value)
    }

    fun clearFilter() {
        filtersFlow.value = PlanetFilters()
        sortingFlow.value = PlanetSorting()
    }

    fun goBack() {
        _events.value = FilterEvent.GoBack()
    }

    sealed class FilterEvent : ViewEvent() {
        data class ApplyChanges(val sortBy: PlanetSorting, val filterBy: PlanetFilters) :
            FilterEvent()

        class GoBack : FilterEvent()
    }
}
