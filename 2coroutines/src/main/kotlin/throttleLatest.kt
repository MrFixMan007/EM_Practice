import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun <T> Flow<T>.throttleLatest(windowDuration: Long): Flow<T> = flow {
    var lastValue: T? = null
    var firstValue: T? = null
    var lastEmitTime = System.currentTimeMillis()

    collect { value ->
        if (firstValue == null) {
            firstValue = value
        }
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmitTime >= windowDuration) {
            firstValue?.let {
                firstValue = if (lastValue != value) value else null
                emit(it)
            }
            lastValue?.let {
                lastValue = null
                emit(it)
            }
            lastEmitTime = currentTime
        }
        if (firstValue != null && firstValue != value) {
            lastValue = value
        }
    }
    lastValue = null
    firstValue = null
}

private fun main(): Unit = runBlocking {
    //  должен вернуть 1, 3, 4, 5, 6, 7
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
        .throttleLatest(1000)
        .collect { println(it) }
}

fun <T> Flow<T>.throttleLatestWithPrints(windowDuration: Long): Flow<T> = flow { // оставил для проверок
    var lastValue: T? = null
    var firstValue: T? = null
    var lastEmitTime = System.currentTimeMillis()

    collect { value ->
        println("on value = $value:")
        if (firstValue == null) {
            println("firstVal = $value")
            firstValue = value
        }
        val currentTime = System.currentTimeMillis()
        val res = currentTime - lastEmitTime //
        println("$currentTime - $lastEmitTime = $res") //
        if (res >= windowDuration) {
            firstValue?.let {
                println("emit firstVal $it")
                firstValue = if (lastValue != value) value else null
                if (firstValue != null) println("now firstValue is $firstValue")
                emit(it)
            }
            lastValue?.let {
                println("emit lastVal $it")
                lastValue = null
                emit(it)
            }
            lastEmitTime = currentTime
        }
        if (firstValue != null && firstValue != value) {
            println("lastVal = $value")
            lastValue = value
        }
    }
    lastValue = null
    firstValue = null
    println("end")
}