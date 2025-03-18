package ru.effectivem.kotlinpractice

import org.junit.Test

import org.junit.Assert.*
import ru.effectivem.kotlinpractice.z1.Key

class KeyTest {
    @Test
    fun incorrectAddingKeysInMap() { // fail

        val key1 = Key(1, "")
        key1.field3 = "1"

        val map = hashMapOf(
            key1 to "value 1",
            Key(1, "") to "key 2",
        )

        println(map[key1])
        println("Размер map = ${map.size}") // Размер map = 1
        assertEquals("value 1", map[key1])
        // Хешкод одинаков, потому по ключу значение перезаписывается.
        // Если Перезаписать правила хешкода, то проблему можно избежать, но лучше сразу все переменные
        // в дата классе хранить в главном конструкторе
    }

    @Test
    fun changedField2() { // fail
        val map = HashMap<Key, String>()

        val key = Key(1, "initial")
        map[key] = "value 1"

        assertEquals("value 1", map[key])

        key.field2 = "changed" // hashCode изменится
        println(map)
        println(map.containsKey(Key(1, "changed"))) // false

        assertEquals("value 1", map[key]) // не может найти

    }
}