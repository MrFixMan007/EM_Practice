package ru.effectivem.kotlinpractice.z1

data class Key(
    val field1: Int,
    var field2: String
) {
    var field3: String? = null

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is Key) return false
//        return field1 == other.field1 &&
//                field2 == other.field2 &&
//                field3 == other.field3
//    }
//
//    override fun hashCode(): Int {
//        return 31 * (31 * field1 + field2.hashCode()) + (field3?.hashCode() ?: 0)
//    }

    // Смотреть в KeyTest (JUnit тест)
}
