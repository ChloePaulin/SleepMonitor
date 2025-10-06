package fr.simsa.sleepmonitor.widgets.styles

import androidx.annotation.Size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import fr.simsa.sleepmonitor.R
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice

val deliusSwashCaps = FontFamily(
    Font(R.font.delius_swash_caps_regular)
)

@Composable
fun AppName(
    value:String = "SleepMonitor",
    color: Color = BlueLightPolice,
    fontSize:Int = 32,
) {
    Text(
        text = value,
        color = color,
        fontFamily = deliusSwashCaps,
        fontSize = fontSize.sp,
    )
}
