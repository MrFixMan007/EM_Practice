package ru.effectivem.a6_db_network_patterns.z1.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bouquets")
data class Bouquet(
    @PrimaryKey(autoGenerate = true) val bouquetId: Long,
    @ColumnInfo(name = "bouquetName", collate = ColumnInfo.NOCASE) val bouquetName: String,
    @ColumnInfo(name = "bouquetPrice") val price: Int,
    @ColumnInfo(name = "decoration") val decoration: String,
)