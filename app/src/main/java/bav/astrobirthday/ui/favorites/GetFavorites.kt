package bav.astrobirthday.ui.favorites

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import bav.astrobirthday.data.entities.PLANETS_TABLE
import bav.astrobirthday.data.entities.PLANET_USER_INFO_TABLE
import bav.astrobirthday.data.entities.PlanetAndInfoDTO
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.local.PlanetDao

class GetFavorites(private val database: PlanetDao) {

    fun getSource(sort: PlanetSorting): PagingSource<Int, PlanetAndInfoDTO> {
        val query = SimpleSQLiteQuery(
            """
                SELECT * FROM $PLANETS_TABLE 
                INNER JOIN $PLANET_USER_INFO_TABLE
                ON pl_name = name
                WHERE  is_favorite = 1
                ORDER BY ${sort.column} ${sort.order}
            """.trimIndent()
        )
        return database.getFavoritePlanets(query)
    }
}