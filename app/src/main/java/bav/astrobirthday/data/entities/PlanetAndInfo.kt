package bav.astrobirthday.data.entities

import androidx.annotation.StringRes
import androidx.room.Embedded
import androidx.room.Relation
import bav.astrobirthday.R

enum class SortableColumn(val column: String, @StringRes val resId: Int) {
    NAME("pl_name", R.string.sort_by_name),
    AGE("age", R.string.sort_by_age),
    BIRTHDAY("birthday", R.string.sort_by_birthday)
}

enum class SortOrder(@StringRes val resId: Int) {
    DESC(R.string.sort_by_desc),
    ASC(R.string.sort_by_asc)
}

data class PlanetSorting(val column: SortableColumn, val order: SortOrder)

data class PlanetFilter(
    val distanceFrom: Float = 0f,
    val distanceTo: Float = 6780.0f,
    val periodFrom: Float = 0f,
    val periodTo: Float = 7300010.0f
)

data class PlanetAndInfo(
    @Embedded val planet: Planet,

    @Relation(
        parentColumn = "pl_name",
        entityColumn = "name"
    )
    val info: PlanetUserInfo? = null
)