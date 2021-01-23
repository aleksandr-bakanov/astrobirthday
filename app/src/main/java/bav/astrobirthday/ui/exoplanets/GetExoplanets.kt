package bav.astrobirthday.ui.exoplanets

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import bav.astrobirthday.data.entities.PLANETS_TABLE
import bav.astrobirthday.data.entities.PLANET_USER_INFO_TABLE
import bav.astrobirthday.data.entities.PlanetAndInfo
import bav.astrobirthday.data.entities.PlanetFilter
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.local.PlanetDao

class GetExoplanets(private val database: PlanetDao) {

    fun getSource(
        sort: PlanetSorting,
        searchRequest: String,
        filter: PlanetFilter
    ): PagingSource<Int, PlanetAndInfo> {
        val searchQuery = if (searchRequest.isBlank()) "" else "AND pl_name LIKE '%$searchRequest%'"
        val filterQuery = getFilterQuery(filter)
        val query = SimpleSQLiteQuery(
            """
                SELECT * FROM $PLANETS_TABLE 
                LEFT JOIN $PLANET_USER_INFO_TABLE
                ON pl_name = name
                WHERE id > 14 
                $searchQuery 
                $filterQuery 
                ORDER BY ${sort.column} ${sort.order}
            """.trimIndent()
        )
        return database.planetsByUidOrder(query)
    }

    suspend fun getCount(filter: PlanetFilter): Int {
        val query = SimpleSQLiteQuery(
            """
                SELECT COUNT(id) FROM $PLANETS_TABLE 
                WHERE id > 14 
                ${getFilterQuery(filter)}
            """.trimIndent()
        )
        return database.countPlanetsWithFilter(query)
    }

    private fun getFilterQuery(filter: PlanetFilter): String {
        return """
            AND (sy_dist >= ${filter.distanceFrom} OR sy_dist IS NULL) 
            AND (sy_dist <= ${filter.distanceTo} OR sy_dist IS NULL) 
            AND (pl_orbper >= ${filter.periodFrom} OR pl_orbper IS NULL) 
            AND (pl_orbper <= ${filter.periodTo} OR pl_orbper IS NULL) 
        """.trimIndent()
    }
}