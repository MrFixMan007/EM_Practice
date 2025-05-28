package ru.effectivem.a6_db_network_patterns.z1.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "bouquet_flowers",
    foreignKeys = [
        ForeignKey(
            entity = Flower::class,
            parentColumns = ["flowerName"],
            childColumns = ["flowerName"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Bouquet::class,
            parentColumns = ["bouquetId"],
            childColumns = ["bouquetId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["flowerName", "bouquetId"]
)
data class BouquetFlower(
    @ColumnInfo(name = "bouquetId")
    val bouquetId: Long,
    @ColumnInfo(name = "flowerName")
    val flowerName: String,
    val countInBouquet: Int
)