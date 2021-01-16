package bav.astrobirthday.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


object Migrations {

    val MIGRATION_UPDATE_PLANETS = object : Migration(1, DB_VERSION) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Assume user data not changed
        }
    }
}