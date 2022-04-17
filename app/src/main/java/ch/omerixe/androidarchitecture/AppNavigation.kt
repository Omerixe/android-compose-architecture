package ch.omerixe.androidarchitecture

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ch.omerixe.androidarchitecture.ui.*
import ch.omerixe.androidarchitecture.ui.web.MyWebViewClient
import ch.omerixe.androidarchitecture.ui.web.WebScreen
import ch.omerixe.androidarchitecture.ui.web.WebScreenViewModel
import ch.omerixe.androidarchitecture.ui.web.WebScreenViewModelFactory
import kotlinx.coroutines.launch

internal sealed class Screen(val title: String, val route: String) {
    object Home : Screen("Home", "home")
    object Overview : Screen("Overview", "overview")
    object Login : Screen("Login", "login")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Home : LeafScreen("home")
    object Overview : LeafScreen("overview")
    object Login : LeafScreen("login")
    object Web : LeafScreen("web")

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
    var drawerGesturesEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var currentScreen: Screen by remember {
        mutableStateOf(Screen.Home)
    }

    ModalDrawer(
        drawerContent = {
            Drawer(
                screens = listOf(
                    Screen.Home,
                    Screen.Overview
                ),
                activeRoute = { currentScreen.route },
                onDestinationClicked = { screen ->
                    scope.launch {
                        drawerState.close()
                        currentScreen = screen
                    }
                    if (screen.route == Screen.Home.route) {
                        navController.popBackStack(
                            route = LeafScreen.Home.createRoute(Screen.Home),
                            inclusive = false,
                            saveState = true
                        )
                    } else {
                        navController.navigate(route = screen.route) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(route = LeafScreen.Home.createRoute(Screen.Home)) {
                                    inclusive = false
                                }
                            }
                            launchSingleTop = true
                        }
                    }
                }
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerGesturesEnabled
    ) {
        val enableDarawerGestures: (Boolean) -> Unit = { drawerGesturesEnabled = it }
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            addHomeGraph(
                navController = navController,
                loggedIn = mainViewModel.loggedIn,
                logOut = mainViewModel::logOut,
                drawerState = drawerState,
                enableDrawerGestures = enableDarawerGestures,
            )
            addLoginGraph(
                navController = navController,
                logIn = mainViewModel::logIn,
                enableDrawerGestures = enableDarawerGestures
            )
            addOverviewGraph(
                navController = navController,
                drawerState = drawerState,
                enableDrawerGestures = enableDarawerGestures,
            )
        }
    }

}

private fun NavGraphBuilder.addHomeGraph(
    navController: NavHostController,
    loggedIn: MutableState<Boolean>,
    logOut: () -> Unit,
    drawerState: DrawerState,
    enableDrawerGestures: (Boolean) -> Unit,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home)
    ) {
        composable(route = LeafScreen.Home.createRoute(Screen.Home)) {
            val scope = rememberCoroutineScope()
            enableDrawerGestures(true)

            LaunchedEffect(loggedIn.value) {
                if (!loggedIn.value) {
                    navController.navigate(Screen.Login.route)
                }
            }
            HomeScreen(
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
                },
                onWebClicked = {
                    navController.navigate(LeafScreen.Web.createRoute(Screen.Home))
                }
            )
        }
    }
    composable(route = LeafScreen.Web.createRoute(Screen.Home)) {
        enableDrawerGestures(false)
        val viewModelFactory = WebScreenViewModelFactory(
            webViewClient = MyWebViewClient(),
            url = "https://www.google.ch"
        )
        val viewModel: WebScreenViewModel = viewModel(factory = viewModelFactory)
        WebScreen(viewModel = viewModel)
    }
}

private fun NavGraphBuilder.addLoginGraph(
    navController: NavHostController,
    logIn: () -> Unit,
    enableDrawerGestures: (Boolean) -> Unit
) {
    navigation(
        route = Screen.Login.route,
        startDestination = LeafScreen.Login.createRoute(Screen.Login)
    ) {
        composable(route = LeafScreen.Login.createRoute(Screen.Login)) {
            enableDrawerGestures(false)
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
    navController: NavController,
    drawerState: DrawerState,
    enableDrawerGestures: (Boolean) -> Unit,
) {
    navigation(
        route = Screen.Overview.route,
        startDestination = LeafScreen.Overview.createRoute(Screen.Overview),
    ) {
        enableDrawerGestures(true)
        composable(route = LeafScreen.Overview.createRoute(Screen.Overview)) {
            val scope = rememberCoroutineScope()
            OverviewScreen(
                onButtonClick = {
                    navController.navigate(LeafScreen.Detail.createRoute(Screen.Overview, "abc"))
                },
                onMenuClicked = { scope.launch { drawerState.open() } }
            )
        }
        composable(route = LeafScreen.Detail.createRoute(Screen.Overview),
            arguments = listOf(
                navArgument("detailId") { type = NavType.StringType }
            )) {
            enableDrawerGestures(false)
            val detailScreenViewModel: DetailScreenViewModel = viewModel()
            DetailScreen(viewModel = detailScreenViewModel) { navController.popBackStack() }
        }
    }
}
