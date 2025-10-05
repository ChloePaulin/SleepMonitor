package fr.simsa.sleepmonitor.widgets.styles

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.simsa.sleepmonitor.R

@Composable
fun EnTete() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            Modifier.padding(
                bottom = innerPadding
                    .calculateBottomPadding()
            )
        ) {
            RectangleForm(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(65.dp),
                        contentAlignment = Alignment.Center,
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.logo_sleep_monitor),
                            contentDescription = "Logo Sleep Monitor",
                            Modifier
                                .size(65.dp)
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        AppName()
                    }
                }
            }
        }
    }
}
