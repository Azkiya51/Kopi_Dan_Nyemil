package com.kopidannyemil.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kopidannyemil.app.ui.screens.*
import com.kopidannyemil.app.ui.theme.KopiDanNyemilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KopiDanNyemilTheme { AppNavigation() }
        }
    }
}

@Composable
fun AppNavigation(vm: AppViewModel = viewModel()) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = "splash",
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { fadeOut(tween(200)) }
    ) {
        composable("splash") {
            SplashScreen(onStart = { nav.navigate("signup") })
        }
        composable("signup") {
            SignUpScreen(
                onRegister = { nav.navigate("register") },
                onLogin = { nav.navigate("login") }
            )
        }
        composable("login") {
            LoginScreen(vm, onBack = { nav.popBackStack() }, onSuccess = {
                nav.navigate("home") { popUpTo("splash") { inclusive = true } }
            })
        }
        composable("register") {
            RegisterScreen(vm, onBack = { nav.popBackStack() }, onSuccess = {
                nav.navigate("home") { popUpTo("splash") { inclusive = true } }
            })
        }
        composable("home") { HomeScreen(vm, nav) }
        composable("profile") {
            ProfileScreen(vm, nav, onLogout = {
                vm.logout()
                nav.navigate("signup") { popUpTo(0) { inclusive = true } }
            })
        }
        composable("address") {
            AddressScreen(vm, nav, onOrder = { nav.navigate("confirmation") })
        }
        composable("confirmation") {
            ConfirmationScreen(vm, onOk = {
                nav.navigate("home") { popUpTo("home") { inclusive = true } }
            })
        }
    }
}
