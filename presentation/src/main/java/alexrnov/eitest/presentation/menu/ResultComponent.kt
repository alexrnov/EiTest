package alexrnov.eitest.presentation.menu

import alexrnov.eitest.presentation.ArchetypeContent
import alexrnov.eitest.presentation.DarkThemePreviews
import alexrnov.eitest.presentation.LightThemePreviews
import alexrnov.eitest.presentation.R
import alexrnov.eitest.presentation.theme.AppTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ResultComponent(
	isLandscape: Boolean,
	isTablet: Boolean,
	innerPadding: PaddingValues,
	content: ArchetypeContent
) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(innerPadding)
	) {
		if (isTablet) {
			val maxContentWidth = if (isLandscape) 700.dp else 560.dp
				// ПОРТРЕТНЫЙ РЕЖИМ
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 16.dp),
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Column(
						modifier = Modifier
							.weight(1f)
							.padding(bottom = 24.dp)
							.verticalScroll(rememberScrollState())
							.widthIn(max = maxContentWidth)
							.fillMaxWidth(),
						verticalArrangement = Arrangement.spacedBy(20.dp)
					) {
						ContentBlock(content)
					}

					// В портрете оставляем true, так как Column занимает fillMaxWidth
					// и нам нужно сжать плашку по бокам, чтобы она не была на весь экран
					Disclaimer(isTablet = true, useExternalPadding = true)
				}
		} else {
			val maxContentWidth = 500.dp
			if (!isLandscape) {
				// ПОРТРЕТНЫЙ РЕЖИМ
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(16.dp),
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Column(
						modifier = Modifier
							.weight(1f)
							.padding(bottom = 16.dp)
							.verticalScroll(rememberScrollState())
							.widthIn(max = maxContentWidth)
							.fillMaxWidth(),
						verticalArrangement = Arrangement.spacedBy(20.dp)
					) {
						ContentBlock(content)
					}
					// В портрете оставляем true, так как Column занимает fillMaxWidth
					// и нам нужно сжать плашку по бокам, чтобы она не была на весь экран
					Disclaimer(isTablet = false, useExternalPadding = true)
				}
			} else {
				// АЛЬБОМНЫЙ РЕЖИМ
				Row(
					modifier = Modifier
						.fillMaxSize()
						.padding(16.dp),
					horizontalArrangement = Arrangement.spacedBy(24.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					// Левая колонка (Контент)
					Column(
						modifier = Modifier
							.weight(1f)
							.padding(bottom = 4.dp)
							.verticalScroll(rememberScrollState())
							.widthIn(max = maxContentWidth),
						verticalArrangement = Arrangement.spacedBy(20.dp)
					) {
						ContentBlock(content)
					}

					// Правая колонка (Дисклеймер)
					Box(
						modifier = Modifier.weight(1f),
						contentAlignment = Alignment.Center
					) {
						// Передаем false, чтобы убрать внутреннее удвоение отступа
						Disclaimer(isTablet = false, useExternalPadding = false)
					}
				}
			}
		}
	}
}

@Composable
fun ContentBlock(content: ArchetypeContent) {
	InfoSection(stringResource(R.string.result_name), content.name)
	InfoSection(
		stringResource(R.string.result_description),
		content.description
	)
	InfoSection(stringResource(R.string.result_advice), content.advice)
}

@Composable
fun InfoSection(title: String, desc: String) {
	Column(
		modifier = Modifier.fillMaxWidth() // Гарантируем, что контейнер секции занимает всю ширину
	) {
		Text(
			modifier = Modifier.padding(bottom = 6.dp),
			text = title,
			style = MaterialTheme.typography.titleMedium.copy(
				platformStyle = PlatformTextStyle(includeFontPadding = false),
				fontWeight = FontWeight.SemiBold
			),
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
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			textAlign = TextAlign.Justify, // Включаем выравнивание по ширине (выравнивание краев)
			modifier = Modifier.fillMaxWidth() // Заставляем сам текстовый элемент растягиваться
		)
	}
}

@Composable
private fun Disclaimer(isTablet: Boolean, useExternalPadding: Boolean) {
	val verticalSpacing = if (isTablet) 10.dp else 6.dp
	val titleText = stringResource(R.string.disclaimer_title)
	val descriptionText = stringResource(R.string.disclaimer_description)

	Box(
		modifier = Modifier.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		Column(
			modifier = Modifier
				.wrapContentHeight()
				.widthIn(min = 240.dp, max = 500.dp)
				// Отступ вокруг самой плашки: применяется только в портретном режиме
				.padding(horizontal = if (useExternalPadding) 16.dp else 0.dp)
				.clip(RoundedCornerShape(4.dp))
				.background(MaterialTheme.colorScheme.secondaryContainer)
				// Внутренние отступы, чтобы текст не прилипал к границам цветной подложки
				.padding(horizontal = 16.dp, vertical = verticalSpacing),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				modifier = Modifier.padding(top = 4.dp, bottom = if (isTablet) 16.dp else 8.dp),
				text = titleText,
				style = MaterialTheme.typography.titleMedium,
				textAlign = TextAlign.Center
			)
			Text(
				modifier = Modifier.padding(bottom = 4.dp),
				text = descriptionText,
				style = MaterialTheme.typography.bodySmall,
				textAlign = TextAlign.Center,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}

@LightThemePreviews
@DarkThemePreviews
@Composable
fun ResultComponentPreview() {
	val configuration = LocalConfiguration.current
	val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
	val isTablet = configuration.smallestScreenWidthDp >= 600

	AppTheme(isTablet = isTablet) {
		Scaffold { innerPadding ->
			ResultComponent(
				isLandscape = isLandscape,
				isTablet = isTablet,
				innerPadding = innerPadding,
				content = ArchetypeContent(
					stringResource(R.string.integral_name),
					stringResource(R.string.integral_desc),
					stringResource(R.string.deficit_advice)
				)
			)
		}
	}
}