package fr.simsa.sleepmonitor.widgets.views.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fr.simsa.sleepmonitor.data.users.UserRepository
import fr.simsa.sleepmonitor.models.User
import fr.simsa.sleepmonitor.widgets.views.profile.LoggedUser
import fr.simsa.sleepmonitor.widgets.views.profile.NotLoggedUser
import kotlinx.coroutines.launch

/**
 * Écran principal du profil.
 * Gère la logique de connexion / déconnexion et d’affichage.
 */
@Composable
fun Profile(modifier: Modifier = Modifier) {

    /**
     * Utilisateur actuellement connecté.
     */
    var currentUser by remember { mutableStateOf<User?>(null) }

    /**
     * Message d'erreur à afficher si besoin.
     */
    var errorMessage by remember { mutableStateOf<String?>(null) }

    /**
     * Coroutine pour gérer les appels à la Firebase.
     */
    val scope = rememberCoroutineScope()

    // Vérifier si un utilisateur est déjà connecté au démarrage et le récupère.
    LaunchedEffect(Unit) {
        currentUser = UserRepository.getCurrentUser()
    }

    if (currentUser == null) {
        NotLoggedUser(
            onLoginClick = { email, password ->
                // launch permet de faire appel sans bloquer l'interface.
                scope.launch {
                    val result = UserRepository.loginUser(email, password)
                    result.onSuccess { user ->
                        currentUser = user
                        errorMessage = null
                        println("Connexion réussie : ${user.username}")
                    }.onFailure { error ->
                        errorMessage = error.message
                        println("Erreur connexion : ${error.message}")
                    }
                }
            },
            onRegisterClick = { username, email, password ->
                // launch permet de faire appel sans bloquer l'interface.
                scope.launch {
                    val result = UserRepository.registerUser(username, email, password)
                    result.onSuccess { user ->
                        currentUser = user
                        errorMessage = null
                        println("Inscription réussie : ${user.username}")
                    }.onFailure { error ->
                        errorMessage = error.message
                        println("Erreur inscription : ${error.message}")
                    }
                }
            },
            modifier = modifier
        )
    } else {
        LoggedUser(
            // Indique que l'utilisateur n'est pas null.
            user = currentUser!!,
            onLogout = {
                UserRepository.logout()
                currentUser = null
                println("Déconnexion réussie")
            },
            onUpdate = { updatedUser, newEmail, newPassword ->
                scope.launch {
                    val result = UserRepository.updateUser(updatedUser, newEmail, newPassword)
                    result.onSuccess { user ->
                        currentUser = user
                        println("Profil mis à jour")
                    }.onFailure { error ->
                        println("Erreur mise à jour : ${error.message}")
                    }
                }
            },
            modifier = modifier
        )
    }
}
