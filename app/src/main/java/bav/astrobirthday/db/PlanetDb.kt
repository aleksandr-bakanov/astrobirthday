package bav.astrobirthday.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Planet::class], version = 1)
@TypeConverters(Converters::class)
abstract class PlanetDb : RoomDatabase() {
    abstract fun planetDao(): PlanetDao
}