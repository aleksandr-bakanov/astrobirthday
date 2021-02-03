package bav.astrobirthday.data.local

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.data.entities.PlanetAndInfo
import bav.astrobirthday.data.entities.PlanetSyncView
import bav.astrobirthday.data.entities.PlanetUserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {

    @Transaction
    @Query("SELECT * FROM planets WHERE pl_name = :name")
    fun getByName(name: String): Flow<PlanetAndInfo>

    @Transaction
    @Query("SELECT * FROM planets WHERE pl_name IN (:names) ORDER BY id ASC")
    fun getByNames(names: Collection<String>): Flow<List<PlanetAndInfo>>

    @Transaction
    @Query("SELECT * FROM planets WHERE pl_name LIKE :pattern ORDER BY id ASC")
    fun getByNamesLike(pattern: String): Flow<List<PlanetAndInfo>>

    @Transaction
    @RawQuery(observedEntities = [Planet::class, PlanetUserInfo::class])
    fun planetsByUidOrder(query: SupportSQLiteQuery): PagingSource<Int, PlanetAndInfo>

    @Transaction
    @RawQuery(observedEntities = [Planet::class, PlanetUserInfo::class])
    fun getFavoritePlanets(query: SupportSQLiteQuery): PagingSource<Int, PlanetAndInfo>

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
    suspend fun syncInfo(updates: List<PlanetUserInfo>)
}