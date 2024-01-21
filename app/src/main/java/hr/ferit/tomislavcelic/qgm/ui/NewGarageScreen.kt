package hr.ferit.tomislavcelic.qgm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.ferit.tomislavcelic.qgm.data.Garage
import hr.ferit.tomislavcelic.qgm.data.QGMViewModel
import hr.ferit.tomislavcelic.qgm.ui.theme.QGMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGarageScreen(
    viewModel: QGMViewModel,
    navigation: NavController
) {
    var location by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var maxCapacity by remember { mutableIntStateOf(2) }

    val isInputValid = location.isNotEmpty() && nickname.isNotEmpty()

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
                        Text(text = "New Garage", fontWeight = FontWeight.Bold)
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (isInputValid) {
                                    var newGarage = mapOf(
                                        "location" to location,
                                        "nickname" to nickname,
                                        "maxCapacity" to maxCapacity,
                                        "cars" to mutableListOf<String>()
                                    )

                                    viewModel.db.collection("garages").add(newGarage)
                                    viewModel.syncDatabase()
                                    navigation.popBackStack()
                                } else {
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
                    value = location,
                    onValueChange = { location = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Location") }
                )
                TextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Nickname") }
                )
                TextField(
                    value = maxCapacity.toString(),
                    onValueChange = {
                        var number: Int? = it.toIntOrNull()
                        if (number == null || number <= -1) {
                            number = 1
                        }
                        maxCapacity = number
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text("Max Capacity") }
                )

            }
        }
    }
}
