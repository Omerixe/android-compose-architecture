package ch.omerixe.androidarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ch.omerixe.androidarchitecture.ui.theme.AndroidArchitectrureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val mainViewModel: MainViewModel = viewModel()
    AndroidArchitectrureTheme {
        val navController = rememberNavController()
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            AppNavigation(navController = navController, mainViewModel = mainViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}