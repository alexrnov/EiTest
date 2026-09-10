package alexrnov.eitest.presentation

import androidx.compose.ui.graphics.Color

val eiBackgroundColor = Color(163, 152, 109, 255)
val siBackgroundColor = Color(111, 136, 173, 255)
val aiBackgroundColor = Color(162, 110, 157, 255)

val headTextColor = Color(255, 255, 255, 255)
val hexagonBlockBackground = Color(0xFF5F5C5D)

fun getCategoryBackground(i: Int): Color = when (i) {
	0 -> eiBackgroundColor
	1 -> siBackgroundColor
	else -> aiBackgroundColor
}