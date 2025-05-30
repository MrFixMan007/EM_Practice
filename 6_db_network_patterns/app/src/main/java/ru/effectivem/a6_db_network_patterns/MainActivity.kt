package ru.effectivem.a6_db_network_patterns

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.effectivem.a6_db_network_patterns.z1.data.FlowerShopRepository
import ru.effectivem.a6_db_network_patterns.z1.db.FlowerShopDatabase
import ru.effectivem.a6_db_network_patterns.z3.data.api.ApiService
import ru.effectivem.a6_db_network_patterns.z3.data.api.ResponseLogInterceptor
import ru.effectivem.a6_db_network_patterns.z4.CarBuilder
import ru.effectivem.a6_db_network_patterns.z4.factory.ToyotaFactory
import ru.effectivem.a6_db_network_patterns.z4.model.CarType
import ru.effectivem.a6_db_network_patterns.z4.model.GasTank
import ru.effectivem.a6_db_network_patterns.z4.model.Seat
import ru.effectivem.a6_db_network_patterns.z4.model.SeatType
import ru.effectivem.a6_db_network_patterns.z4.model.Transmission

class MainActivity : AppCompatActivity() {

    private lateinit var repository: FlowerShopRepository
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initApi()
        initUI()
        initCar()
    }

    private fun init() {
        val db = FlowerShopDatabase.getDatabase(this)

        repository = FlowerShopRepository(db = db)

        lifecycleScope.launch(Dispatchers.IO) {
            repository.init()

            //покупка всех букетов
            Log.d("db created ", "кол-во букетов ${repository.getBouquetsCount()}")
            Log.d("db created ", "кол-во цветов ${repository.getFlowersCount()}")

            val bouquetsWithFlowers = repository.getBouquetsWithFlowers()
            Log.d("db bouquets With Flowers", "$bouquetsWithFlowers")

//            Закоментировал для просмотра таблиц в инспекции

//            val bouquetIds = repository.getAllBouquets()
//            bouquetIds.forEach { bouquet ->
//                Log.d(
//                    "db created ",
//                    "букет ${bouquet.bouquetName} был куплен за ${bouquet.price} рублей"
//                )
//                repository.buyBouquet(bouquet.bouquetId)
//            }
//            Log.d("db created ", "кол-во букетов ${repository.getBouquetsCount()}")
//            Log.d("db created ", "кол-во цветов ${repository.getFlowersCount()}")
//

//            Log.d(
//                "db bouquets With Flowers",
//                if (repository.getBouquetsWithFlowers()
//                        .isEmpty()
//                ) "Все цветы раскуплены" else "цветы ещё остались"
//            )
//            val newBouquetsWithFlowers = repository.getBouquetsWithFlowers()
//            Log.d("db bouquets With Flowers", "$newBouquetsWithFlowers")
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

    private fun initApi() {
        val client = OkHttpClient.Builder()
            .addInterceptor(ResponseLogInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(ApiService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            runCatching {
                Log.d(
                    "api service",
                    apiService.getPost().body()
                        .toString()
                        .replace(", ", ",\n\t")
                        .replaceFirst("(", "\n(\n\t")
                )
            }.onFailure {
                Log.e("apiService", "$it")
            }
        }
    }

    private fun initCar() {
        val car = CarBuilder()
            .setCarType(type = CarType.SPORTS_CAR)
            .setGasTank(GasTank(100))
            .setTransmission(Transmission.SEMI_AUTOMATIC)
            .setSeats(listOf(Seat(SeatType.SPORT)))
            .build()
        car.gasTank.fill(50)
        car.engine.addMileage(10)
        car.gasTank.drain(15)
        Log.d("car", "$car")

        val toyotaFactory = ToyotaFactory()
        val toyotaSportCar = toyotaFactory.createSportCar()
        Log.d("car", "$toyotaSportCar")
    }
}