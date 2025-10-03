package fr.simsa.sleepmonitor.widgets.styles

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun RectangleForm(
    modifier: Modifier = Modifier,
    height: Int = 150,
    shape: Shape = RectangleShape,
    color: Color = Color(154,213,233),
    content:@Composable () -> Unit,
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
