package hr.ferit.tomislavcelic.qgm

import android.util.Log
import androidx.compose.runtime.Composable
import hr.ferit.tomislavcelic.qgm.data.QGMViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import hr.ferit.tomislavcelic.qgm.ui.CarChangeGarageScreen
import hr.ferit.tomislavcelic.qgm.ui.CarDetailsScreen
import hr.ferit.tomislavcelic.qgm.ui.CarListScreen
import hr.ferit.tomislavcelic.qgm.ui.GarageDetailsScreen
import hr.ferit.tomislavcelic.qgm.ui.GarageListScreen
import hr.ferit.tomislavcelic.qgm.ui.MainScreen
import hr.ferit.tomislavcelic.qgm.ui.NewCarScreen
import hr.ferit.tomislavcelic.qgm.ui.NewGarageScreen

object Routes {
    const val SCREEN_MAIN = "mainScreen"

    const val SCREEN_GARAGE_LIST = "garageList"
    const val SCREEN_GARAGE_DETAILS = "garageDetails/{garageID}"
    const val SCREEN_GARAGE_ADD = "addGarage"

    const val SCREEN_CAR_LIST = "carList"
    const val SCREEN_CAR_DETAILS = "carDetails/{carID}"
    const val SCREEN_CAR_CHANGE_GARAGE = "carChangeGarage/{carID}"
    const val SCREEN_CAR_ADD = "addCar"

    fun getGarageDetailsPath(garageId: String?): String {
        if (garageId != null) {
            return "garageDetails/$garageId"
        }
        return "garageDetails/0"
    }

    fun getCarDetailsPath(carId: String?): String {
        if (carId != null) {
            return "carDetails/$carId"
        }
        return "carDetails/0"
    }

    fun getCarChangeGaragePath(carId: String?): String {
        if (carId != null) {
            return "carChangeGarage/$carId"
        }
        return "carChangeGarage/0"
    }
}


@Composable
fun NavigationController(
    viewModel: QGMViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_MAIN
    ) {
        composable(Routes.SCREEN_MAIN) {
            MainScreen(viewModel = viewModel, navigation = navController)
        }
        composable(Routes.SCREEN_GARAGE_LIST) {
            GarageListScreen(viewModel = viewModel, navigation = navController)
        }
        composable(
            Routes.SCREEN_GARAGE_DETAILS,
            arguments = listOf(
                navArgument("garageID") {
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("garageID")?.let {garageID ->
                GarageDetailsScreen(
                    viewModel = viewModel,
                    navigation = navController,
                    garage = viewModel.garagesData.first{ it.id == garageID }
                )
            }
        }
        composable(Routes.SCREEN_CAR_LIST) {
            CarListScreen(viewModel = viewModel, navigation = navController)
        }
        composable(
            Routes.SCREEN_CAR_DETAILS,
            arguments = listOf(
                navArgument("carID") {
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("carID")?.let {carID ->
                CarDetailsScreen(
                    viewModel = viewModel,
                    navigation = navController,
                    car = viewModel.carsData.first{ it.id == carID }
                )
            }
        }
        composable(
            Routes.SCREEN_CAR_CHANGE_GARAGE,
            arguments = listOf(
                navArgument("carID") {
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("carID")?.let {carID ->
                CarChangeGarageScreen(
                    viewModel = viewModel,
                    navigation = navController,
                    car = viewModel.carsData.first{ it.id == carID }
                )
            }
        }
        composable(Routes.SCREEN_GARAGE_ADD) {
            NewGarageScreen(viewModel = viewModel, navigation = navController)
        }
        composable(Routes.SCREEN_CAR_ADD) {
            NewCarScreen(viewModel = viewModel, navigation = navController)
        }
    }
}