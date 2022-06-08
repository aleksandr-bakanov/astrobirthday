package bav.astrobirthday.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PlanetAndInfoDTO(
    @Embedded val planet: PlanetDTO,

    @Relation(
        parentColumn = "pl_name",
        entityColumn = "name"
    )
    val info: PlanetUserInfoDTO? = null
)