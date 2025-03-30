import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmitTime: Long = 0

    collect {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmitTime > windowDuration) {
            lastEmitTime = currentTime
            emit(it)
        }
    }
}

private fun main(): Unit = runBlocking {
    //  должен вернуть 1, 4, 5, 6, 8
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
        .throttleFirst(1000)
        .collect { println(it) }
}
