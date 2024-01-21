package hr.ferit.tomislavcelic.qgm.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QGMViewModel : ViewModel() {
    val db = Firebase.firestore
    val garagesData = mutableListOf<Garage>()
    val carsData = mutableListOf<Car>()

    init {
        fetchDatabaseData()
    }

    fun fetchDatabaseData() {
        Log.d("Celki", "Vamo")
        db.collection("garages").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val garage = document.toObject(Garage::class.java)
                    garage?.id = document.id

                    if (garage != null)
                        garagesData.add(garage)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Celki", "Failure. Garages. Error: $exception")
            }

        db.collection("cars").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    Log.d("Celki", "foreach")
                    val car = document.toObject(Car::class.java)
                    car?.id = document.id

                    if (car != null)
                        carsData.add(car)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Celki", "Failure. Cars. Error: $exception")
            }
    }

    fun fetchGarages() {
        db.collection("garages").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val garage = document.toObject(Garage::class.java)
                    garage?.id = document.id

                    if (garage != null && !garagesData.contains(garage))
                        garagesData.add(garage)
                }
            }
    }

    fun fetchCars() {
        db.collection("cars").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val car = document.toObject(Car::class.java)
                    car?.id = document.id

                    if (car != null && !carsData.contains(car)) {
                        carsData.add(car)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Celki", "Failure. Cars. Error: $exception")
            }
    }


    fun syncDatabase() {
        pushGaragesToFirestore()
        pushCarsToFirestore()
        fetchGarages()
        fetchCars()
    }

    private fun pushGaragesToFirestore() {
        for (garage in garagesData) {
            db.collection("garages").document(garage.id)
                .set(garage)
                .addOnSuccessListener {
                    Log.d("Celki", "Garage updated or added successfully")
                }
                .addOnFailureListener { exception ->
                    Log.e("Celki", "Error updating or adding garage", exception)
                }
        }
    }

    private fun pushCarsToFirestore() {
        for (car in carsData) {
            db.collection("cars").document(car.id)
                .set(car)
                .addOnSuccessListener {
                    Log.d("Celki", "Car updated or added successfully")
                }
                .addOnFailureListener { exception ->
                    Log.e("Celki", "Error updating or adding car", exception)
                }
        }
    }

}
