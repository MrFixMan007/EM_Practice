import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun <T> Flow<T>.throttleLast(windowDuration: Long): Flow<T> = flow {
    var lastValue: T? = null
    var lastEmitTime = System.currentTimeMillis()

    collect { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmitTime >= windowDuration) {
            lastValue?.let {
                emit(it)
                lastEmitTime = currentTime
                lastValue = null
            }
        }
        lastValue = value
    }
    lastValue = null
}

private fun main(): Unit = runBlocking {
    //  должен вернуть 3, 4, 5, 7
    flow {
        emit(1)
        delay(90)
        emit(2)
        delay(90)
        emit(3)
        delay(1010)
        emit(4)
        delay(1010)
        emit(5)
        delay(2000)
        emit(6)
        delay(90)
        emit(7)
        delay(1010)
        emit(8)
        delay(80)
        emit(9)
    }
        .throttleLast(1000)
        .collect { println(it) }
}

fun <T> Flow<T>.throttleLastWithPrints(windowDuration: Long): Flow<T> = flow { // оставил для проверок
    var lastValue: T? = null
    var lastEmitTime = System.currentTimeMillis()

    collect { value ->
        val currentTime = System.currentTimeMillis()
        val res = currentTime - lastEmitTime //
        println("$currentTime - $lastEmitTime = $res") //
        if (res >= windowDuration) {
            lastValue?.let {
                emit(it)
                lastEmitTime = currentTime
                lastValue = null
            }
        }
        lastValue = value
    }
    lastValue = null
    println("end")
}
