package com.kopidannyemil.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kopidannyemil.app.ui.components.GradientBackground
import com.kopidannyemil.app.ui.components.PrimaryButton
import com.kopidannyemil.app.ui.theme.*

@Composable
fun SplashScreen(onStart: () -> Unit) {
    var show by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { show = true }
    val scale by animateFloatAsState(
        if (show) 1f else 0.5f,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = "scale"
    )
    val alpha by animateFloatAsState(if (show) 1f else 0f, tween(900), label = "alpha")

    GradientBackground {
        Column(
            Modifier.fillMaxSize().padding(28.dp).navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .graphicsLayer { scaleX = scale; scaleY = scale; this.alpha = alpha }
                    .shadow(16.dp, CircleShape)
                    .size(150.dp)
                    .background(White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier.size(118.dp).background(Blue500, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LocalCafe, null, tint = White, modifier = Modifier.size(64.dp))
                }
            }
            Spacer(Modifier.height(28.dp))
            Text(
                "Kopi dan Nyemil",
                style = MaterialTheme.typography.headlineLarge,
                color = Blue900,
                modifier = Modifier.graphicsLayer { this.alpha = alpha }
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Get a taste of your wish",
                style = MaterialTheme.typography.bodyLarge,
                color = InkSoft,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { this.alpha = alpha }
            )
            Spacer(Modifier.weight(1.2f))
            PrimaryButton("Start now", onStart)
            Spacer(Modifier.height(8.dp))
        }
    }
}
