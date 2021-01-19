package bav.astrobirthday.ui.exoplanets

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import bav.astrobirthday.data.entities.PLANETS_TABLE
import bav.astrobirthday.data.entities.PLANET_USER_INFO_TABLE
import bav.astrobirthday.data.entities.PlanetAndInfo
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.local.PlanetDao

class GetExoplanets(private val database: PlanetDao) {

    fun getSource(sort: PlanetSorting, searchRequest: String): PagingSource<Int, PlanetAndInfo> {
        val searchQuery = if (searchRequest.isBlank()) "" else "AND pl_name LIKE '%$searchRequest%'"
        val query = SimpleSQLiteQuery(
            """
                SELECT * FROM $PLANETS_TABLE 
                LEFT JOIN $PLANET_USER_INFO_TABLE
                ON pl_name = name
                WHERE id > 14 
                $searchQuery 
                ORDER BY ${sort.column} ${sort.order}
            """.trimIndent()
        )
        return database.planetsByUidOrder(query)
    }
}