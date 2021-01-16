package bav.astrobirthday.data.entities

import androidx.room.Embedded
import androidx.room.Relation

enum class SortableColumn(val column: String) {
    ID("id"),
    NAME("pl_name"),
    AGE("age"),
    BIRTHDAY("birthday")
}

enum class SortOrder {
    DESC, ASC
}

data class PlanetSorting(val column: SortableColumn, val order: SortOrder)

data class PlanetAndInfo(
    @Embedded val planet: Planet,

    @Relation(
        parentColumn = "pl_name",
        entityColumn = "name"
    )
    val info: PlanetUserInfo? = null
)