package bav.astrobirthday.data.repository

import androidx.paging.toLiveData
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanetRepository(
    private val localDataSource: PlanetDao
) {
    fun getPlanets(names: List<String>) = performGetOperation(
        databaseQuery = { localDataSource.getByNames(names) }
    )

    fun getPlanet(name: String) = performGetOperation(
        databaseQuery = { localDataSource.getByName(name) }
    )

    fun getPlanets() = localDataSource.planetsByUidOrder().toLiveData(
        pageSize = 50, initialLoadKey = 9,
    )
}