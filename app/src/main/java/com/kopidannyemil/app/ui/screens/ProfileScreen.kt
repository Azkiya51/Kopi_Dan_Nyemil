package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kopidannyemil.app.AppViewModel
import com.kopidannyemil.app.ui.components.*
import com.kopidannyemil.app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(vm: AppViewModel, nav: NavController, onLogout: () -> Unit) {
    var name by remember { mutableStateOf(vm.userName) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = White,
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = { AppBottomBar("profile", vm.totalItems, nav) }
    ) { pad ->
        GradientBackground(Modifier.padding(pad)) {
            Column(
                Modifier.fillMaxSize().statusBarsPadding().verticalScroll(rememberScrollState()).padding(28.dp)
            ) {
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(72.dp).background(Blue600, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            vm.userName.take(1).uppercase(),
                            color = White, fontSize = 32.sp, style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Halo ${vm.userName},", style = MaterialTheme.typography.headlineMedium, color = Blue900)
                        Text("@${vm.currentUser?.username ?: "-"}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(Modifier.height(32.dp))
                Text("Profile Saya", style = MaterialTheme.typography.titleLarge)
                Text("Profile Setting", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                AppTextField(name, { name = it }, "Nama", icon = Icons.Default.Badge, imeAction = ImeAction.Done)
                Spacer(Modifier.height(12.dp))
                AppTextField(
                    vm.currentUser?.username ?: "", {}, "Username",
                    icon = Icons.Default.AccountCircle, enabled = false
                )
                Spacer(Modifier.height(28.dp))
                PrimaryButton("Save", {
                    if (name.isNotBlank()) {
                        vm.updateName(name)
                        scope.launch { snackbar.showSnackbar("Profil berhasil disimpan") }
                    } else {
                        scope.launch { snackbar.showSnackbar("Nama tidak boleh kosong") }
                    }
                })
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onLogout,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Logout")
                }
            }
        }
    }
}
