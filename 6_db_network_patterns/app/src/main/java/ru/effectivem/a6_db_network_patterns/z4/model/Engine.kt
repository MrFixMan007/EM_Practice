package ru.effectivem.a6_db_network_patterns.z4.model

data class Engine(
    val volume: Int,
    private var _mileage: Int = 0,
    var isStarted: Boolean = false,
) {
    val mileage: Int
        get() = _mileage

    init {
        require(volume > 0) { "Volume must be positive" }
        require(_mileage >= 0) { "Mileage must be positive" }
    }

    fun addMileage(amount: Int) {
        _mileage += amount
    }
}