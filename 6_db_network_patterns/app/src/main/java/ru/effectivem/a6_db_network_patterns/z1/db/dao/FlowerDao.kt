package ru.effectivem.a6_db_network_patterns.z1.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Flower

@Dao
interface FlowerDao {
    @Insert
    suspend fun insert(flower: Flower)

    @Insert
    suspend fun insertAll(flower: List<Flower>)

    @Update
    suspend fun update(flower: Flower)

    @Query("SELECT * FROM flowers")
    suspend fun getAllFlowers(): List<Flower>

    @Query("SELECT flowerName FROM flowers")
    suspend fun getAllFlowerNames(): List<String>

    @Query("SELECT COUNT(*) FROM flowers")
    suspend fun getFlowersCount(): Int

    @Query("DELETE FROM flowers WHERE flowerName = :name")
    suspend fun deleteFlower(name: String)
}