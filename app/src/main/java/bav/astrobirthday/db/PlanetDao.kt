package bav.astrobirthday.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planets")
    fun getAll(): List<Planet>

    @Query("SELECT * FROM planets WHERE uid = :id")
    fun getById(id: Int): Planet
}