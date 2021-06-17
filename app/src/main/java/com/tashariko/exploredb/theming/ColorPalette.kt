package com.tashariko.exploredb.theming

import android.media.MediaCodec
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors


/**
 * https://gustav-karlsson.medium.com/extending-the-jetpack-compose-material-theme-with-more-colors-e1b849390d50
 */
internal val LightColorPalette = AppColors(
    material = lightColors(
        primary = colorPrimary,
        primaryVariant = colorPrimaryDark,
        onPrimary = screenBgColor,
        surface = screenBgColor,
        secondary = colorSecondary,
        secondaryVariant = colorSecondaryDark,
        onSecondary = screenBgColor,
        background = screenBgColor
    ),
    primaryLight = colorPrimaryLight,
    offlineColor = offlineColor,
    transparent = transparent,
    appTextColor = appTextColor,
    appTextLightColor = textLightColor,
    appExtreme = appExtreme,
    buttonDisabled = buttonDisabled
)

internal val DarkColorPalette = AppColors(
    material = darkColors(
        primary = colorPrimary,
        primaryVariant = colorPrimaryDark,
        onPrimary = screenBgColorDark,
        surface = screenBgColorDark,
        secondary = colorSecondaryDark,
        secondaryVariant = colorSecondary,
        onSecondary = screenBgColorDark,
        background = screenBgColorDark
    ),
    primaryLight = colorPrimaryLight,
    offlineColor = offlineColor,
    transparent = transparent,
    appTextColor = appTextColor,
    appTextLightColor = textLightColor,
    appExtreme = appExtreme,
    buttonDisabled = buttonDisabled
)