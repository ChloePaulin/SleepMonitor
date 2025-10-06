package fr.simsa.sleepmonitor.widgets.views

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fr.simsa.sleepmonitor.R
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground

/**
 * Barre de navigation qui s'affiche en bas de l'écran avec trois onglets :
 * **Historique**, **Accueil**, et **Profil**.
 *
 * Il y a un suivi d'état avec (`selectedItem`) pour suivre l'onglet
 * sélectionné et met à jour l'interface lorsqu'un onglet est cliqué.
 */

@Composable
fun Footer(modifier: Modifier = Modifier) {
    /**
     * Représente l'onglet actuellement sélectionné
     */
    var selectedItem by remember { mutableIntStateOf(1) }

    /**
     * Liste des onglets à afficher dans la barre de navigation.
     */
    val onglets = listOf("Historique", "Accueil", "Profil")

    NavigationBar(
        containerColor = BlueNightBackground
    ) {
        onglets.forEachIndexed { index, onglet ->
            NavigationBarItem(
                label = {
                    Text(
                        onglet,
                        color = BlueLightPolice
                    )
                },
                selected = selectedItem == index,
                icon = {
                    when (onglet) {
                        onglets[0] -> Image(
                            painter = painterResource(id = R.drawable.history_60dp_bluelight),
                            contentDescription = "Historique"
                        )

                        onglets[1] -> Image(
                            painter = painterResource(id = R.drawable.home_60dp_bluelight),
                            contentDescription = "Accueil"
                        )

                        onglets[2] -> Image(
                            painter = painterResource(id = R.drawable.account_box_60dp_bluelight),
                            contentDescription = "Profil"
                        )
                    }
                },
                onClick = {
                    selectedItem = index
                    println("Click sur onglet $onglet")
                }
            )
        }
    }
}
