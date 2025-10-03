package fr.simsa.sleepmonitor.widgets.styles

import android.graphics.fonts.FontFamily
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice

@Composable
fun AppName() {
    Text("SleepMonitor",
        Color = BlueLightPolice,
        fontFamily = FontFamily.Builder(""))
}