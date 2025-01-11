package com.amk.negareh.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.amk.negareh.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = FontFamily(Font(R.font.dana_medium)),
        fontWeight = FontWeight(400),
        color = Color(0xFF424242),
        textAlign = TextAlign.Center,
    ),

    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.dana_medium)),
        fontWeight = FontWeight(400),
        color = Color(0xFF424242),
        textAlign = TextAlign.Center,

    ),

    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.dana_medium)),
        fontWeight = FontWeight(400),
        color = Color(0xFF424242),
        textAlign = TextAlign.Center,
    )

)

val textFieldStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily(Font(R.font.dana_medium)),
    fontWeight = FontWeight(400),
    color = Color(0xFF424242),
    textAlign = TextAlign.Right,
)

val bodyLargeCard = TextStyle(
    fontSize = 22.sp,
    fontFamily = FontFamily(Font(R.font.dana_medium)),
    fontWeight = FontWeight(600),
    color = Color(0xFFFFFFFF),
    textAlign = TextAlign.Center,
)

val bodyMediumCard = TextStyle(
    fontSize = 18.sp,
    fontFamily = FontFamily(Font(R.font.dana_medium)),
    fontWeight = FontWeight(500),
    color = Color(0xFFFFCCB9),
    textAlign = TextAlign.Center,
)

val bodySmallCard = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily(Font(R.font.dana_medium)),
    fontWeight = FontWeight(500),
    color = Color(0xFFFFFFFF),
    textAlign = TextAlign.Center,
)