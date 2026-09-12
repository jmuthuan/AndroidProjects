package com.example.simplecalculator.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Single, intentional, fixed vintage color identity. It is never derived from
// the system dark/light setting or from Android 12+ dynamic color (Material You) —
// this app has one permanent vintage-instrument-panel palette regardless of device
// wallpaper or OS version. See story: fix-vintage-theme-dynamic-color-override.
// Built with darkColorScheme(...) as the base builder because the tones actually
// rendered through this scheme (status bar tint via `primary`, and the occluded
// `background` role) are a dark, near-black family.
private val VintageColorScheme = darkColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    background = onBackground
)

@Composable
fun SimpleCalculatorTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = VintageColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            // Fixed rather than system-theme-driven: VintageColorScheme.primary is a
            // dark, near-black tone, so status bar icons must render light/white
            // against it. Revisit this literal if `primary` in Color.kt is ever
            // changed to a light tone.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}