package fr.simsa.sleepmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.simsa.sleepmonitor.ui.theme.SleepMonitorTheme
import fr.simsa.sleepmonitor.widgets.styles.EnTete

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepMonitorTheme {
                EnTete()
            }
        }
    }
}
