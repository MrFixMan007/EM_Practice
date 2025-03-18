package ru.effectivem.kotlinpractice.extensions

fun cocktailSort(list: List<Int?>?): List<Int?> {

    if (list == null) return emptyList()
    val intList = list.filterNotNull().toMutableList()

    fun swap(i: Int, j: Int) {
        val temp = intList[i]
        intList[i] = intList[j]
        intList[j] = temp
    }

    do {
        var swapped = false
        for (i in 0 until intList.size - 1)
            if (intList[i] > intList[i + 1]) {
                swap(i, i + 1)
                swapped = true
            }
        if (!swapped) break
        swapped = false
        for (i in intList.size - 2 downTo 0)
            if (intList[i] > intList[i + 1]) {
                swap(i, i + 1)
                swapped = true
            }
    } while (swapped)

    val nullList = list.filter { it == null }

    return intList + nullList
}

fun main() {

    val list = listOf(1, null, 5, 1, null)
    val list1 = listOf(2, 5, 1)
    val list2 = null
    val list3 = listOf(null)
    val list4 = listOf(1)
    val list5 = listOf(null, null, 1, 6)
    val list6 = listOf(1, null, null, 5)
    val list7 = listOf(1, null, null, 5, null, 6, 1, 8, null, 10)

    println(cocktailSort(list))
    println(cocktailSort(list1))
    println(cocktailSort(list2))
    println(cocktailSort(list3))
    println(cocktailSort(list4))
    println(cocktailSort(list5))
    println(cocktailSort(list6))
    println(cocktailSort(list7))
}