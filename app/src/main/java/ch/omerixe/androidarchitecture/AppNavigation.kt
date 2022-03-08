package ch.omerixe.androidarchitecture

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.omerixe.androidarchitecture.ui.*
import kotlinx.coroutines.launch

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
    mainViewModel: MainViewModel
) {
    //Todo: Is this the right place to save these values?
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerContent = {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                onClick = { scope.launch { drawerState.close() } },
                content = { Text("Close Drawer") }
            )

        },
        drawerState = drawerState
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            addHomeGraph(
                navController = navController,
                loggedIn = mainViewModel.loggedIn,
                logOut = mainViewModel::logOut,
                drawerState = drawerState,
            )
            addLoginGraph(navController = navController, logIn = mainViewModel::logIn)
            addOverviewGraph(navController = navController)
        }
    }

}

private fun NavGraphBuilder.addHomeGraph(
    navController: NavHostController,
    loggedIn: MutableState<Boolean>,
    logOut: () -> Unit,
    drawerState: DrawerState,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home)
    ) {
        composable(route = LeafScreen.Home.createRoute(Screen.Home)) {
            val scope = rememberCoroutineScope()

            LaunchedEffect(loggedIn.value) {
                if (!loggedIn.value) {
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
                },
                onMenuClicked = {
                    scope.launch { drawerState.open() }
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
            OverviewScreen(
                onButtonClick = {
                    navController.navigate(LeafScreen.Detail.createRoute(Screen.Overview, "abc"))
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = LeafScreen.Detail.createRoute(Screen.Overview),
            arguments = listOf(
                navArgument("detailId") { type = NavType.StringType }
            )) {
            val detailScreenViewModel: DetailScreenViewModel = viewModel()
            DetailScreen(viewModel = detailScreenViewModel) { navController.popBackStack() }
        }
    }
}
