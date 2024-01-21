package hr.ferit.tomislavcelic.qgm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.ferit.tomislavcelic.qgm.R
import hr.ferit.tomislavcelic.qgm.Routes
import hr.ferit.tomislavcelic.qgm.data.Car
import hr.ferit.tomislavcelic.qgm.data.Garage
import hr.ferit.tomislavcelic.qgm.data.QGMViewModel
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.remember
import androidx.compose.ui.modifier.modifierLocalConsumer
import coil.compose.rememberAsyncImagePainter
import hr.ferit.tomislavcelic.qgm.ui.theme.QGMTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailsScreen(
    viewModel: QGMViewModel,
    navigation: NavController,
    car: Car
) {
    fun findGarageByVin(vin: String): Garage? {
        return viewModel.garagesData.find { it.cars.contains(vin) }
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
                        Text(text = "${car.make} ${car.model}", fontWeight = FontWeight.Bold)
                    },
                    actions = {
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
                    .padding(16.dp)
            ) {
                if (car.imageUrl.isNotEmpty()) {
                    Log.d("Celki", "Prije slike")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = car.imageUrl),
                            contentDescription = "car image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }

                Log.d("Celki", "${car.imageUrl}")
                Log.d("Celki", "Prošlo sliku")
                Text(
                    text = "VIN: ${car.vin}",
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                Text(text = "License Plate: ${car.license}")
                Text(text = "Color: ${car.color}")
                Text(text = "Fuel Type: ${car.fuelType.name}")
                Text(text = "Service Date: ${car.serviceDay}/${car.serviceMonth}")
                var storedGarage = findGarageByVin(car.vin)
                var garageText = ""
                if (storedGarage != null) {
                    Text(text = "Car location: ${(findGarageByVin(car.vin))?.location}",
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                    garageText = "Change garage"
                }
                else {
                    Text(text="Car not yet stored.", modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                    garageText = "Add to garage"
                }
                Button(
                    onClick = {
                        navigation.navigate(Routes.getCarChangeGaragePath(car.id))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = garageText,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }

}








