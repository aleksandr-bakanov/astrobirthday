package bav.astrobirthday.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Column
import bav.astrobirthday.data.entities.PlanetFilter.FilterFromTo
import bav.astrobirthday.data.entities.PlanetFilters
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.data.entities.isDefault
import bav.astrobirthday.ui.common.Resources.String
import bav.astrobirthday.ui.common.ViewEvent
import bav.astrobirthday.ui.exoplanets.GetExoplanets
import bav.astrobirthday.ui.filter.FilterViewModel.FilterEvent.ApplyChanges
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach

class FilterViewModel(
    private val getExoplanets: GetExoplanets,
    initialFilter: PlanetFilters,
    initialSorting: PlanetSorting
) : ViewModel() {

    val state: LiveData<FilterViewState>
        get() = _state
    private val _state = MutableLiveData<FilterViewState>()

    val events: LiveData<FilterEvent>
        get() = _events
    private val _events = MutableLiveData<FilterEvent>()

    private val filtersFlow = MutableStateFlow(initialFilter)
    private val sortingFlow = MutableStateFlow(initialSorting)

    private val sortingItems = Column.values().flatMap { column ->
        SortOrder.values().reversed().map { order ->
            val label = String.FormatRes(R.string.pattern_join, column.resId, order.resId)
            label to PlanetSorting(column, order)
        }
    }

    init {
        val items = mapAdapterItems(initialFilter, initialSorting)
        _state.value = mapState(items, initialFilter, initialSorting)
        combine(filtersFlow, sortingFlow) { filterBy, sortBy -> filterBy to sortBy }
            .debounce(250L)
            .mapLatest { (filterBy, sortBy) ->
                val updatedItems = mapAdapterItems(filterBy, sortBy)
                mapState(updatedItems, filterBy, sortBy, getExoplanets.getCount(filterBy))
            }
            .onEach(_state::setValue)
            .launchIn(viewModelScope)
    }

    private fun mapState(
        adapterItems: List<FilterItems>,
        filterBy: PlanetFilters,
        sortBy: PlanetSorting,
        count: Int = 0
    ) = FilterViewState(
        adapterItems = adapterItems,
        clearVisible = filterBy.isDefault.not() || sortBy.isDefault.not(),
        applyText = if (count == 0) {
            String.Res(R.string.apply_button_title)
        } else {
            String.Res(R.string.apply_button_title_pattern, count)
        }
    )

    private fun mapAdapterItems(filterBy: PlanetFilters, sortBy: PlanetSorting): List<FilterItems> {
        return listOf(
            FilterItems.Header(R.string.filter_sorting_header),
            FilterItems.createSelect(
                sortingItems,
                sortingItems.indexOfFirst { it.second == sortBy }
            ),
            FilterItems.Divider(),
            FilterItems.Header(R.string.filter_distance_header),
            FilterItems.FromTo(Column.DISTANCE, filterBy.filters[Column.DISTANCE]),
            FilterItems.Header(R.string.filter_period_header),
            FilterItems.FromTo(Column.PERIOD, filterBy.filters[Column.PERIOD]),
            FilterItems.Header(R.string.filter_planet_radius_header),
            FilterItems.FromTo(Column.PLANET_RADIUS, filterBy.filters[Column.PLANET_RADIUS]),
            FilterItems.Header(R.string.filter_planet_mass_header),
            FilterItems.FromTo(Column.PLANET_MASS, filterBy.filters[Column.PLANET_MASS]),
            FilterItems.Header(R.string.filter_star_radius_header),
            FilterItems.FromTo(Column.STAR_RADIUS, filterBy.filters[Column.STAR_RADIUS]),
            FilterItems.Header(R.string.filter_star_mass_header),
            FilterItems.FromTo(Column.STAR_MASS, filterBy.filters[Column.STAR_MASS]),
        )
    }

    fun onFromToChanged(column: Column, filter: FilterFromTo) {
        val filters = filtersFlow.value.filters.toMutableMap()
        filters[column] = filter
        filtersFlow.value = PlanetFilters(filters)
    }

    fun onSelectSorting(selection: PlanetSorting) {
        sortingFlow.value = selection
    }

    fun onApplyClicked() {
        _events.value = ApplyChanges(sortingFlow.value, filtersFlow.value)
    }

    fun clearFilter() {
        filtersFlow.value = PlanetFilters()
        sortingFlow.value = PlanetSorting()
    }

    data class FilterViewState(
        val adapterItems: List<FilterItems>,
        val clearVisible: Boolean,
        val applyText: String
    )

    sealed class FilterEvent : ViewEvent() {
        data class ApplyChanges(val sortBy: PlanetSorting, val filterBy: PlanetFilters) :
            FilterEvent()
    }
}
