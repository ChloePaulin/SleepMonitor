package fr.simsa.sleepmonitor.widgets.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.simsa.sleepmonitor.models.User
import fr.simsa.sleepmonitor.widgets.views.profile.LoggedUser
import fr.simsa.sleepmonitor.widgets.views.profile.NotLoggedUser

/**
 * Écran principal du profil.
 * Gère la logique de connexion / déconnexion et d’affichage.
 */
@Composable
fun Profile(modifier: Modifier = Modifier,
            viewModel: ViewModel = viewModel()) {

    var currentUser by remember { mutableStateOf<User?>(null) }

    if (currentUser == null) {
        NotLoggedUser(
            onLoginClick = {
                email, password ->
                viewModel.login(email, password)
            },
            modifier = modifier
        )
    } else {
        LoggedUser(
            user = currentUser!!,
            onLogout = { currentUser = null },
            onUpdate = { updatedUser -> currentUser = updatedUser },
            modifier = modifier
        )
    }
}
