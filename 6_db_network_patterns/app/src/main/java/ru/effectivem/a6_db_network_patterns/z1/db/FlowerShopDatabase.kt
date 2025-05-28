package ru.effectivem.a6_db_network_patterns.z1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.effectivem.a6_db_network_patterns.z1.db.dao.BouquetDao
import ru.effectivem.a6_db_network_patterns.z1.db.dao.BouquetFlowerDao
import ru.effectivem.a6_db_network_patterns.z1.db.dao.FlowerDao
import ru.effectivem.a6_db_network_patterns.z1.db.dao.FlowerShopDao
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Bouquet
import ru.effectivem.a6_db_network_patterns.z1.db.entity.BouquetFlower
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Flower

@Database(
    entities = [Flower::class, Bouquet::class, BouquetFlower::class],
    version = 1
)
abstract class FlowerShopDatabase : RoomDatabase() {
    abstract fun flowerDao(): FlowerDao
    abstract fun bouquetDao(): BouquetDao
    abstract fun bouquetFlowerDao(): BouquetFlowerDao
    abstract fun flowerShopDao(): FlowerShopDao
}