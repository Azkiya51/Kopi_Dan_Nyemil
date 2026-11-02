package com.kopidannyemil.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kopidannyemil.app.AppViewModel
import com.kopidannyemil.app.ui.components.*
import com.kopidannyemil.app.ui.theme.*

@Composable
fun SignUpScreen(onRegister: () -> Unit, onLogin: () -> Unit) {
    GradientBackground {
        Column(Modifier.fillMaxSize().padding(28.dp).statusBarsPadding().navigationBarsPadding()) {
            Spacer(Modifier.height(48.dp))
            Text("Sign up", style = MaterialTheme.typography.headlineLarge, color = Blue900)
            Spacer(Modifier.height(12.dp))
            Text(
                "Bergabunglah sebagai member dan rasakan lebih banyak keseruan dari berbagai hidangan di Kopi dan Nyemil.",
                style = MaterialTheme.typography.bodyLarge,
                color = InkSoft
            )
            Spacer(Modifier.weight(1f))
            PrimaryButton("Register", onRegister)
            Spacer(Modifier.height(12.dp))
            SecondaryButton("Login", onLogin)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun LoginScreen(vm: AppViewModel, onBack: () -> Unit, onSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    GradientBackground {
        Column(
            Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().imePadding()
                .verticalScroll(rememberScrollState()).padding(28.dp)
        ) {
            BackButton(onBack)
            Spacer(Modifier.height(24.dp))
            Text("Login User", style = MaterialTheme.typography.headlineLarge, color = Blue900)
            Spacer(Modifier.height(6.dp))
            Text("Masuk untuk mulai memesan menu favoritmu.", style = MaterialTheme.typography.bodyLarge, color = InkSoft)
            Spacer(Modifier.height(32.dp))
            AppTextField(username, { username = it; error = null }, "Username", icon = Icons.Default.AccountCircle)
            Spacer(Modifier.height(14.dp))
            AppTextField(
                password, { password = it; error = null }, "Password",
                icon = Icons.Default.Lock, isPassword = true, imeAction = ImeAction.Done
            )
            Spacer(Modifier.height(10.dp))
            ErrorText(error)
            Spacer(Modifier.height(6.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Blue100),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp)
            ) {
                Text(
                    "Akun demo  •  username: demo  •  password: demo123",
                    style = MaterialTheme.typography.bodyMedium, color = Blue900,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(Modifier.height(32.dp))
            PrimaryButton("Login", {
                error = vm.login(username, password)
                if (error == null) onSuccess()
            })
        }
    }
}

@Composable
fun RegisterScreen(vm: AppViewModel, onBack: () -> Unit, onSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    GradientBackground {
        Column(
            Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().imePadding()
                .verticalScroll(rememberScrollState()).padding(28.dp)
        ) {
            BackButton(onBack)
            Spacer(Modifier.height(24.dp))
            Text("Register", style = MaterialTheme.typography.headlineLarge, color = Blue900)
            Spacer(Modifier.height(6.dp))
            Text("Buat akun barumu dalam hitungan detik.", style = MaterialTheme.typography.bodyLarge, color = InkSoft)
            Spacer(Modifier.height(32.dp))
            AppTextField(name, { name = it; error = null }, "Nama lengkap", icon = Icons.Default.Badge)
            Spacer(Modifier.height(14.dp))
            AppTextField(username, { username = it; error = null }, "Username", icon = Icons.Default.AlternateEmail)
            Spacer(Modifier.height(14.dp))
            AppTextField(
                password, { password = it; error = null }, "Password (min. 6 karakter)",
                icon = Icons.Default.Lock, isPassword = true, imeAction = ImeAction.Done
            )
            Spacer(Modifier.height(10.dp))
            ErrorText(error)
            Spacer(Modifier.height(32.dp))
            PrimaryButton("Register", {
                error = vm.register(name, username, password)
                if (error == null) onSuccess()
            })
        }
    }
}
