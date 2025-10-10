package fr.simsa.sleepmonitor.widgets.views

import androidx.compose.runtime.Composable
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
    var currentUser by remember { mutableStateOf<User?>(null) }
    val scope = rememberCoroutineScope()

    if (currentUser == null) {
        NotLoggedUser(
            onLoginClick = { email, password ->
                scope.launch {
                    val user = UserRepository.loginUser(email, password)
                    if (user != null) {
                        currentUser = user
                        println("✅ Connexion réussie : ${user.username}")
                    } else {
                        println("❌ Identifiants incorrects")
                    }
                }
            },
            onRegisterClick = { username, email, password ->
                scope.launch {
                    val newUser = UserRepository.registerUser(username, email, password)
                    if (newUser != null) {
                        currentUser = newUser
                        println("✅ Utilisateur créé : ${newUser.username}")
                    } else {
                        println("❌ Erreur lors de la création du compte")
                    }
                }
            },
            modifier = modifier
        )
    } else {
        LoggedUser(
            user = currentUser!!,
            onLogout = { currentUser = null },
            onUpdate = { updatedUser ->
                scope.launch {
                    val success = UserRepository.updateUser(updatedUser)
                    if (success) {
                        currentUser = updatedUser
                        println("✅ Profil mis à jour dans Firestore")
                    } else {
                        println("❌ Échec de la mise à jour du profil")
                    }
                }
            },
            modifier = modifier
        )
    }
}
