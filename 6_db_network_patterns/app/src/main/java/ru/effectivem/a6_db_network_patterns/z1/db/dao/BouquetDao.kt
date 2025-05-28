package ru.effectivem.a6_db_network_patterns.z1.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Bouquet

@Dao
interface BouquetDao {
    @Insert
    suspend fun insert(bouquet: Bouquet): Long

    @Insert
    suspend fun insertAll(bouquet: List<Bouquet>): List<Long>

    @Query("SELECT * FROM bouquets")
    suspend fun getAllBouquets(): List<Bouquet>

    @Query("SELECT COUNT(*) FROM bouquets")
    suspend fun getBouquetsCount(): Int

    @Query("DELETE FROM bouquets WHERE bouquetId = :id")
    suspend fun deleteBouquet(id: Int)
}