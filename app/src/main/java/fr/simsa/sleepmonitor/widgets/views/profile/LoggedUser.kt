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
import fr.simsa.sleepmonitor.widgets.views.latoRegular

/**
 * Vue affichée quand un utilisateur est connecté.
 */
@Composable
fun LoggedUser(
    user: User,
    onLogout: () -> Unit,
    onUpdate: (User) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showModification by remember { mutableStateOf(false) }

    var id by remember { mutableStateOf(user.id) }
    var username by remember { mutableStateOf(user.username) }
    var email by remember { mutableStateOf(user.email) }
    var password by remember { mutableStateOf(user.password) }
    var createdAt by remember { mutableStateOf(user.createdAt) }

    Column(
        modifier = modifier
            .background(
                color = BlueLightPolice
            )
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
                "Identifiant : ${user.id}",
                color = BlueNightBackground,
                fontFamily = latoRegular
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
                "Date de création : ${user.createdAt}",
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
                    username = user.username
                    email = user.email
                    password = user.password
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

        // Modifier le profil via popup
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
                                    "Nouveau mot de passe",
                                    color = BlueLightPolice
                                )
                            },
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = {
                                username = user.username
                                email = user.email
                                password = user.password
                                showModification = false
                            }
                            ) {
                                Text("Annuler")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                val updatedUser = user.copy(
                                    username = username,
                                    email = email,
                                    password = password
                                )
                                onUpdate(updatedUser)
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
