package hr.ferit.tomislavcelic.qgm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import hr.ferit.tomislavcelic.qgm.data.QGMViewModel
import hr.ferit.tomislavcelic.qgm.ui.theme.QGMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(
    viewModel: QGMViewModel,
    navigation: NavController,
) {
    var searchText by remember { mutableStateOf("") }

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
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(225.dp)
                                .padding(8.dp)
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            navigation.navigate(Routes.SCREEN_CAR_ADD)
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Garage")
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
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Search by make, model, or VIN") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                        }
                    )
                )

                val filteredCars = viewModel.carsData.filter {
                    it.make.contains(searchText, ignoreCase = true) ||
                            it.model.contains(searchText, ignoreCase = true) ||
                            it.vin.contains(searchText, ignoreCase = true)
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(filteredCars) { car ->
                        CarListItem(car = car) {
                            navigation.navigate(Routes.getCarDetailsPath(it.id))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarListItem(car: Car, onItemClick: (Car) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onItemClick(car) }
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "${car.make} ${car.model}", fontWeight = FontWeight.Bold)
            Text(text = "VIN: ${car.vin}")
            Text(text = "License Plate: ${car.license}")
            Text(text = "Color: ${car.color}")
            Text(text = "Fuel Type: ${car.fuelType}")
            Text(text = "Service Date: ${car.serviceDay}/${car.serviceMonth}")
        }
    }
}
