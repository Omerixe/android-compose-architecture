package ch.omerixe.androidarchitecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OverviewScreen(onButtonClick: () -> Unit, onMenuClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Overview") },
                navigationIcon = {
                    IconButton(onClick = { onMenuClicked() }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column() {
            Text(text = "OverviewScreen!")
            OutlinedButton(onClick = onButtonClick) {
                Text(text = "Click me!")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    OverviewScreen({}, {})
}