package com.kopidannyemil.app.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kopidannyemil.app.AppViewModel
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.ui.components.GradientBackground
import com.kopidannyemil.app.ui.components.PrimaryButton
import com.kopidannyemil.app.ui.theme.*

@Composable
fun ConfirmationScreen(vm: AppViewModel, onOk: () -> Unit) {
    BackHandler { onOk() }
    var show by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { show = true }
    val scale by animateFloatAsState(
        if (show) 1f else 0f,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "scale"
    )

    GradientBackground {
        Column(
            Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))
            Text("Halo ${vm.userName},", style = MaterialTheme.typography.headlineLarge, color = Blue900)
            Spacer(Modifier.weight(1f))
            Box(
                Modifier.graphicsLayer { scaleX = scale; scaleY = scale }
                    .size(120.dp).background(Blue100, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(Modifier.size(88.dp).background(Blue600, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Check, "OK", tint = White, modifier = Modifier.size(52.dp))
                }
            }
            Spacer(Modifier.height(28.dp))
            Text(
                "Terima kasih sudah memesan!\nTunggu saja, sebentar lagi kurir kami akan mengantarkan pesananmu.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(20.dp))
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("${vm.lastOrderCount} item  •  ${vm.lastOrderTotal.toRupiah()}", fontWeight = FontWeight.Bold, color = Blue700)
                    Spacer(Modifier.height(4.dp))
                    Text("Dikirim ke: ${vm.lastOrderAddress}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(Modifier.weight(1.2f))
            PrimaryButton("Oke", onOk)
        }
    }
}
