package bav.astrobirthday.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bav.astrobirthday.data.entities.Planet

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planets")
    fun getAll(): LiveData<List<Planet>>

    @Query("SELECT * FROM planets WHERE id = :id")
    fun getById(id: Int): LiveData<Planet>

    @Query("SELECT * FROM planets WHERE pl_name = :name")
    fun getByName(name: String): LiveData<Planet>

    @Query("SELECT * FROM planets WHERE pl_name IN (:names) ORDER BY id ASC")
    fun getByNames(names: List<String>): LiveData<List<Planet>>

    // The Int type parameter tells Room to use a PositionalDataSource
    // object, with position-based loading under the hood.
    @Query("SELECT * FROM planets WHERE id > 9 ORDER BY id ASC")
    fun planetsByUidOrder(): DataSource.Factory<Int, Planet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planet: Planet)
}