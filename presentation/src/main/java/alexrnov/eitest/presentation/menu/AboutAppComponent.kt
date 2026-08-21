package alexrnov.eitest.presentation.menu

import alexrnov.eitest.presentation.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AboutAppComponent(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean
) {
	val scrollState = rememberScrollState()

	// Рассчитываем отступ между столбцами динамически
	val columnSpacing = if (isTablet) 64.dp else 32.dp

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(innerPadding),
		contentAlignment = Alignment.TopCenter
	) {
		Column(
			modifier = Modifier
				.fillMaxHeight()
				.widthIn(max = if (isTablet) 900.dp else 700.dp)
				.verticalScroll(scrollState)
				.padding(if (isTablet) 32.dp else 16.dp),
			verticalArrangement = Arrangement.spacedBy(24.dp)
		) {
			if (isLandscape || isTablet) {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(columnSpacing) // Используем переменную
				) {
					Box(modifier = Modifier.weight(1f)) {
						InfoBlock(title = stringResource(R.string.rules_title), desc = stringResource(R.string.rules_description))
					}
					Box(modifier = Modifier.weight(1f)) {
						InfoBlock(title = stringResource(R.string.information_title), desc = stringResource(R.string.information_description))
					}
				}

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(columnSpacing) // Используем переменную
				) {
					Box(modifier = Modifier.weight(1f)) {
						InfoBlock(title = stringResource(R.string.pictures_title), desc = stringResource(R.string.pictures_description))
					}
					Box(modifier = Modifier.weight(1f)) {
						InfoBlock(title = stringResource(R.string.version_title), desc = stringResource(R.string.version_description))
					}
				}
			} else {
				InfoBlock(title = stringResource(R.string.rules_title), desc = stringResource(R.string.rules_description))
				InfoBlock(title = stringResource(R.string.information_title), desc = stringResource(R.string.information_description))
				InfoBlock(title = stringResource(R.string.pictures_title), desc = stringResource(R.string.pictures_description))
				InfoBlock(title = stringResource(R.string.version_title), desc = stringResource(R.string.version_description))
			}
		}
	}
}

@Composable
fun InfoBlock(title: String, desc: String) {
	Column {
		Text(
			// 4.dp — идеальный отступ, чтобы заголовок и текст считывались как единое целое
			modifier = Modifier.padding(bottom = 6.dp),
			text = title,
			style = MaterialTheme.typography.titleMedium.copy(
				// Отключаем скрытые отступы шрифта для точности
				platformStyle = PlatformTextStyle(includeFontPadding = false)
			),
			// Добавляем цвет из темы для лучшей иерархии
			color = MaterialTheme.colorScheme.onSurface
		)
		Text(
			text = desc,
			style = MaterialTheme.typography.bodyMedium.copy(
				hyphens = Hyphens.Auto, // Автопереносы (помогут избежать больших дыр между словами)
				lineBreak = LineBreak(
					strategy = LineBreak.Strategy.HighQuality,
					strictness = LineBreak.Strictness.Strict,
					wordBreak = LineBreak.WordBreak.Default
				),
				platformStyle = PlatformTextStyle(includeFontPadding = false)
			),
			// Описание делаем чуть приглушенным (Secondary) для улучшения читаемости
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			textAlign = TextAlign.Justify
		)
	}
}