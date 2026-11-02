package com.readrops.app.util.theme

import org.junit.Assert.assertEquals
import org.junit.Test

class AppThemeTest {

    @Test
    fun savedPreferencesSelectTheExpectedTheme() {
        mapOf(
            "light" to AppTheme.LIGHT,
            "dark" to AppTheme.DARK,
            "oled" to AppTheme.OLED,
            "system" to AppTheme.SYSTEM,
            "unknown" to AppTheme.SYSTEM
        ).forEach { (value, expected) ->
            assertEquals(expected, AppTheme.fromPreference(value))
        }
    }

    @Test
    fun explicitThemesOverrideTheSystemAndSystemThemeFollowsIt() {
        listOf(false, true).forEach { systemDark ->
            assertEquals(false, AppTheme.LIGHT.isDark(systemDark))
            assertEquals(true, AppTheme.DARK.isDark(systemDark))
            assertEquals(true, AppTheme.OLED.isDark(systemDark))
            assertEquals(systemDark, AppTheme.SYSTEM.isDark(systemDark))
        }
    }
}
