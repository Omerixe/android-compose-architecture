package ch.omerixe.androidarchitecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(onButtonClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.semantics { contentDescription = "Login Screen" },
        topBar = {
            TopAppBar(
                title = { Text(text = "Login") },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Close, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column() {
            Text(text = "LoginScreen!")
            OutlinedButton(onClick = onButtonClick) {
                Text(text = "Click me!")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen({})
}