package com.tashariko.exploredb.theming

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import com.tashariko.exploredb.R


val ConvergenceFont = FontFamily(
    Font(R.font.convergence)
)

val AppTypography = Typography(
    h1 = TextStyle(
        fontFamily = ConvergenceFont,
        fontWeight = FontWeight.W300,
        fontSize = 96.sp
    ),
    body1 = TextStyle(
        fontFamily = ConvergenceFont,
        fontWeight = FontWeight.W600,
        fontSize = textNormalSize
    )
)