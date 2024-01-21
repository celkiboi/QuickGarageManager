package hr.ferit.tomislavcelic.qgm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.ferit.tomislavcelic.qgm.data.Car
import hr.ferit.tomislavcelic.qgm.data.QGMViewModel
import android.util.Log
import hr.ferit.tomislavcelic.qgm.ui.theme.QGMTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarChangeGarageScreen(
    viewModel: QGMViewModel,
    navigation: NavController,
    car: Car
) {
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
                var currentGarage = viewModel.garagesData.find { it.cars.contains(car.vin) }
                val filteredGarages = viewModel.garagesData.filter {
                    (it.cars.size < it.maxCapacity) && (it != currentGarage)
                }.toList()
                Log.d("Celki", "$filteredGarages")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(filteredGarages) { garage ->
                        GarageListItem(garage = garage) {
                            garage.cars.add(car.vin)
                            currentGarage?.cars?.remove(car.vin)
                            viewModel.syncDatabase()
                            navigation.popBackStack()
                        }
                    }
                }
            }
        }
    }
}









