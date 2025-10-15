package fr.simsa.sleepmonitor.widgets.views.pages

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import fr.simsa.sleepmonitor.R
import fr.simsa.sleepmonitor.models.LightVariationEvent
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.forms.Button
import fr.simsa.sleepmonitor.widgets.styles.forms.RectangleForm

val latoRegular = FontFamily(
    Font(R.font.lato_regular)
)

@Composable
fun Home(modifier: Modifier = Modifier) {
    var threshold = 30f
    val context = LocalContext.current
    val lightValue = remember { mutableFloatStateOf(0f) }
    val variationCount = remember { mutableIntStateOf(0) }
    var lastValue by remember { mutableStateOf<Float?>(null) }
    var sleepMonitorLog by remember { mutableStateOf(false) }
    var variationEvents by remember { mutableStateOf(listOf<LightVariationEvent>()) }

    DisposableEffect(context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                /* Rien à faire ici */
            }

            override fun onSensorChanged(event: SensorEvent) {
                val lux = event.values[0]
                lastValue?.let { last ->
                    if (kotlin.math.abs(lux - last) > threshold) {
                        variationCount.intValue += 1
                        // On stocke l'événement si l'enregistrement est actif
                        if (sleepMonitorLog) {
                            variationEvents = variationEvents + LightVariationEvent(
                                timestamp = System.currentTimeMillis(),
                                lux = lux
                            )
                        }
                        lastValue = lux
                        lightValue.floatValue = lux
                    }
                }
            }
        }
        sensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose { sensorManager.unregisterListener(listener) }
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
                        if (sleepMonitorLog) sleepMonitorLog = false
                        else {
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
