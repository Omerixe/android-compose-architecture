package ch.omerixe.androidarchitecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    onDetailClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onMenuClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.semantics { contentDescription = "Home Screen" },
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(onClick = { onMenuClicked() }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column() {
            Text(text = "HomeScreen!")
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