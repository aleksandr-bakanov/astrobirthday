package bav.astrobirthday.data.entities

import androidx.annotation.StringRes
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Column.NAME

enum class Column(val columnName: String, @StringRes val resId: Int) {
    NAME("pl_name", R.string.sort_by_name),
    AGE("age", R.string.sort_by_age),
    BIRTHDAY("birthday", R.string.sort_by_birthday),
    DISTANCE("sy_dist", R.string.sort_by_distance),
    PERIOD("pl_orbper", R.string.sort_by_distance),
    PLANET_MASS("pl_bmasse", R.string.sort_by_planet_mass),
    PLANET_RADIUS("pl_rade", R.string.sort_by_planet_radius),
    STAR_MASS("st_mass", R.string.sort_by_star_mass),
    STAR_RADIUS("st_rad", R.string.sort_by_star_radius),
}

enum class SortOrder(@StringRes val resId: Int) {
    DESC(R.string.sort_by_desc),
    ASC(R.string.sort_by_asc)
}

data class PlanetSorting(val column: Column = NAME, val order: SortOrder = SortOrder.ASC)
val PlanetSorting.isDefault: Boolean
    get() = this == PlanetSorting()

sealed class PlanetFilter {
    data class FilterFromTo(
        val from: Float,
        val to: Float
    ) : PlanetFilter()
}

data class PlanetFilters(
    val filters: Map<Column, PlanetFilter> = Config.defaultFilters
)

val PlanetFilters.isDefault: Boolean
    get() = filters == Config.defaultFilters
