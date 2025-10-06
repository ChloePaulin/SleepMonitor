package fr.simsa.sleepmonitor.widgets.styles.forms

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

@Composable
fun Button(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    onClick: () -> Unit = {  },
    content:@Composable () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        onClick = { onClick() }
    ) {
        content()
    }
}
