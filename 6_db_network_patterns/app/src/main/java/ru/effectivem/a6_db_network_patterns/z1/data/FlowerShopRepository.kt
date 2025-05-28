package ru.effectivem.a6_db_network_patterns.z1.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.effectivem.a6_db_network_patterns.z1.db.FlowerShopDatabase
import ru.effectivem.a6_db_network_patterns.z1.db.dao.BouquetDao
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Bouquet
import ru.effectivem.a6_db_network_patterns.z1.db.entity.BouquetFlower
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Flower
import kotlin.random.Random

class FlowerShopRepository(
    private val db: FlowerShopDatabase,
) {
    private val flowerDao = db.flowerDao()
    private val bouquetDao: BouquetDao = db.bouquetDao()
    private val bouquetFlowerDao = db.bouquetFlowerDao()
    private val flowerShopDao = db.flowerShopDao()

    suspend fun init() {
        withContext(Dispatchers.IO) {
            db.clearAllTables()
            val bouquetsCount = bouquetDao.getBouquetsCount()
            val flowersCount = flowerDao.getFlowersCount()
            val isEmpty = bouquetsCount == 0 && flowersCount == 0
            if (isEmpty) {
                Log.d("db", "БД пустая")

                db.flowerDao().insertAll(
                    listOf(
                        Flower("тюльпан", 10, 100),
                        Flower("роза белая", 18, 180),
                        Flower("роза красная", 20, 220),
                        Flower("азалия", 12, 120),
                        Flower("фиалка", 12, 100),
                        Flower("орхидея", 14, 110),
                        Flower("нарцисс", 15, 130),
                        Flower("пион", 16, 180),
                        Flower("гвоздика", 17, 170),
                        Flower("фикус", 14, 150),
                    )
                )

                val bouquetIds = db.bouquetDao().insertAll(
                    listOf(
                        Bouquet(0, "Букет 1", 100),
                        Bouquet(0, "Букет 2", 120),
                        Bouquet(0, "Букет 3", 120),
                        Bouquet(0, "Букет 4", 120),
                        Bouquet(0, "Букет 5", 120),
                    )
                )

                val allFlowerNames = flowerDao.getAllFlowerNames()

                allFlowerNames.forEach { flowerName ->

                    bouquetIds.forEach { bouquetId ->
                        if (Random.nextInt(0, 3) > 0) {
                            bouquetFlowerDao.insert(
                                BouquetFlower(
                                    flowerName = flowerName,
                                    bouquetId = bouquetId,
                                    countInBouquet = Random.nextInt(1, 11)
                                )
                            )
                        }
                    }
                }

                Log.d("db", "БД пополнилась")
                Log.d("db", "кол-во цветов ${flowerDao.getFlowersCount()}")
                Log.d("db", "кол-во букетов ${bouquetDao.getBouquetsCount()}")
                Log.d("db", "id букетов $bouquetIds")
                Log.d(
                    "db",
                    "кол-во букетов-цветов ${bouquetFlowerDao.getBouquetFlowersCount()}"
                )
            } else {
                Log.d("db", "БД не пустая")

                Log.d("db", "кол-во цветов ${flowerDao.getFlowersCount()}")
                Log.d("db", "кол-во букетов ${bouquetDao.getBouquetsCount()}")
                Log.d(
                    "db",
                    "кол-во букетов-цветов ${bouquetFlowerDao.getBouquetFlowersCount()}"
                )
            }
        }
    }

    suspend fun addFlower(flower: Flower) {
        withContext(Dispatchers.IO) { flowerDao.insert(flower) }
    }

    suspend fun getAllFlowers() = withContext(Dispatchers.IO) { flowerDao.getAllFlowers() }
    suspend fun getFlowersCount() = withContext(Dispatchers.IO) { flowerDao.getFlowersCount() }

    suspend fun addBouquet(bouquet: Bouquet) {
        withContext(Dispatchers.IO) { bouquetDao.insert(bouquet) }
    }

    suspend fun getAllBouquets() = withContext(Dispatchers.IO) { bouquetDao.getAllBouquets() }
    suspend fun getBouquetsCount() = withContext(Dispatchers.IO) { bouquetDao.getBouquetsCount() }

    suspend fun addFlowerToBouquet(bouquetFlower: BouquetFlower) {
        withContext(Dispatchers.IO) { bouquetFlowerDao.insert(bouquetFlower) }
    }

    suspend fun getBouquetsWithFlowers() =
        withContext(Dispatchers.IO) { flowerShopDao.getBouquetsWithFlowers() }

    suspend fun buyBouquet(bouquetId: Long) =
        withContext(Dispatchers.IO) { flowerShopDao.deleteBouquetAndUpdateFlowers(bouquetId) }
}