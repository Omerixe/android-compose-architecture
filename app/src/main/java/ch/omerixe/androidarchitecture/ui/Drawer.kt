package ch.omerixe.androidarchitecture.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ch.omerixe.androidarchitecture.Screen

@Composable
internal fun Drawer(
    modifier: Modifier = Modifier,
    screens: List<Screen>,
    activeRoute: () -> String?,
    onDestinationClicked: (screen: Screen) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        screens.forEach { screen ->
            val isActiveScreen = activeRoute()?.contains(screen.route)
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
                style = MaterialTheme.typography.h4,
                color = if (isActiveScreen == true) Color.Green else MaterialTheme.colors.primary,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen)
                }
            )
        }
    }
}