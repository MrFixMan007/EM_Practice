package ru.effectivem.a6_db_network_patterns.z1.db.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Bouquet
import ru.effectivem.a6_db_network_patterns.z1.db.entity.BouquetFlower
import ru.effectivem.a6_db_network_patterns.z1.db.model.FlowerCount

@Dao
interface FlowerShopDao {
    @Transaction
    @Query("SELECT * FROM bouquets")
    suspend fun getBouquetsWithFlowers(): List<BouquetWithFlowers>

    data class BouquetWithFlowers(
        @Embedded
        val bouquet: Bouquet,

        @Relation(
            parentColumn = "bouquetId",
            entity = BouquetFlower::class,
            entityColumn = "bouquetId"
        )
        val flowerCounts: List<BouquetFlower>,
    )

    @Transaction
    suspend fun deleteBouquetAndUpdateFlowers(bouquetId: Long) {
        val flowerCounts = getFlowerCountsForBouquet(bouquetId)

        flowerCounts.forEach { (flowerName, count) ->
            decrementFlowerCount(flowerName, count)
        }
        deleteBouquetFlowers(bouquetId)
        deleteBouquet(bouquetId)
    }

    @Query(
        """
        SELECT flowerName, SUM(countInBouquet) AS count 
        FROM bouquet_flowers 
        WHERE bouquetId = :bouquetId 
        GROUP BY flowerName
    """
    )
    suspend fun getFlowerCountsForBouquet(bouquetId: Long): List<FlowerCount>

    @Query(
        """
        UPDATE flowers 
        SET totalCount = totalCount - :decrement 
        WHERE flowerName = :flowerName
    """
    )
    suspend fun decrementFlowerCount(flowerName: String, decrement: Int)

    @Query("DELETE FROM bouquet_flowers WHERE bouquetId = :bouquetId")
    suspend fun deleteBouquetFlowers(bouquetId: Long)

    @Query("DELETE FROM bouquets WHERE bouquetId = :bouquetId")
    suspend fun deleteBouquet(bouquetId: Long)
}