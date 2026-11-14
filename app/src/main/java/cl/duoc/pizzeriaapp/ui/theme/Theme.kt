package cl.duoc.pizzeriaapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA43F27),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE9DF),
    onPrimaryContainer = Color(0xFF3B160E),
    secondary = Color(0xFF42634B),
    secondaryContainer = Color(0xFFE4EDDF),
    onSecondaryContainer = Color(0xFF243C2B),
    background = Color(0xFFFAF6F0),
    onBackground = Color(0xFF30251F),
    surface = Color(0xFFFFFCF8),
    onSurface = Color(0xFF30251F),
    onSurfaceVariant = Color(0xFF73645B),
    outlineVariant = Color(0xFFE5D8CD)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB59B),
    onPrimary = Color(0xFF59200F),
    primaryContainer = Color(0xFF533025),
    onPrimaryContainer = Color(0xFFFFE9DF),
    secondary = Color(0xFFB3CFB7),
    secondaryContainer = Color(0xFF2E4433),
    onSecondaryContainer = Color(0xFFE4EDDF),
    background = Color(0xFF1C1815),
    onBackground = Color(0xFFF3E9DF),
    surface = Color(0xFF27211C),
    onSurface = Color(0xFFF3E9DF),
    onSurfaceVariant = Color(0xFFCABAAF),
    outlineVariant = Color(0xFF514238)
)

@Composable
fun PizzeriaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
