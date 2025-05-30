package ru.effectivem.a6_db_network_patterns.z4.model

import android.util.Log

data class Car(
    val carType: CarType,
    val seats: List<Seat>,
    val engine: Engine,
    val gasTank: GasTank,
    val transmission: Transmission,
    var tripComputer: TripComputer?,
) {
    inner class TripComputer(
        val charge: Int
    ) {
        fun showFuelVolume() {
            Log.d("${TripComputer::class.simpleName}", "${gasTank.currentVolume}")
        }

        fun showStatus() {
            Log.d(
                "${TripComputer::class.simpleName}",
                if (engine.isStarted) "Car is started" else "Car isn't started"
            )
        }
    }
}