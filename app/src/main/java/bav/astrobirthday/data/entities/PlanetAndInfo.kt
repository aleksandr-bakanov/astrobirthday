package bav.astrobirthday.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PlanetAndInfo(
    @Embedded val planet: Planet,

    @Relation(
        parentColumn = "pl_name",
        entityColumn = "name"
    )
    val info: PlanetUserInfo? = null
)