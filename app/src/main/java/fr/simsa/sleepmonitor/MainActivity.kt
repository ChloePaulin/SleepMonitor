package fr.simsa.sleepmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import fr.simsa.sleepmonitor.ui.theme.SleepMonitorTheme
import fr.simsa.sleepmonitor.widgets.views.EnTete
import fr.simsa.sleepmonitor.widgets.views.Footer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepMonitorTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                    topBar = { EnTete()},
                    bottomBar = { Footer() },
                ){
                }
            }
        }
    }
}
