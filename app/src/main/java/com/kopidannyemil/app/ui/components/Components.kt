package com.kopidannyemil.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kopidannyemil.app.ui.theme.*

@Composable
fun GradientBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Blue200, Blue100, White, White)))
    ) {
        Box(
            Modifier.offset(x = 220.dp, y = (-60).dp).size(220.dp)
                .background(Blue300.copy(alpha = 0.35f), CircleShape)
        )
        Box(
            Modifier.offset(x = (-70).dp, y = 120.dp).size(150.dp)
                .background(White.copy(alpha = 0.55f), CircleShape)
        )
        content()
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    minLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next
) {
    var visible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = if (icon != null) { { Icon(icon, contentDescription = null) } } else null,
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Tampilkan password"
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        singleLine = minLines == 1,
        minLines = minLines,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Blue600,
            unfocusedBorderColor = Blue200,
            focusedContainerColor = White,
            unfocusedContainerColor = White,
            disabledContainerColor = Blue50,
            focusedLabelColor = Blue600,
            focusedLeadingIconColor = Blue600
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Blue600, disabledContainerColor = Blue200),
        contentPadding = PaddingValues(vertical = 16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) { Text(text, style = MaterialTheme.typography.labelLarge, color = White) }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Blue600),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = White, contentColor = Blue600),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) { Text(text, style = MaterialTheme.typography.labelLarge, color = Blue600) }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.background(White, CircleShape)
    ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali", tint = Blue700) }
}

@Composable
fun ErrorText(message: String?) {
    if (message != null) {
        Text(message, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
    }
}

/** Bottom navigation: Home / Order / Profile */
@Composable
fun AppBottomBar(current: String, cartCount: Int, nav: NavController) {
    NavigationBar(containerColor = White, tonalElevation = 8.dp) {
        val items = listOf(
            Triple("home", "Home", Icons.Default.Home),
            Triple("address", "Order", Icons.Default.ShoppingBag),
            Triple("profile", "Profile", Icons.Default.Person)
        )
        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                selected = current == route,
                onClick = {
                    if (current != route) nav.navigate(route) {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (route == "address" && cartCount > 0) {
                        BadgedBox(badge = { Badge(containerColor = Blue600) { Text("$cartCount") } }) {
                            Icon(icon, label)
                        }
                    } else Icon(icon, label)
                },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue900,
                    selectedTextColor = Blue900,
                    indicatorColor = Blue200,
                    unselectedIconColor = InkSoft,
                    unselectedTextColor = InkSoft
                )
            )
        }
    }
}
