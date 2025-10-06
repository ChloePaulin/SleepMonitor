package fr.simsa.sleepmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.ui.theme.SleepMonitorTheme
import fr.simsa.sleepmonitor.widgets.views.EnTete
import fr.simsa.sleepmonitor.widgets.views.Footer
import fr.simsa.sleepmonitor.widgets.views.Home

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(
                scrim = BlueNightBackground.toArgb()
            )
        )
        setContent {
            SleepMonitorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { EnTete() },
                    bottomBar = { Footer() }
                ) { innerPadding ->
                    Home(modifier = Modifier
                        .padding(innerPadding)
                    )
                }
            }
        }
    }
}
