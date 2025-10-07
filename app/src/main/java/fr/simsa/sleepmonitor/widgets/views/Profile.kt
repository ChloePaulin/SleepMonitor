package fr.simsa.sleepmonitor.widgets.views

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
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.AppName
import fr.simsa.sleepmonitor.widgets.styles.forms.Button

@Composable
fun Profile(modifier: Modifier = Modifier) {

    var userTest by remember {
        mutableStateOf(
            mapOf(
                "id" to "e152361",
                "username" to "JohnDoe",
                "email" to "john-doe@example.fr",
                "password" to "test123456",
                "created_at" to "2023-11-15"
            )
        )
    }

    var showModification by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf(userTest["username"] ?: "Unknown User") }
    var email by remember { mutableStateOf(userTest["email"] ?: "") }
    var password by remember { mutableStateOf(userTest["password"] ?: "") }

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
                "Identifiant : ${userTest["id"]}",
                color = BlueNightBackground,
                fontFamily = latoRegular
            )
        }
        Row {
            Text(
                "Nom : ${userTest["username"]}",
                color = BlueNightBackground,
                fontFamily = latoRegular
            )
        }
        Row {
            Text(
                "E-mail : ${userTest["email"]}",
                color = BlueNightBackground,
                fontFamily = latoRegular
            )
        }
        Row {
            Text(
                "Date de création : ${userTest["created_at"]}",
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
                onClick = { showModification = true }
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
                onClick = { println("Bouton Se déconnecter") }
            )
            {
                Text(
                    "Se déconnecter",
                    color = Color.Red
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
                    tonalElevation = 8.dp,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Nom d'utilisateur") }
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") }
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Nouveau mot de passe") },
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { showModification = false }) {
                                Text("Annuler")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                // Sauvegarde ou actions ici
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
