package fr.simsa.sleepmonitor.widgets.styles

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import fr.simsa.sleepmonitor.R
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice

val deliusSwashCaps = FontFamily(
    Font(R.font.delius_swash_caps_regular)
)

@Composable
fun AppName() {
    Text("SleepMonitor",
        color = BlueLightPolice,
        fontFamily = deliusSwashCaps,
        fontSize = 32.sp
        )
}
