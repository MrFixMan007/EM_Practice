package ru.effectivem.a6_db_network_patterns.z1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.effectivem.a6_db_network_patterns.z1.db.dao.BouquetDao
import ru.effectivem.a6_db_network_patterns.z1.db.dao.BouquetFlowerDao
import ru.effectivem.a6_db_network_patterns.z1.db.dao.FlowerDao
import ru.effectivem.a6_db_network_patterns.z1.db.dao.FlowerShopDao
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Bouquet
import ru.effectivem.a6_db_network_patterns.z1.db.entity.BouquetFlower
import ru.effectivem.a6_db_network_patterns.z1.db.entity.Flower

const val FlowerShopDatabaseName = "flower-shop-db"

@Database(
    entities = [Flower::class, Bouquet::class, BouquetFlower::class],
    version = 2
)
abstract class FlowerShopDatabase : RoomDatabase() {
    abstract fun flowerDao(): FlowerDao
    abstract fun bouquetDao(): BouquetDao
    abstract fun bouquetFlowerDao(): BouquetFlowerDao
    abstract fun flowerShopDao(): FlowerShopDao

    companion object {
        @Volatile
        private var INSTANCE: FlowerShopDatabase? = null

        fun getDatabase(
            context: Context,
            dbName: String = FlowerShopDatabaseName
        ): FlowerShopDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlowerShopDatabase::class.java,
                    dbName
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // задание 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE bouquets ADD COLUMN decoration TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE flowers ADD COLUMN countryOfOrigin TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}