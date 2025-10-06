package fr.simsa.sleepmonitor.widgets.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.simsa.sleepmonitor.ui.theme.BlueLightPolice
import fr.simsa.sleepmonitor.ui.theme.BlueNightBackground
import fr.simsa.sleepmonitor.widgets.styles.AppName
import fr.simsa.sleepmonitor.widgets.styles.forms.Button

@Composable
fun Profile(modifier: Modifier = Modifier) {
    var userTest = mapOf(
        "id" to "e152361",
        "userName" to "JohnDoe",
        "email" to "john-doe@example.fr"
    )

    Column(
        modifier = modifier
            .background(
                color = BlueLightPolice
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            AppName(
                value = "Mon Profil",
                color = BlueNightBackground,
                fontSize = 45,
            )
        }
        // Text("Je suis ton profil comprenant : l'email, l'username et l'ancienneté")
        Row {
            OutlinedTextField(
                state = rememberTextFieldState(),
                label = {
                    Text(
                        "UserName",
                        color = BlueLightPolice
                    )
                },
                shape = RoundedCornerShape(50),
            )
        }
        Row {
            Button(
                shape = CircleShape
            ) {
                Text("Changer le mot de passe")
            }
        }
        Row {
            Button(
                modifier = Modifier.background(
                    color = BlueNightBackground,
                    shape = CircleShape
                ),
                shape = CircleShape,
                onClick = { println("Bouton Se déconnecter") }
            )
            {
                Text(
                    "Se déconnecter"
                )
            }
        }
    }
}