package ch.omerixe.androidarchitecture

import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.omerixe.androidarchitecture.ui.DetailScreen
import ch.omerixe.androidarchitecture.ui.HomeScreen
import ch.omerixe.androidarchitecture.ui.LoginScreen
import ch.omerixe.androidarchitecture.ui.OverviewScreen

internal sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Overview : Screen("overview")
    object Login : Screen("login")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Home : LeafScreen("home")
    object Overview : LeafScreen("overview")
    object Login : LeafScreen("login")

    object Detail : LeafScreen("detail/{detailId}") {
        fun createRoute(root: Screen, detailId: String): String {
            return "${root.route}/detail/$detailId"
        }
    }
}

@Composable
internal fun AppNavigation(
    navController: NavHostController,
) {
    // Todo: This would be somewhere else in a ViewModel
    // Todo: LoggedIn should be checked in every screen that needs a log-in
    var loggedIn by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        addHomeGraph(navController = navController, isLoggedIn = { loggedIn }) { loggedIn = false }
        addLoginGraph(navController = navController) {
            loggedIn = true
        }
        addOverviewGraph(navController = navController)
    }
}

private fun NavGraphBuilder.addHomeGraph(
    navController: NavHostController,
    isLoggedIn: () -> Boolean, //This should be a state to get rid of the function
    logOut: () -> Unit
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home)
    ) {
        composable(route = LeafScreen.Home.createRoute(Screen.Home)) {
            val loggedIn = isLoggedIn()
            LaunchedEffect(loggedIn) {
                if (!loggedIn) {
                    navController.navigate(Screen.Login.route)
                }
            }
            HomeScreen(
                onOverViewClick = {
                    navController.navigate(Screen.Overview.route)
                },
                onDetailClick = {
                    //Todo: Is there a better way to do this?
                    navController.navigate(Screen.Overview.route)
                    navController.navigate(LeafScreen.Detail.createRoute(Screen.Overview, "abc"))
                },
                onLogoutClick = {
                    logOut()
                }
            )
        }
    }
}

private fun NavGraphBuilder.addLoginGraph(
    navController: NavHostController,
    logIn: () -> Unit
) {
    navigation(
        route = Screen.Login.route,
        startDestination = LeafScreen.Login.createRoute(Screen.Login)
    ) {
        composable(route = LeafScreen.Login.createRoute(Screen.Login)) {
            LoginScreen(
                onButtonClick = {
                    navController.popBackStack()
                    logIn()
                },
            )
        }
    }
}

private fun NavGraphBuilder.addOverviewGraph(
    navController: NavController
) {
    navigation(
        route = Screen.Overview.route,
        startDestination = LeafScreen.Overview.createRoute(Screen.Overview),
    ) {
        composable(route = LeafScreen.Overview.createRoute(Screen.Overview)) {
            OverviewScreen {
                navController.navigate(LeafScreen.Detail.createRoute(Screen.Overview, "abc"))
            }
        }
        composable(route = LeafScreen.Detail.createRoute(Screen.Overview),
            arguments = listOf(
                navArgument("detailId") { type = NavType.StringType }
            )) {
            DetailScreen()
        }
    }
}

object LoginStatus {
    var loggedIn = false
}