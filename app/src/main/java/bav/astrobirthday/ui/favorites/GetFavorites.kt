package bav.astrobirthday.ui.favorites

import androidx.sqlite.db.SimpleSQLiteQuery
import bav.astrobirthday.data.entities.PLANETS_TABLE
import bav.astrobirthday.data.entities.PLANET_USER_INFO_TABLE
import bav.astrobirthday.data.entities.PlanetAndInfoDTO
import bav.astrobirthday.data.local.PlanetDao
import kotlinx.coroutines.flow.Flow

class GetFavorites(private val database: PlanetDao) {

    fun getSource(): Flow<List<PlanetAndInfoDTO>> {
        val query = SimpleSQLiteQuery(
            """
                SELECT * FROM $PLANETS_TABLE 
                INNER JOIN $PLANET_USER_INFO_TABLE
                ON pl_name = name
                WHERE is_favorite = 1
            """.trimIndent()
        )
        return database.getFavoritePlanets(query)
    }
}