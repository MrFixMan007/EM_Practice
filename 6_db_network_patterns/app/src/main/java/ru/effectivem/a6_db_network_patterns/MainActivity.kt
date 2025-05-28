package ru.effectivem.a6_db_network_patterns

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.effectivem.a6_db_network_patterns.z1.data.FlowerShopRepository
import ru.effectivem.a6_db_network_patterns.z1.db.FlowerShopDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var repository: FlowerShopRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initUI()
    }

    private fun init() {
        this.deleteDatabase("flower-shop-db")

        val db = Room.databaseBuilder(
            this.applicationContext,
            FlowerShopDatabase::class.java,
            "flower-shop-db"
        )
            .build()

        repository = FlowerShopRepository(db = db)

        lifecycleScope.launch(Dispatchers.IO) {
            repository.init()
            Log.d("db created ", "кол-во букетов ${repository.getBouquetsCount()}")
            Log.d("db created ", "кол-во цветов ${repository.getFlowersCount()}")
            val bouquetsWithFlowers = repository.getBouquetsWithFlowers()
            Log.d("db bouquets With Flowers", "$bouquetsWithFlowers")
            val bouquetIds = repository.getAllBouquets()
            bouquetIds.forEach { bouquet ->
                Log.d("db created ", "букет ${bouquet.bouquetName} был куплен за ${bouquet.price} рублей")
                repository.buyBouquet(bouquet.bouquetId)
            }
            Log.d("db created ", "кол-во букетов ${repository.getBouquetsCount()}")
            Log.d("db created ", "кол-во цветов ${repository.getFlowersCount()}")

            Log.d("db bouquets With Flowers", if (repository.getBouquetsWithFlowers().isEmpty()) "Все цветы раскуплены" else "цветы ещё остались")
            val newBouquetsWithFlowers = repository.getBouquetsWithFlowers()
            Log.d("db bouquets With Flowers", "$newBouquetsWithFlowers")
        }

    }

    private fun initUI() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}