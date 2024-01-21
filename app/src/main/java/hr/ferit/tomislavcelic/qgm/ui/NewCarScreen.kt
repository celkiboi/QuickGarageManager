package hr.ferit.tomislavcelic.qgm.ui

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import hr.ferit.tomislavcelic.qgm.data.Car
import hr.ferit.tomislavcelic.qgm.data.Fuel
import hr.ferit.tomislavcelic.qgm.data.QGMViewModel
import java.util.UUID
import kotlin.reflect.KProperty
import androidx.activity.compose.rememberLauncherForActivityResult
import hr.ferit.tomislavcelic.qgm.ui.theme.QGMTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCarScreen(
    viewModel: QGMViewModel,
    navigation: NavController
) {
    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var vin by remember { mutableStateOf("") }
    var license by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var serviceDay by remember { mutableStateOf(1) }
    var serviceMonth by remember { mutableStateOf(12) }
    var imageUrl by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(0) }

    val isInputValid = make.isNotEmpty() && model.isNotEmpty() && vin.isNotEmpty() && license.isNotEmpty() && color.isNotEmpty()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    fun uploadImageToStorage(onSuccess: () -> Unit) {
        imageUri?.let { uri ->
            val fileName = UUID.randomUUID().toString()
            val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("car_images/$fileName")

            storageRef.putFile(uri)
                .addOnSuccessListener { taskSnapshot ->
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        imageUrl = downloadUri.toString()
                        Log.d("NewCarScreen", "Image URL: $imageUrl")

                        onSuccess()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("NewCarScreen", "Image upload failed: $exception")
                }
        }
    }

    QGMTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navigation.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    title = {
                        Text(text = "New Car", fontWeight = FontWeight.Bold)
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                uploadImageToStorage {
                                    if (isInputValid) {
                                        val newCar = mapOf(
                                            "make" to make,
                                            "model" to model,
                                            "vin" to vin,
                                            "license" to license,
                                            "color" to color,
                                            "fuelType" to Fuel.getByNumber(selectedItem).name,
                                            "serviceDay" to serviceDay,
                                            "serviceMonth" to serviceMonth,
                                            "imageUrl" to imageUrl
                                        )
                                        viewModel.db.collection("cars").add(newCar)
                                        viewModel.syncDatabase()

                                        navigation.popBackStack()
                                    }

                                }
                            },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(8.dp),
                            enabled = isInputValid
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                TextField(
                    value = make,
                    onValueChange = { make = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Make") }
                )

                TextField(
                    value = model,
                    onValueChange = { model = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Model") }
                )

                TextField(
                    value = vin,
                    onValueChange = { vin = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("VIN") }
                )

                TextField(
                    value = license,
                    onValueChange = { license = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("License Plate") }
                )

                TextField(
                    value = color,
                    onValueChange = { color = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Color") }
                )
                TextField(
                    value = serviceDay.toString(),
                    onValueChange = {
                        var number: Int? = it.toIntOrNull()
                        if (number == null || number <= -1) {
                            number = 1
                        }
                        serviceDay = number
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Service day") }
                )
                TextField(
                    value = serviceMonth.toString(),
                    onValueChange = {
                        var num: Int? = it.toIntOrNull()
                        if (num == null || num <= -1) {
                            num = 1
                        }
                        serviceMonth = num
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Service month") }
                )
                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text("Selected fuel: ${Fuel.getByNumber(selectedItem)}")
                    }
                    var selected: Int = 0
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = 0
                                expanded = false
                            },
                            text = {
                                Text("Petrol")
                            }
                        )
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = 1
                                expanded = false
                            },
                            text = {
                                Text("Diesel")
                            }
                        )
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = 2
                                expanded = false
                            },
                            text = {
                                Text("Hybrid")
                            }
                        )
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = 3
                                expanded = false
                            },
                            text = {
                                Text("EV")
                            }
                        )

                    }

                }
                TextButton(onClick = { getContent.launch("image/*") }) {
                    Text("Upload Image")
                }

                imageUri?.let { uri ->
                    Text("Selected Image: $uri")
                }
            }
        }
    }

}
