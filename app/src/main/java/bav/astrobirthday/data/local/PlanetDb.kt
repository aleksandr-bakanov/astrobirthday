package bav.astrobirthday.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bav.astrobirthday.data.entities.Planet

@Database(entities = [Planet::class], version = 1)
@TypeConverters(Converters::class)
abstract class PlanetDb : RoomDatabase() {
    abstract fun planetDao(): PlanetDao
}