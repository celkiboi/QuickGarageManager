package hr.ferit.tomislavcelic.qgm.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import hr.ferit.tomislavcelic.qgm.R
import hr.ferit.tomislavcelic.qgm.Routes
import hr.ferit.tomislavcelic.qgm.data.QGMViewModel
import hr.ferit.tomislavcelic.qgm.ui.theme.Purple40
import hr.ferit.tomislavcelic.qgm.ui.theme.QGMTheme

@Composable
fun MainScreen(
    viewModel: QGMViewModel,
    navigation: NavController
) {
    QGMTheme {
        Log.d("Color", "${MaterialTheme.colorScheme.primary}")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(290.dp)
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    navigation.navigate(Routes.SCREEN_GARAGE_LIST)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)

            ) {
                Text(
                    text = "Garages",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Button(
                onClick = {
                    navigation.navigate(Routes.SCREEN_CAR_LIST)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Cars",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}




