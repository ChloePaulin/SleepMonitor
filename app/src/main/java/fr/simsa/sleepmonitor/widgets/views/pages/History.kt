package fr.simsa.sleepmonitor.widgets.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import fr.simsa.sleepmonitor.data.api.QuotesAPI
import fr.simsa.sleepmonitor.data.users.UserRepository
import fr.simsa.sleepmonitor.models.User
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.AppName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun History(
    modifier: Modifier = Modifier
) {
    /**
     * Utilisateur actuellement connecté.
     */
    var currentUser by remember { mutableStateOf<User?>(null) }

    var quote by remember { mutableStateOf<String?>(null) }

    /**
     * Coroutine pour gérer les appels à la Firebase.
     */
    val scope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(false) }

    // Vérifier si un utilisateur est déjà connecté au démarrage et le récupère.
    LaunchedEffect(Unit) {
        currentUser = UserRepository.getCurrentUser()
    }

    if (currentUser != null) {
        Column(
            modifier = modifier
                .background(color = BlueLightPolice)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row {
                AppName(
                    value = "Mon historique",
                    color = BlueNightBackground,
                    fontSize = 45,
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .background(color = BlueLightPolice)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row {
                AppName(
                    value = "Historique vide",
                    color = BlueNightBackground,
                    fontSize = 45,
                )
            }
            Row {
                Text(
                    "Veuillez vous connecter pour commencer à enregistrer des données",
                    color = BlueNightBackground,
                    modifier = modifier
                )
            }
            Row {
                Button(
                    onClick = {
                        isLoading = true
                        scope.launch {
                            try {
                                val api = QuotesAPI()
                                val fetchedQuote = withContext(Dispatchers.IO) {
                                    api.getRandomQuote()
                                }
                                // Afficher dans le terminal
                                println("Citation récupérée: ${fetchedQuote?.quote}")
                                println("Auteur: ${fetchedQuote?.author}")

                                quote = fetchedQuote?.quote ?: "Aucune citation disponible"
                            } catch (e: Exception) {
                                println("Erreur lors de la récupération: ${e.message}")
                                e.printStackTrace()
                                quote = "Erreur: ${e.message}"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Chargement..." else "Obtenir une citation")
                }
            }
        }
    }
}