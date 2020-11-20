package bav.astrobirthday.data.repository

import bav.astrobirthday.data.local.PlanetDao
import bav.astrobirthday.utils.performGetOperation

class PlanetRepository(
    private val localDataSource: PlanetDao
) {
    fun getPlanets(names: List<String>) = performGetOperation(
        databaseQuery = { localDataSource.getByNames(names) }
    )

    fun getPlanet(name: String) = performGetOperation(
        databaseQuery = { localDataSource.getByName(name) }
    )
}