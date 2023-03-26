package bav.astrobirthday.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bav.astrobirthday.R

val SfProDisplay = FontFamily(
    Font(R.font.sf_pro_display_medium, weight = FontWeight.W500)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = SfProDisplay,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = SfProDisplay,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),
    button = TextStyle(
        fontFamily = SfProDisplay,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
)