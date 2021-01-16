package bav.astrobirthday.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import bav.astrobirthday.data.entities.PLANET_USER_INFO_COLUMNS
import bav.astrobirthday.data.entities.PLANET_USER_INFO_TABLE
import java.io.File


object Migrations {

    val MIGRATION_UPDATE_PLANETS = object : Migration(1, DB_VERSION) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Assume user data not changed
        }
    }

    private fun databaseExists(context: Context): Boolean {
        val dbFile = context.getDatabasePath(DB_NAME)
        if (dbFile.exists()) return true
        dbFile.parentFile?.mkdirs()
        return false
    }

    fun copyFromAssets(context: Context, currentVersion: Int, assetPath: String) {
        // https://stackoverflow.com/questions/59634325/createfromasset-migration-but-keep-specific-columns
        val dbExists = databaseExists(context)
        if (!dbExists) {
            // Initial load
            copyAssetFile(context, assetPath)
            return
        }
        if (getDBVersion(context, DB_NAME) >= DB_VERSION) {
            return
        }
        // Update
        // Open and close the original DB so as to checkpoint the WAL file
        val originalDBPath = context.getDatabasePath(DB_NAME)
        val originalDB = SQLiteDatabase.openDatabase(
            originalDBPath.path,
            null,
            SQLiteDatabase.OPEN_READWRITE
        )
        originalDB.close()

        // 1. Rename original database
        val preservedDBName = "preserved_$DB_NAME"
        val preservedDBPath = File(originalDBPath.parentFile, preservedDBName)
        originalDBPath.renameTo(preservedDBPath)

        // 2. Copy the replacement database from the assets folder
        copyAssetFile(context, assetPath)

        // 3. Open the newly copied database
        val copiedDB = SQLiteDatabase.openDatabase(
            originalDBPath.path,
            null,
            SQLiteDatabase.OPEN_READWRITE
        )
        val preservedDB = SQLiteDatabase.openDatabase(
            preservedDBPath.path,
            null,
            SQLiteDatabase.OPEN_READONLY
        )

        // 4. get the original data to be preserved
        val csr: Cursor = preservedDB.query(
            PLANET_USER_INFO_TABLE, PLANET_USER_INFO_COLUMNS,
            null, null, null, null, null
        )
        copiedDB.execSQL(// Copied from PlanetDb_impl
            """
                CREATE TABLE IF NOT EXISTS `planets_user_info` (
                    `name` TEXT NOT NULL, 
                    `is_favorite` INTEGER NOT NULL, 
                    `age` REAL NOT NULL,
                    `birthday` TEXT NOT NULL,
                    PRIMARY KEY(`name`)
                )
            """.trimIndent()
        )

        // 5. Apply preserved data to the newly copied data
        copiedDB.beginTransaction()
        val cv = ContentValues()
        while (csr.moveToNext()) {
            cv.clear()
            for (column in PLANET_USER_INFO_COLUMNS) {
                when (csr.getType(csr.getColumnIndex(column))) {
                    Cursor.FIELD_TYPE_INTEGER -> cv.put(
                        column,
                        csr.getLong(csr.getColumnIndex(column))
                    )
                    Cursor.FIELD_TYPE_STRING -> cv.put(
                        column,
                        csr.getString(csr.getColumnIndex(column))
                    )
                    Cursor.FIELD_TYPE_FLOAT -> cv.put(
                        column,
                        csr.getDouble(csr.getColumnIndex(column))
                    )
                    Cursor.FIELD_TYPE_BLOB -> cv.put(
                        column,
                        csr.getBlob(csr.getColumnIndex(column))
                    )
                }
            }
            copiedDB.insertWithOnConflict(
                PLANET_USER_INFO_TABLE,
                null,
                cv,
                CONFLICT_REPLACE
            )
        }
        copiedDB.setTransactionSuccessful()
        copiedDB.endTransaction()
        csr.close()
        // 6. Cleanup
        copiedDB.close()
        preservedDB.close()
        preservedDBPath.delete()
    }

    private fun copyAssetFile(context: Context, assetPath: String) {
        context.assets.open(assetPath).use { input ->
            context.getDatabasePath(DB_NAME).outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }

    private fun getDBVersion(context: Context, databaseName: String): Int {
        val db = SQLiteDatabase.openDatabase(
            context.getDatabasePath(databaseName).path,
            null,
            SQLiteDatabase.OPEN_READONLY
        )
        return db.version.also {
            db.close()
        }
    }
}