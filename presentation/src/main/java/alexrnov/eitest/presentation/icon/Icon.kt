import alexrnov.eitest.presentation.Hexagon
import alexrnov.eitest.presentation.getCategoryBackground
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HexagonAppIconStructure(
	modifier: Modifier = Modifier,
	hexagonSize: Dp = 60.dp,
	selectedHexagon: String? = null,
	onHexagonClick: (String) -> Unit = {}
) {
	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center
	) {
		Layout(
			content = {
				Hexagon(
					text = "EQ",
					size = hexagonSize,
					backgroundColor = getCategoryBackground(0),
					textColor = Color.White,
					isSelected = selectedHexagon == "EQ_",
					onClick = { onHexagonClick("EQ") }
				)
				Hexagon(
					text = "SQ",
					size = hexagonSize,
					backgroundColor = getCategoryBackground(1),
					textColor = Color.White,
					isSelected = selectedHexagon == "SQ",
					onClick = { onHexagonClick("SQ") }
				)
				Hexagon(
					text = "AQ",
					size = hexagonSize,
					backgroundColor = getCategoryBackground(2),
					textColor = Color.White,
					isSelected = selectedHexagon == "AQ",
					onClick = { onHexagonClick("AQ") }
				)
			}
		) { measurables, constraints ->
			val placeables = measurables.map { it.measure(constraints) }

			val hSize = hexagonSize.toPx()
			val radius = hSize / 2f

			val xStep = radius * 1.73205f
			val yStep = radius * 1.48f

			val totalWidth = (xStep + hSize).roundToInt()

			// Возвращаем стандартный расчет высоты, а смещение будем регулировать только через yOffset
			val totalHeight = (yStep + hSize).roundToInt()

			layout(totalWidth, totalHeight) {
				// Уменьшили смещение до 0.12f, чтобы слегка приподнять гексагоны обратно вверх
				val yOffset = (radius * 0.12f).roundToInt()

				// 1. Левый верхний (EQ)
				placeables[0].placeRelative(x = 0, y = yOffset)

				// 2. Правый верхний (SQ)
				placeables[1].placeRelative(x = xStep.roundToInt(), y = yOffset)

				// 3. Нижний по центру (RQ)
				placeables[2].placeRelative(
					x = (xStep / 2f).roundToInt(),
					y = kotlin.math.floor(yStep).toInt() + yOffset
				)
			}
		}
	}
}

/*
@Preview(showBackground = true)
@Composable
fun AppIconPreview() {
	Box(
		modifier = Modifier
			.size(180.dp) // Размер самой иконки приложения
			.background(
				color = Color(0xFFFFFFFF), // Темный премиальный фон для неона
			),
		contentAlignment = Alignment.Center
	) {
		HexagonAppIconStructure(
			modifier = Modifier.padding(16.dp), // Отступы, чтобы свечение не обрезалось
			hexagonSize = 54.dp, // Немного уменьшим, чтобы композиция идеально сидела в коробке
			selectedHexagon = "EQ" // Пример: зажжем левый гексагон для красоты
		)
	}
}

 */

@Preview(showBackground = true)
@Composable
fun AppIconPreview() {
	Box(
		modifier = Modifier
			.size(180.dp) // Размер самой иконки приложения остается прежним
			.background(
				color = Color(0xFFFFFFFF),
			),
		contentAlignment = Alignment.Center
	) {
		HexagonAppIconStructure(
			// Уменьшаем отступ до 6.dp, чтобы дать больше места крупным гексагонам,
			// но оставляем минимальный зазор для неонового хвоста свечения.
			modifier = Modifier.padding(6.dp),
			// Увеличиваем размер каждого гексагона с 54.dp до 72.dp
			hexagonSize = 72.dp,
			selectedHexagon = "EQ"
		)
	}
}