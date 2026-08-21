package alexrnov.eitest.presentation.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExtendedRectangle(
	modifier: Modifier = Modifier,
	width: Dp = 100.dp,  // Вытянутая ширина по умолчанию
	height: Dp = 60.dp,  // Высота совпадает с гексагоном для симметрии
	cornerRadius: Dp = 14.dp, // Радиус скругления углов прямоугольника
	backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
	isSelected: Boolean = false,
	onClick: () -> Unit,
	content: @Composable BoxScope.() -> Unit
) {
	BaseGlowContainer(
		width = width,
		height = height,
		shape = RoundedCornerShape(cornerRadius), // Форма скругленного прямоугольника
		modifier = modifier,
		backgroundColor = backgroundColor,
		isSelected = isSelected,
		onClick = onClick,
		content = content
	)
}