package bav.astrobirthday.ui.filter

import androidx.lifecycle.ViewModel
import bav.astrobirthday.data.entities.PlanetFilter
import bav.astrobirthday.ui.exoplanets.GetExoplanets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach

class FilterViewModel(
    private val getExoplanets: GetExoplanets
) : ViewModel() {

    var filter: PlanetFilter = PlanetFilter()
        set(value) {
        field = value
        distanceFlow.value = filter.distanceFrom to filter.distanceTo
        periodFlow.value = filter.periodFrom to filter.periodTo
    }

    private val distanceFlow = MutableStateFlow(filter.distanceFrom to filter.distanceTo)
    private val periodFlow = MutableStateFlow(filter.periodFrom to filter.periodTo)

    val filteredPlanetsCount: Flow<Int> =
        combine(distanceFlow, periodFlow) { (minDistance, maxDistance), (minPeriod, maxPeriod) ->
            PlanetFilter(
                distanceFrom = minDistance,
                distanceTo = maxDistance,
                periodFrom = minPeriod,
                periodTo = maxPeriod
            )
        }
            .debounce(200L)
            .onEach { filter = it }
            .mapLatest {
                getExoplanets.getCount(it)
            }

    fun setDistanceMax(value: Float) {
        distanceFlow.value = distanceFlow.value.first to value
    }

    fun setDistanceMin(value: Float) {
        distanceFlow.value = value to distanceFlow.value.second
    }

    fun setPeriodMax(value: Float) {
        periodFlow.value = periodFlow.value.first to value
    }

    fun setPeriodMin(value: Float) {
        periodFlow.value = value to periodFlow.value.second
    }
}
