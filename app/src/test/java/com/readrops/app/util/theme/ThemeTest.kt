package com.readrops.app.util.theme

import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ThemeTest {

    @Test
    fun oledReadingAndNavigationSurfacesStayBlackAtElevation() {
        val scheme = readropsColorScheme(useDarkTheme = true, useOledTheme = true)
        listOf(
            scheme.background,
            scheme.surface,
            scheme.surfaceVariant,
            scheme.surfaceContainerLowest,
            scheme.surfaceContainerLow,
            scheme.surfaceContainer,
            scheme.surfaceContainerHighest
        ).forEach { assertEquals(Color.Black, it) }

        listOf(0, 1, 3, 6, 12).forEach { elevation ->
            assertEquals(Color.Black, scheme.surfaceColorAtElevation(elevation.dp))
        }
        assertNotEquals(scheme.background, scheme.surfaceContainerHigh)
    }

    @Test
    fun oledTextAndAccentsRemainReadable() {
        val scheme = readropsColorScheme(useDarkTheme = true, useOledTheme = true)
        listOf(
            scheme.onBackground to scheme.background,
            scheme.onSurface to scheme.surface,
            scheme.onSurface to scheme.surfaceContainerHigh,
            scheme.onSurfaceVariant to scheme.surfaceVariant,
            scheme.primary to scheme.background,
            scheme.onPrimary to scheme.primary,
            scheme.inverseOnSurface to scheme.inverseSurface
        ).forEach { (foreground, background) ->
            val lighter = maxOf(foreground.luminance(), background.luminance())
            val darker = minOf(foreground.luminance(), background.luminance())
            assertTrue("Text contrast must be at least 4.5:1", (lighter + 0.05f) / (darker + 0.05f) >= 4.5f)
        }
    }

    @Test
    fun switchingAwayFromOledRestoresTheExistingPalettes() {
        val oled = readropsColorScheme(useDarkTheme = false, useOledTheme = true)
        val light = readropsColorScheme(useDarkTheme = false, useOledTheme = false)
        val dark = readropsColorScheme(useDarkTheme = true, useOledTheme = false)

        assertEquals(Color.Black, oled.background)
        assertEquals(backgroundLight, light.background)
        assertEquals(surfaceLight, light.surface)
        assertEquals(backgroundDark, dark.background)
        assertEquals(surfaceDark, dark.surface)
        assertEquals(dark.primary, oled.primary)
        assertEquals(dark.error, oled.error)
    }
}
