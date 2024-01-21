package hr.ferit.tomislavcelic.qgm.data

import kotlin.random.Random

data class Garage(
    var id: String = "",
    val location: String = "123 sample street",
    val nickname: String = "villa",
    val maxCapacity: Int = 2,
    var cars: MutableList<String> = mutableListOf()
)


