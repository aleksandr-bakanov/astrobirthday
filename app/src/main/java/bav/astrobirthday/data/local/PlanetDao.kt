package bav.astrobirthday.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import bav.astrobirthday.data.entities.Planet
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planets")
    fun getAll(): Flow<List<Planet>>

    @Query("SELECT * FROM planets WHERE id = :id")
    fun getById(id: Int): Flow<Planet>

    @Query("SELECT * FROM planets WHERE pl_name = :name")
    fun getByName(name: String): Flow<Planet>

    @Query("SELECT * FROM planets WHERE pl_name IN (:names) ORDER BY id ASC")
    fun getByNames(names: Collection<String>): Flow<List<Planet>>

    // The Int type parameter tells Room to use a PositionalDataSource
    // object, with position-based loading under the hood.
    @Query("SELECT * FROM planets WHERE id > 14 ORDER BY id ASC")
    fun planetsByUidOrder(): PagingSource<Int, Planet>

    @Query("SELECT * FROM planets WHERE is_favorite = 1")
    fun getFavoritePlanets(): PagingSource<Int, Planet>

    @Query("UPDATE planets SET is_favorite = :isFavorite WHERE pl_name = :name")
    suspend fun setFavorite(name: String, isFavorite: Boolean)
}