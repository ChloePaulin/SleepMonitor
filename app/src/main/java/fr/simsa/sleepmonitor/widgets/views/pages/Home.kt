package fr.simsa.sleepmonitor.widgets.views.pages

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import fr.simsa.sleepmonitor.R
import fr.simsa.sleepmonitor.data.lights.LightRepository.uploadSleepDataToFirebase
import fr.simsa.sleepmonitor.models.LightVariationEvent
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.forms.Button
import fr.simsa.sleepmonitor.widgets.styles.forms.RectangleForm

val latoRegular = FontFamily(
    Font(R.font.lato_regular)
)

/**
 * Écran d'accueil de l'application qui permet de lancer ou d'arrêter l'application.
 * @param modifier Modifier à appliquer à l'écran.
 */
@Composable
fun Home(modifier: Modifier = Modifier) {

    /**
     * Indique le seuil à dépasser pour détecter une variation de lumière.
     */
    val threshold = 100f

    /**
     * Contexte dans lequel l'application est exécutée.
     */
    val context = LocalContext.current

    /**
     * Valeur de la lumière initialisée à 0.
     */
    val lightValue = remember { mutableFloatStateOf(0f) }

    /**
     * Compteur de variations de lumière.
     */
    val variationCount = remember { mutableIntStateOf(0) }

    /**
     * Valeur de la dernière variation de lumière.
     */
    var lastValue by remember { mutableStateOf<Float?>(null) }

    /**
     * Indique si l'enregistrement de variations de lumière est actif ou non.
     */
    var sleepMonitorLog by remember { mutableStateOf(false) }

    /**
     * Liste des événements de variations de lumière.
     */
    var variationEvents by remember { mutableStateOf(listOf<LightVariationEvent>()) }

    /**
     * Indique si la boite de résumé est visible ou non.
     */
    var showVariationDialog by remember { mutableStateOf(false) }

    DisposableEffect(context) {

        /**
         * Objet de gestion du capteur de lumière.
         */
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        /**
         * Type de capteur, ici capteur de lumière.
         */
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        /**
         * Listener qui gère les évènements du capteur de lumière.
         */
        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                /*
                Rien à faire ici mais obligatoire pour le code.
                */
            }

            override fun onSensorChanged(event: SensorEvent) {
                val lux = event.values[0]
                lastValue?.let { last ->
                    if (kotlin.math.abs(lux - last) > threshold) {
                        variationCount.intValue += 1
                        if (sleepMonitorLog) {
                            variationEvents = variationEvents + LightVariationEvent(
                                timestamp = System.currentTimeMillis(),
                                lux = lux
                            )
                        }
                    }
                }
                // Change la valeur de la dernière variation de lumière.
                lastValue = lux

                lightValue.floatValue = lux
            }
        }
        sensorManager.registerListener(
            listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL
        )
        onDispose {
            sensorManager.unregisterListener(listener)
        }
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
                contentDescription = "Sleep Monitor fond d'écran",
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 300.dp)
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

            // Bouton Start/Stop
            Button(
                onClick = {
                    if (sleepMonitorLog) {
                        // Stop
                        sleepMonitorLog = false
                        showVariationDialog = true
                    } else {
                        // Start : réinitialise tout
                        variationEvents = emptyList()
                        variationCount.intValue = 0
                        sleepMonitorLog = true
                    }
                },
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .size(150.dp),
                shape = CircleShape,
            ) {
                Image(
                    painter = if (sleepMonitorLog)
                        painterResource(id = R.drawable.stop_circle_90dp_bluedark)
                    else
                        painterResource(id = R.drawable.play_circle_90dp_bluedark),
                    contentDescription = if (sleepMonitorLog)
                        "Arrêter l'enregistrement"
                    else
                        "Démarrer l'enregistrement",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }

    // Résumé de la nuit
    if (showVariationDialog) {
        Dialog(onDismissRequest = { showVariationDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 5.dp,
                modifier = Modifier.padding(16.dp),
                color = BlueNightBackground
            ) {
                Column(
                    Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Résumé de la nuit",
                        color = BlueLightPolice,
                        fontWeight = FontWeight.Bold,
                        fontSize = 6.em
                    )
                    Text(
                        "Nombre de variations lumineuses : ${variationCount.intValue}",
                        color = BlueLightPolice
                    )
                    println(variationCount.intValue)
                    Button(
                        onClick = {
                            showVariationDialog = false
                            println(variationEvents)
                            uploadSleepDataToFirebase(variationEvents)
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Valider")
                    }
                }
            }
        }
    }
}
