package bav.astrobirthday.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.entities.PlanetSyncView
import bav.astrobirthday.data.entities.PlanetUserInfo

const val DB_VERSION = 1
const val DB_NAME = "planets.db"

@Database(
    entities = [Planet::class, PlanetUserInfo::class],
    views = [PlanetSyncView::class],
    version = DB_VERSION
)
@TypeConverters(Converters::class)
abstract class PlanetDb : RoomDatabase() {
    abstract fun planetDao(): PlanetDao

    companion object {
        fun create(context: Context): PlanetDb {
            Migrations.copyFromAssets(context, DB_VERSION, "database/planets.db")
            return Room.databaseBuilder(
                context,
                PlanetDb::class.java, DB_NAME
            )
                .addMigrations(*Migrations.MIGRATIONS.toTypedArray())
                .build()
        }
    }
}