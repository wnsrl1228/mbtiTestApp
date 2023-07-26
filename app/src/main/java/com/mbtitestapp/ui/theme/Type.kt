package com.mbtitestapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.unit.sp
import com.mbtitestapp.R

/**
 * default font 적용 방법 : https://stackoverflow.com/questions/73022970/how-do-i-apply-a-default-font-to-all-text-elements-in-the-app-using-jetpack-co
 */

object AppFont {
    val gmarketSans = FontFamily(
        Font(R.font.gmarket_sans_light, FontWeight.Light),
        Font(R.font.gmarket_sans_medium, FontWeight.Normal),
        Font(R.font.gmarket_sans_bold, FontWeight.Bold),
    )
}

private val defaultTypography = Typography()
val myTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.gmarketSans),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.gmarketSans),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.gmarketSans),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.gmarketSans),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.gmarketSans),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.gmarketSans),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.gmarketSans),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.gmarketSans),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.gmarketSans),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.gmarketSans),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.gmarketSans),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.gmarketSans),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.gmarketSans),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.gmarketSans),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.gmarketSans)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)