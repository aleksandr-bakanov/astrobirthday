package bav.astrobirthday.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import bav.astrobirthday.data.entities.PlanetAndInfoDTO
import bav.astrobirthday.data.entities.PlanetDTO
import bav.astrobirthday.data.entities.PlanetSyncView
import bav.astrobirthday.data.entities.PlanetUserInfoDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {

    @Transaction
    @Query("SELECT * FROM planets WHERE pl_name = :name")
    fun getByName(name: String): Flow<PlanetAndInfoDTO>

    @Transaction
    @Query("SELECT * FROM planets WHERE pl_name IN (:names) ORDER BY id ASC")
    fun getByNames(names: Collection<String>): Flow<List<PlanetAndInfoDTO>>

    @Transaction
    @Query("SELECT * FROM planets WHERE pl_name LIKE :pattern ORDER BY id ASC")
    fun getByNamesLike(pattern: String): Flow<List<PlanetAndInfoDTO>>

    @Transaction
    @RawQuery(observedEntities = [PlanetDTO::class, PlanetUserInfoDTO::class])
    fun planetsByUidOrder(query: SupportSQLiteQuery): PagingSource<Int, PlanetAndInfoDTO>

    @Transaction
    @RawQuery(observedEntities = [PlanetDTO::class, PlanetUserInfoDTO::class])
    fun getFavoritePlanets(query: SupportSQLiteQuery): Flow<List<PlanetAndInfoDTO>>

    @Query("UPDATE planets_user_info SET is_favorite = :isFavorite WHERE name = :name")
    suspend fun setFavorite(name: String, isFavorite: Boolean)

    @Query("SELECT COUNT(id) FROM planets")
    suspend fun countPlanets(): Int

    @Query("SELECT COUNT(name) FROM planets_user_info WHERE is_favorite = 1")
    suspend fun countFavoritePlanets(): Int

    @RawQuery
    suspend fun countPlanetsWithFilter(query: SupportSQLiteQuery): Int

    @Transaction
    @Query("SELECT * FROM PlanetSyncView ORDER BY id LIMIT :count OFFSET :start")
    suspend fun getPlanetsChunked(start: Int, count: Int): List<PlanetSyncView>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun syncInfo(updates: List<PlanetUserInfoDTO>)
}