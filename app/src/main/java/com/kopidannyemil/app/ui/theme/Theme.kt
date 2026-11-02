package com.kopidannyemil.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Blue50 = Color(0xFFF1F9FE)
val Blue100 = Color(0xFFE1F5FE)
val Blue200 = Color(0xFFB3E5FC)
val Blue300 = Color(0xFF81D4FA)
val Blue400 = Color(0xFF4FC3F7)
val Blue500 = Color(0xFF29B6F6)
val Blue600 = Color(0xFF039BE5)
val Blue700 = Color(0xFF0288D1)
val Blue900 = Color(0xFF01579B)
val Ink = Color(0xFF0D2B3E)
val InkSoft = Color(0xFF5F7C8D)
val White = Color(0xFFFFFFFF)

private val Scheme = lightColorScheme(
    primary = Blue600,
    onPrimary = White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue900,
    secondary = Blue400,
    background = White,
    onBackground = Ink,
    surface = White,
    onSurface = Ink,
    surfaceVariant = Blue50,
    onSurfaceVariant = InkSoft,
    outline = Blue200,
    error = Color(0xFFE5484D)
)

private val AppTypography = Typography(
    headlineLarge = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Ink),
    headlineMedium = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Ink),
    titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Ink),
    bodyLarge = TextStyle(fontSize = 16.sp, color = Ink),
    bodyMedium = TextStyle(fontSize = 14.sp, color = InkSoft),
    labelLarge = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
)

@Composable
fun KopiDanNyemilTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, typography = AppTypography, content = content)
}
