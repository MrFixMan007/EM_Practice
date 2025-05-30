package ru.effectivem.a6_db_network_patterns.z1.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.effectivem.a6_db_network_patterns.z1.db.entity.BouquetFlower

@Dao
interface BouquetFlowerDao {
    @Insert
    suspend fun insert(bouquetFlower: BouquetFlower)

    @Insert
    suspend fun insertAll(bouquetFlowers: List<BouquetFlower>)

    @Query("SELECT * FROM bouquet_flowers WHERE bouquetId = :bouquetId")
    suspend fun getBouquetFlowers(bouquetId: Int): List<BouquetFlower>

    @Query("DELETE FROM bouquet_flowers WHERE bouquetId = :bouquetId")
    suspend fun deleteBouquetFlower(bouquetId: Int)

    @Query("SELECT COUNT(*) FROM bouquet_flowers")
    suspend fun getBouquetFlowersCount(): Int
}