package fr.simsa.sleepmonitor.widgets.views.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fr.simsa.sleepmonitor.models.User
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.AppName
import fr.simsa.sleepmonitor.widgets.styles.forms.Button
import fr.simsa.sleepmonitor.widgets.views.pages.latoRegular
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Vue affichée quand un utilisateur est connecté.
 * @param user L'utilisateur est obligatoirement connecté.
 * @param onLogout Fonction appelée lors du clic sur le bouton de déconnexion.
 * @param onUpdate Fonction appelée lors du clic sur le bouton de modification du profil.
 * @param modifier Modifier optionnel.
 */
@Composable
fun LoggedUser(
    user: User,
    onLogout: () -> Unit,
    onUpdate: (User, String?, String?) -> Unit,
    modifier: Modifier = Modifier,
) {

    /**
     * Boîte de dialogue pour modifier le profil.
     */
    var showModification by remember { mutableStateOf(false) }

    // Variables pour le formulaire de modification.
    var username by remember { mutableStateOf(user.username) }
    var email by remember { mutableStateOf(user.email) }
    var password by remember { mutableStateOf("") } // Initialisé vide car pas stocké

    // Date formater pour l'affichage.
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE)
    val formattedDate = user.createdAt?.let { dateFormatter.format(it) } ?: "Non disponible"

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
                value = "Mon Profil",
                color = BlueNightBackground,
                fontSize = 45,
            )
        }
        Row {
            Text(
                "Nom : ${user.username}",
                color = BlueNightBackground,
                fontFamily = latoRegular
            )
        }
        Row {
            Text(
                "E-mail : ${user.email}",
                color = BlueNightBackground,
                fontFamily = latoRegular
            )
        }
        Row {
            Text(
                "Date de création : $formattedDate",
                color = BlueNightBackground,
                fontFamily = latoRegular
            )
        }

        Row {
            Button(
                modifier = Modifier.background(
                    color = Color(70, 130, 180),
                    shape = CircleShape
                ),
                shape = CircleShape,
                onClick = {
                    // Valeurs du formulaire réinitialiser
                    username = user.username
                    email = user.email
                    // Password toujours vide
                    password = ""
                    showModification = true
                }
            )
            {
                Text(
                    "Modifier mon profil",
                    color = Color(70, 130, 180)
                )
            }
        }

        Row {
            Button(
                modifier = Modifier.background(
                    color = Color.Red,
                    shape = CircleShape
                ),
                shape = CircleShape,
                onClick = {
                    println("Bouton Se déconnecter")
                    onLogout()
                }
            )
            {
                Text(
                    "Se déconnecter",
                    color = Color.White
                )
            }
        }

        // Modification du profil via boîte de dialogue.
        if (showModification) {
            Dialog(
                onDismissRequest = { showModification = false }
            )
            {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 5.dp,
                    modifier = Modifier.padding(16.dp),
                    color = BlueNightBackground
                ) {
                    Column(
                        Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = {
                                Text(
                                    "Nom d'utilisateur",
                                    color = BlueLightPolice
                                )
                            }
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = {
                                Text(
                                    "Email",
                                    color = BlueLightPolice
                                )
                            }
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = {
                                Text(
                                    "Nouveau mot de passe (optionnel)",
                                    color = BlueLightPolice
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            placeholder = {
                                Text(
                                    "Laisser vide si inchangé",
                                    color = BlueLightPolice.copy(alpha = 0.5f)
                                )
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = {
                                // Réinitialiser et fermer si on clique sur annuler.
                                username = user.username
                                email = user.email
                                password = ""
                                showModification = false
                            }
                            ) {
                                Text("Annuler")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                val updatedUser = user.copy(
                                    username = username
                                )

                                // Envoyer le nouveau email et mot de passe.
                                // Si password est vide, on envoie null.
                                val newPassword = if (password.isBlank()) null else password

                                onUpdate(updatedUser, email, newPassword)

                                // Réinitialiser le mot de passe après envoi pour qu'il reste null.
                                password = ""
                                showModification = false
                            }) {
                                Text("Valider")
                            }
                        }
                    }
                }
            }
        }
    }
}
