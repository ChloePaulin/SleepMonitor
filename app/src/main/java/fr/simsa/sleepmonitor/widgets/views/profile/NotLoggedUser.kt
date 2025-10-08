package fr.simsa.sleepmonitor.widgets.views.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

/**
 * Vue affichée quand aucun utilisateur n’est connecté.
 */
@Composable
fun NotLoggedUser(
    modifier: Modifier = Modifier,
    onLoginClick: (String, String) -> Unit
) {
    var showLogIn by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BlueLightPolice)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppName(
            value = "Mon Profil",
            color = BlueNightBackground,
            fontSize = 45
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Aucun utilisateur connecté",
            color = BlueNightBackground
        )
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Button(
            modifier = Modifier
                .background(Color(70, 130, 180),
                    CircleShape),
            shape = CircleShape,
            onClick = {
                showLogIn = true
            }
        ) {
            Text(
                "Se connecter",
                color = Color.White)
        }
    }

// Se connecter
    if (showLogIn) {
        Dialog(
            onDismissRequest = {
                showLogIn = false
            }
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
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            showLogIn = false
                        }
                        ) {
                            Text("Annuler")
                        }
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        Button(onClick = {
                            onLoginClick(email, password)
                            showLogIn = false
                        }) {
                            Text("Valider")
                        }
                    }
                }
            }
        }
    }
}
