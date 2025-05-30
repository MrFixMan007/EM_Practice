package ru.effectivem.a6_db_network_patterns.z4

import ru.effectivem.a6_db_network_patterns.z4.model.Car
import ru.effectivem.a6_db_network_patterns.z4.model.CarType
import ru.effectivem.a6_db_network_patterns.z4.model.Engine
import ru.effectivem.a6_db_network_patterns.z4.model.GasTank
import ru.effectivem.a6_db_network_patterns.z4.model.Seat
import ru.effectivem.a6_db_network_patterns.z4.model.Transmission

interface Builder<T> {
    fun setCarType(type: CarType): Builder<T>
    fun setSeats(seats: List<Seat>): Builder<T>
    fun setEngine(engine: Engine): Builder<T>
    fun setGasTank(gasTank: GasTank): Builder<T>
    fun setTransmission(transmission: Transmission): Builder<T>
    fun setTripComputer(tripComputer: Car.TripComputer): Builder<T>
    fun build(): T
}