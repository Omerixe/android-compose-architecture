package ch.omerixe.androidarchitecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    onOverViewClick: () -> Unit,
    onDetailClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column() {
            Text(text = "HomeScreen!")
            OutlinedButton(onClick = onOverViewClick) {
                Text(text = "Click me (Overview)!")
            }
            OutlinedButton(onClick = onDetailClick) {
                Text(text = "Click me (Detail)!")
            }
            OutlinedButton(onClick = onLogoutClick) {
                Text(text = "Log Out!")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}, {})
}