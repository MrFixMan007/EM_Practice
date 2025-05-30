package ru.effectivem.a6_db_network_patterns.z4.model

data class GasTank(
    val maxVolume: Int,
    private var _currentVolume: Int = 0
) {
    var currentVolume: Int
        get() = _currentVolume
        set(value) {
            require(value in 0..maxVolume) {
                "Volume must be between 0 and $maxVolume"
            }
            _currentVolume = value
        }

    init {
        require(maxVolume > 0) { "Max volume must be positive" }
        require(_currentVolume >= 0) { "Current volume must be positive" }
        currentVolume = 0
    }

    fun fill(amount: Int) {
        currentVolume += amount
    }

    fun drain(amount: Int) {
        currentVolume -= amount
    }
}