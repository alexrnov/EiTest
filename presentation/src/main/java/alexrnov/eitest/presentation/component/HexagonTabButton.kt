package alexrnov.eitest.presentation.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomHexagon(
	modifier: Modifier = Modifier,
	size: Dp = 60.dp,
	backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
	isSelected: Boolean = false,
	onClick: () -> Unit,
	content: @Composable BoxScope.() -> Unit
) {
	BaseGlowContainer(
		width = size,
		height = size,
		shape = HexagonShape, // Твой GenericShape правильного шестиугольника
		modifier = modifier,
		backgroundColor = backgroundColor,
		isSelected = isSelected,
		onClick = onClick,
		content = content
	)
}