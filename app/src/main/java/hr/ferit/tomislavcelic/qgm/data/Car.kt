package hr.ferit.tomislavcelic.qgm.data

data class Car(
    var id: String = "",
    val make: String = "make",
    val model: String = "model",
    val vin: String = "ABCDEFGH",
    val license: String = "OS 123 AB",
    val color: String = "Red",
    val fuelType: Fuel = Fuel.petrol,
    val serviceDay: Int = 1,
    val serviceMonth: Int = 12,
    val imageUrl: String = ""
)

enum class Fuel(val value: Int) {
    petrol(0), diesel(1), hybrid(2), EV(3);

    companion object {
        fun getDefaultFuel() = petrol

        fun getByNumber(num: Int): Fuel {
            return when(num) {
                0 -> petrol
                1 -> diesel
                2 -> hybrid
                3 -> EV
                else -> petrol
            }
        }
    }
}
