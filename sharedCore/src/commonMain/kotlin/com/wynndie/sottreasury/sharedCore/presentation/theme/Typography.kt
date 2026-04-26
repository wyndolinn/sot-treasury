package com.wynndie.sottreasury.sharedCore.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wynndie.sottreasury.sharedResources.Res
import com.wynndie.sottreasury.sharedResources.oswald_bold
import com.wynndie.sottreasury.sharedResources.oswald_medium
import com.wynndie.sottreasury.sharedResources.oswald_regular
import com.wynndie.sottreasury.sharedResources.oswald_semi_bold
import org.jetbrains.compose.resources.Font

val oswaldFontFamily: FontFamily
    @Composable get() = FontFamily(
        Font(Res.font.oswald_regular, FontWeight.Normal),
        Font(Res.font.oswald_medium, FontWeight.Medium),
        Font(Res.font.oswald_semi_bold, FontWeight.SemiBold),
        Font(Res.font.oswald_bold, FontWeight.Bold),
    )

val Typography: Typography
    @Composable get() = Typography(
        titleLarge = TextStyle(
            fontFamily = oswaldFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.9.sp
        ),
        titleMedium = TextStyle(
            fontFamily = oswaldFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.8.sp
        ),
        titleSmall = TextStyle(
            fontFamily = oswaldFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.7.sp
        ),


        bodyMedium = TextStyle(
            fontFamily = oswaldFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.7.sp
        ),


        labelMedium = TextStyle(
            fontFamily = oswaldFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = oswaldFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        )
    )