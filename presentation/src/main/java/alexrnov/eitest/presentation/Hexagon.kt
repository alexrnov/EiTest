package alexrnov.eitest.presentation

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Hexagon(
	text: String,
	modifier: Modifier = Modifier,
	size: Dp = 60.dp,
	backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
	textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	isSelected: Boolean = false,
	onClick: () -> Unit
) {
	val glowColor = backgroundColor

	Box(
		modifier = modifier
			.size(size)
			// слои усиленного размытия
			.then(
				if (isSelected) {
					Modifier.drawBehind {
						// Позволяем холсту не обрезать рисунок за пределами 60.dp
						// (Работает на Android, если у родителя выключен clipChildren)

						val radius = this.size.width / 2f
						val centerX = this.size.width / 2f
						val centerY = this.size.height / 2f

						drawIntoCanvas { canvas ->
							// Коэффициент увеличения пути для дальнего свечения (на 15% больше)
							val glowScale = 1.15f

							// Путь для дальнего (широкого) свечения
							val widePath = Path().apply {
								for (i in 0 until 6) {
									val angle = Math.PI * 2 * i / 6 - Math.PI / 2
									// Умножаем радиус на glowScale, чтобы свечение выходило ДАЛЬШЕ
									val x = centerX + (radius * glowScale) * cos(angle).toFloat()
									val y = centerY + (radius * glowScale) * sin(angle).toFloat()
									if (i == 0) moveTo(x, y) else lineTo(x, y)
								}
								close()
							}.asAndroidPath()

							// Стандартный путь для ближнего свечения
							val densePath = Path().apply {
								for (i in 0 until 6) {
									val angle = Math.PI * 2 * i / 6 - Math.PI / 2
									val x = centerX + radius * cos(angle).toFloat()
									val y = centerY + radius * sin(angle).toFloat()
									if (i == 0) moveTo(x, y) else lineTo(x, y)
								}
								close()
							}.asAndroidPath()

							// БЛИЖНИЙ СЛОЙ: Сделали плотнее (был 5.dp -> стал 8.dp)
							val paintDense = Paint().asFrameworkPaint().apply {
								this.color = glowColor.toArgb()
								this.style = android.graphics.Paint.Style.FILL
								this.maskFilter = BlurMaskFilter(8.dp.toPx(), BlurMaskFilter.Blur.NORMAL)
							}
							canvas.nativeCanvas.drawPath(densePath, paintDense)

							// ДАЛЬНИЙ СЛОЙ: Сделали намного шире (был 15.dp -> стал 28.dp)
							val paintWide = Paint().asFrameworkPaint().apply {
								this.color = glowColor.toArgb()
								this.style = android.graphics.Paint.Style.FILL
								this.maskFilter = BlurMaskFilter(28.dp.toPx(), BlurMaskFilter.Blur.NORMAL)
							}
							canvas.nativeCanvas.drawPath(widePath, paintWide)
						}
					}
				} else Modifier
			)
			// обрезание и фон кнопки
			.clip(HexagonShape)
			.clickable { onClick() }
			.background(backgroundColor)
			// обводка (для усиления эффекта 3.dp белого "ядра")
			.then(
				if (isSelected) {
					Modifier.border(3.dp, Color.White, HexagonShape)
				} else {
					Modifier.border(1.dp, backgroundColor, HexagonShape)
				}
			),
		contentAlignment = Alignment.Center
	) {
		Text(
			text = text,
			color = textColor,
			fontSize = (size.value * 0.35f).sp,
			fontWeight = FontWeight.Bold,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis
		)
	}
}

// Форма правильного шестиугольника остается прежней
val HexagonShape = GenericShape { size, _ ->
	val radius = size.width / 2f
	val centerX = size.width / 2f
	val centerY = size.height / 2f

	for (i in 0 until 6) {
		val angle = Math.PI * 2 * i / 6 - Math.PI / 2
		val x = centerX + radius * cos(angle).toFloat()
		val y = centerY + radius * sin(angle).toFloat()
		if (i == 0) moveTo(x, y) else lineTo(x, y)
	}
	close()
}