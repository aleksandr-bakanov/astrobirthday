package bav.astrobirthday.ui.exoplanets

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import bav.astrobirthday.data.entities.PLANETS_TABLE
import bav.astrobirthday.data.entities.PLANET_USER_INFO_TABLE
import bav.astrobirthday.data.entities.PlanetAndInfo
import bav.astrobirthday.data.entities.PlanetFilter.FilterFromTo
import bav.astrobirthday.data.entities.PlanetFilters
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.local.PlanetDao

class GetExoplanets(private val database: PlanetDao) {

    fun getSource(
        sort: PlanetSorting,
        searchRequest: String,
        filter: PlanetFilters
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
                ORDER BY ${sort.column.columnName} ${sort.order}
            """.trimIndent()
        )
        return database.planetsByUidOrder(query)
    }

    suspend fun getCount(filter: PlanetFilters): Int {
        val query = SimpleSQLiteQuery(
            """
                SELECT COUNT(id) FROM $PLANETS_TABLE 
                WHERE id > 14 
                ${getFilterQuery(filter)}
            """.trimIndent()
        )
        return database.countPlanetsWithFilter(query)
    }

    private fun getFilterQuery(filterBy: PlanetFilters): String {
        val sb = StringBuilder()
        filterBy.filters.forEach { (column, filter) ->
            when (filter) {
                is FilterFromTo -> {
                    sb.append("AND (${column.columnName} >= ${filter.from} OR ${column.columnName} IS NULL) ")
                    sb.append("AND (${column.columnName} <= ${filter.to}  OR ${column.columnName} IS NULL) ")
                }
            }
        }
        return sb.toString()
    }
}