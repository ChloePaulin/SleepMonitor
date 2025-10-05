package fr.simsa.sleepmonitor.widgets.views

import android.R.attr.onClick
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

@Composable
fun Footer(modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableIntStateOf(1) }
    val onglets = listOf("Historique", "Accueil", "Profil")
    NavigationBar {
        onglets.forEachIndexed { index, onglet ->
            NavigationBarItem(
                label = { Text(onglet) },
                selected = selectedItem == index,
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.home_24dp),
                        contentDescription = "Home"
                )
                },
                onClick = {
                    selectedItem = index
                    println("Click sur onglet $onglet")
                }
            )
        }
    }
}
