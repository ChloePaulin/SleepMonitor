package fr.simsa.sleepmonitor.widgets.styles

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice

@Composable
fun CircleForm(
    modifier: Modifier = Modifier,
    height: Int = 70,
    shape: Shape = CircleShape,
    color: Color = BlueLightPolice,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier
            .height(height.dp),
        shape = shape,
        color = color,
    ) {
        content()
    }
}
