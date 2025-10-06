package fr.simsa.sleepmonitor.widgets.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice

@Composable
fun Profile(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(
                color = BlueLightPolice
            )
            .fillMaxSize()
    ) {
        Text("Je suis ton profil")
    }
}