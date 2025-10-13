package fr.simsa.sleepmonitor.widgets.views.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import fr.simsa.sleepmonitor.R
import fr.simsa.sleepmonitor.data.users.UserRepository
import fr.simsa.sleepmonitor.models.User
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.forms.Button
import fr.simsa.sleepmonitor.widgets.styles.forms.RectangleForm

val latoRegular = FontFamily(
    Font(R.font.lato_regular)
)

@Composable
fun Home(modifier: Modifier = Modifier) {

    /**
     * Enregistre les nuits de l'utilisateur.
     */
    var sleepMonitorLog by remember { mutableStateOf(false) }

    /**
     * Utilisateur actuellement connecté.
     */
    var currentUser by remember { mutableStateOf<User?>(null) }

    /**
     * Coroutine pour gérer les appels à la Firebase.
     */
    val scope = rememberCoroutineScope()

    // Vérifier si un utilisateur est déjà connecté au démarrage et le récupère.
    LaunchedEffect(Unit) {
        currentUser = UserRepository.getCurrentUser()
    }

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
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, top = 135.dp)
                        .background(color = BlueNightBackground)
                        .align(Alignment.TopCenter)
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
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Button(
                onClick = {
                    if (sleepMonitorLog) sleepMonitorLog = false else sleepMonitorLog = true
                    println("Click Me")
                },
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .size(150.dp),
                shape = CircleShape,
            ) {
                Image(
                    painter = if (sleepMonitorLog) painterResource(id = R.drawable.stop_circle_90dp_bluedark) else painterResource(
                        id = R.drawable.play_circle_90dp_bluedark
                    ),
                    contentDescription = if (sleepMonitorLog) "Arrêter l'enregistrement" else "Démarrer l'enregistrement",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}
