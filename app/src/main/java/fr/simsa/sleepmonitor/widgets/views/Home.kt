package fr.simsa.sleepmonitor.widgets.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import fr.simsa.sleepmonitor.R
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.forms.Button
import fr.simsa.sleepmonitor.widgets.styles.forms.RectangleForm

val latoRegular = FontFamily(
    Font(R.font.lato_regular)
)

@Composable
fun Home(modifier: Modifier = Modifier) {

    RectangleForm(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.sleep_monitor_background),
                contentDescription = "Fond d'éran application",
                modifier = Modifier.fillMaxSize()
            )
            Row(modifier = modifier
                .padding(32.dp)
                .background(color = BlueNightBackground)
            ) {
                Text(
                    "Écoutez ce que vos nuits ont à vous dire.",
                    color = BlueLightPolice,
                    fontSize = 8.em,
                    fontFamily = latoRegular,
                    letterSpacing = 0.1.em,
                    lineHeight = 1.em,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {},
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .size(150.dp),
                shape = CircleShape,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.play_circle_90dp_bluedark),
                    contentDescription = "Démarrer l'enregistrement",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}