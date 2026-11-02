package com.readrops.app.util.theme

enum class AppTheme(val preferenceValue: String) {
    LIGHT("light"),
    DARK("dark"),
    OLED("oled"),
    SYSTEM("system");

    fun isDark(systemDark: Boolean): Boolean = when (this) {
        LIGHT -> false
        DARK, OLED -> true
        SYSTEM -> systemDark
    }

    companion object {
        fun fromPreference(value: String): AppTheme =
            entries.firstOrNull { it.preferenceValue == value } ?: SYSTEM
    }
}
