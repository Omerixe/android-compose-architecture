package ch.omerixe.androidarchitecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(onButtonClick: () -> Unit) {
    Scaffold(

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