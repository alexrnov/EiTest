package alexrnov.eitest.presentation.component

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BaseGlowContainer(
	width: Dp,
	height: Dp,
	shape: Shape,
	modifier: Modifier = Modifier,
	backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
	isSelected: Boolean = false,
	onClick: () -> Unit,
	content: @Composable BoxScope.() -> Unit
) {
	val glowColor = backgroundColor

	Box(
		modifier = modifier
			.width(width)
			.height(height)
			.graphicsLayer() // Включаем GPU-ускорение для BlurMaskFilter
			.then(
				if (isSelected) {
					Modifier.drawBehind {
						val centerX = this.size.width / 2f
						val centerY = this.size.height / 2f

						// Для прямоугольника/капсулы размытие лучше делать через нативный
						// контур самой формы (Outline), чтобы оно повторяло её геометрию:
						drawIntoCanvas { canvas ->
							val paintDense = Paint().asFrameworkPaint().apply {
								this.color = glowColor.toArgb()
								this.style = android.graphics.Paint.Style.FILL
								this.maskFilter = BlurMaskFilter(8.dp.toPx(), BlurMaskFilter.Blur.NORMAL)
							}
							val paintWide = Paint().asFrameworkPaint().apply {
								this.color = glowColor.toArgb()
								this.style = android.graphics.Paint.Style.FILL
								this.maskFilter = BlurMaskFilter(28.dp.toPx(), BlurMaskFilter.Blur.NORMAL)
							}

							// Получаем путь (Path) на основе переданной формы
							val androidPath = shape.createOutline(this.size, layoutDirection, this)
								.let { outline ->
									// Превращаем Outline в Android Path для отрисовки свечения
									val path = Path()
									when (outline) {
										is Outline.Rectangle -> path.addRect(outline.rect)
										is Outline.Rounded -> path.addRoundRect(outline.roundRect)
										is Outline.Generic -> path.addPath(outline.path)
									}
									path.asAndroidPath()
								}

							canvas.nativeCanvas.drawPath(androidPath, paintDense)
							canvas.nativeCanvas.drawPath(androidPath, paintWide)
						}
					}
				} else Modifier
			)
			.clip(shape)
			.clickable { onClick() }
			.background(backgroundColor)
			.then(
				if (isSelected) {
					Modifier.border(3.dp, Color.White, shape)
				} else {
					Modifier.border(1.dp, backgroundColor, shape)
				}
			),
		contentAlignment = Alignment.Center,
		content = content
	)
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