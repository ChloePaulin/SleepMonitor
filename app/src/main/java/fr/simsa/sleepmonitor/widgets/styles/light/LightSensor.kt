package fr.simsa.sleepmonitor.widgets.styles.light

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf

/**
 * Récupération de la luminosité ambiante et comptage des variations.
 */
class LightSensorActivity : Activity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private var _currentLight = mutableFloatStateOf(0f)
    private var _variationCount = mutableIntStateOf(0)

    private var lastValue: Float? = null
    private val threshold = 10f // différence minimale en lux pour compter une variation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation capteur
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Interface Jetpack Compose
//        setContent {
//            MaterialTheme {
//                Surface(modifier = Modifier.fillMaxSize()) {
//                    LightSensorScreen(
//                        currentLight = _currentLight.value,
//                        variationCount = _variationCount.value
//                    )
//                }
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent) {
        val lux = event.values[0]

        // Calcul variation
        lastValue?.let { last ->
            if (kotlin.math.abs(lux - last) > threshold) {
                _variationCount.value += 1
            }
        }
        lastValue = lux
        _currentLight.floatValue = lux
    }
}