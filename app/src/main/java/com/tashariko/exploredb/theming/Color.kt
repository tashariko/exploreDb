package com.tashariko.exploredb.theming

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color


val colorPrimaryLight = Color(0xFFBB86FC)
val colorPrimary = Color(0xFF6200EE)
val colorPrimaryDark = Color(0xFF3700B3)
val colorSecondary = Color(0xFF03DAC5)
val colorSecondaryDark = Color(0xFF018786)
val screenBgColor = Color(0xFFFFFFFF)

val buttonDisabled = Color(0xffe3cefd)
val offlineColor = Color(0xffab2323)
val transparent = Color(0x00ffffff)

val appTextColor = Color(0xff333333)
val textLightColor = Color(0xff999999)
val appExtreme = Color(0xfffffff)



data class AppColors(
    val material: Colors,
    val primaryLight: Color,
    val offlineColor: Color,
    val transparent: Color,
    val appTextColor: Color,
    val appTextLightColor: Color,
    val appExtreme: Color,
    val buttonDisabled: Color
) {
    //val primary: Color get() = material.primary
}