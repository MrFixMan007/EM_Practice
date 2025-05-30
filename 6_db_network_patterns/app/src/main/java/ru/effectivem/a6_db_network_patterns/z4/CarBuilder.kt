package ru.effectivem.a6_db_network_patterns.z4

import ru.effectivem.a6_db_network_patterns.z4.model.Car
import ru.effectivem.a6_db_network_patterns.z4.model.Car.TripComputer
import ru.effectivem.a6_db_network_patterns.z4.model.CarType
import ru.effectivem.a6_db_network_patterns.z4.model.Engine
import ru.effectivem.a6_db_network_patterns.z4.model.GasTank
import ru.effectivem.a6_db_network_patterns.z4.model.Seat
import ru.effectivem.a6_db_network_patterns.z4.model.Transmission

class CarBuilder : Builder<Car> {
    private var _car = initCar()

    override fun setCarType(type: CarType): Builder<Car> {
        _car = _car.copy(carType = type)
        return this
    }

    override fun setSeats(seats: List<Seat>): Builder<Car> {
        _car = _car.copy(seats = seats)
        return this
    }

    override fun setEngine(engine: Engine): Builder<Car> {
        _car = _car.copy(engine = engine)
        return this
    }

    override fun setGasTank(gasTank: GasTank): Builder<Car> {
        _car = _car.copy(gasTank = gasTank)
        return this
    }

    override fun setTransmission(transmission: Transmission): Builder<Car> {
        _car = _car.copy(transmission = transmission)
        return this
    }

    override fun setTripComputer(tripComputer: TripComputer): Builder<Car> {
        _car = _car.copy(tripComputer = tripComputer)
        return this
    }

    override fun build(): Car {
        val car = _car.copy()
        reset()
        return car
    }

    private fun initCar(): Car {
        return Car(
            carType = CarType.CITY_CAR,
            seats = listOf(),
            engine = Engine(volume = 100),
            gasTank = GasTank(maxVolume = 100),
            transmission = Transmission.MANUAL,
            tripComputer = null,
        )
    }

    private fun reset() {
        _car = initCar()
    }

}