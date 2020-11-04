package bav.astrobirthday.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planets")
    suspend fun getAll(): List<Planet>

    @Query("SELECT * FROM planets WHERE uid = :id")
    suspend fun getById(id: Int): Planet

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planet: Planet)
}