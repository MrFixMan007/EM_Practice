package ru.effectivem.a6_db_network_patterns.z4.factory

import ru.effectivem.a6_db_network_patterns.z4.CarBuilder
import ru.effectivem.a6_db_network_patterns.z4.model.Car
import ru.effectivem.a6_db_network_patterns.z4.model.CarType
import ru.effectivem.a6_db_network_patterns.z4.model.GasTank
import ru.effectivem.a6_db_network_patterns.z4.model.Seat
import ru.effectivem.a6_db_network_patterns.z4.model.SeatType
import ru.effectivem.a6_db_network_patterns.z4.model.Transmission

class ToyotaFactory : CarFactory {
    override fun createCar(): Car {
        return CarBuilder()
            .setCarType(type = CarType.SPORTS_CAR)
            .setGasTank(GasTank(100))
            .setTransmission(Transmission.MANUAL)
            .setSeats(
                listOf(
                    Seat(SeatType.GENERAL),
                    Seat(SeatType.GENERAL),
                )
            )
            .build()
    }

    override fun createSportCar(): Car {
        return CarBuilder()
            .setCarType(type = CarType.SPORTS_CAR)
            .setGasTank(GasTank(300))
            .setTransmission(Transmission.SEMI_AUTOMATIC)
            .setSeats(listOf(Seat(SeatType.SPORT)))
            .build()
    }

    override fun createFamilyCar(): Car {
        return CarBuilder()
            .setCarType(type = CarType.CITY_CAR)
            .setGasTank(GasTank(200))
            .setTransmission(Transmission.MANUAL)
            .setSeats(
                listOf(
                    Seat(SeatType.GENERAL),
                    Seat(SeatType.GENERAL),
                    Seat(SeatType.GENERAL),
                    Seat(SeatType.GENERAL),
                )
            )
            .build()
    }
}