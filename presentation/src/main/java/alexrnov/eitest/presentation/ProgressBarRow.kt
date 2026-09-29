package alexrnov.eitest.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun ProgressBarRow(
	label: String,
	value: Int,
	modifier: Modifier = Modifier,
	color: Color
) {
	val progress = (value.coerceIn(0, 100) / 100f)

	Row(
		modifier = modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = label,
			style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier.width(40.dp)
		)

		LinearProgressIndicator(
			progress = { progress },
			modifier = Modifier
				.weight(1f)
				.height(8.dp)
				.clip(RoundedCornerShape(4.dp)),
			color = color,
			trackColor = MaterialTheme.colorScheme.surfaceVariant,

			// 🛠 УБИРАЕМ ТОЧКУ И ЗАЗОР ИЗ MATERIAL 3:
			gapSize = 0.dp,               // Убирает пустой зазор между активным прогрессом и фоном
			drawStopIndicator = {}        // Передаем пустую лямбду, чтобы точка в конце не рисовалась
		)

		Text(
			text = "$value%",
			style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			modifier = Modifier.width(50.dp),
			textAlign = TextAlign.End
		)
	}
}