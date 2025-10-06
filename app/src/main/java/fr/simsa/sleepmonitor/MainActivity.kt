package fr.simsa.sleepmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.ui.theme.SleepMonitorTheme
import fr.simsa.sleepmonitor.widgets.views.EnTete
import fr.simsa.sleepmonitor.widgets.views.Footer
import fr.simsa.sleepmonitor.widgets.views.History
import fr.simsa.sleepmonitor.widgets.views.Home
import fr.simsa.sleepmonitor.widgets.views.Profile

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
                /**
                 * Représente l'onglet actuellement sélectionné
                 */
                var selectedItem by remember { mutableIntStateOf(2) }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = { EnTete() },
                    bottomBar = {
                        Footer(
                            selectedItem = selectedItem,
                            onItemSelected = { index -> selectedItem = index }
                        )
                    }
                ) { innerPadding ->
                    /**
                     * Appel de la vue concernée par l'item/l'onglet sélectionné (`selectedItem`)
                     */
                    when (selectedItem) {
                        0 -> History(
                            modifier = Modifier
                                .padding(innerPadding)
                        )

                        1 -> Home(
                            modifier = Modifier
                                .padding(innerPadding)
                        )

                        2 -> Profile(
                            modifier = Modifier
                                .padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
