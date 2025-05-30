package ru.effectivem.a6_db_network_patterns.z4.factory

import ru.effectivem.a6_db_network_patterns.z4.model.Car

interface CarFactory {
    fun createCar(): Car
    fun createSportCar(): Car
    fun createFamilyCar(): Car
}