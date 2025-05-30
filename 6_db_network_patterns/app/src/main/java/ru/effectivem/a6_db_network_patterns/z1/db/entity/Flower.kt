package ru.effectivem.a6_db_network_patterns.z1.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "flowers", indices = [Index(value = ["flowerName"], unique = true)])
data class Flower(
    @PrimaryKey @ColumnInfo(
        name = "flowerName",
        collate = ColumnInfo.NOCASE
    ) val flowerName: String,
    val price: Int,
    @ColumnInfo(name = "totalCount") val totalCount: Int,
    @ColumnInfo(name = "countryOfOrigin") val countryOfOrigin: String,
)