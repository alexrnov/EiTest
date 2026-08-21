package alexrnov.eitest.presentation

import alexrnov.eitest.presentation.theme.AppTheme
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultComponent(
	isLandscape: Boolean,
	isTablet: Boolean,
	content: ArchetypeContent,
	results: List<Int>
) {
	val scrollState = remember(isLandscape) { ScrollState(initial = 0) }

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {
		if (isTablet) {
			val maxContentWidth = if (isLandscape) 700.dp else 560.dp

			if (!isLandscape) {
				// ПЛАНШЕТ: ПОРТРЕТНЫЙ РЕЖИМ
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
							.verticalScroll(scrollState)
							.widthIn(max = maxContentWidth)
							.fillMaxWidth(),
						verticalArrangement = Arrangement.spacedBy(34.dp)
					) {
						Column(modifier = Modifier.fillMaxWidth()) {
							MetricsBlock(results = results, isTablet = true, isLandscape = false)

							HorizontalDivider(
								modifier = Modifier.padding(top = 42.dp, bottom = 8.dp),
								thickness = 2.dp,
								color = MaterialTheme.colorScheme.outlineVariant)
						}
						ContentBlock(content)
					}

					Disclaimer(isTablet = true, useExternalPadding = true)
				}
			} else {
				// ПЛАНШЕТ: АЛЬБОМНЫЙ РЕЖИМ
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(start = 32.dp, end = 32.dp, top = 40.dp, bottom = 24.dp), // Отступ от самого низа экрана
					verticalArrangement = Arrangement.spacedBy(24.dp) // Безопасный зазор между контентом и дисклеймером
				) {
					// 1. Верхняя часть экрана: Две колонки одинаковой ширины (строго ДО дисклеймера)
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.weight(1f), // Динамически занимает всё доступное место сверху
						horizontalArrangement = Arrangement.spacedBy(40.dp),
						verticalAlignment = Alignment.Top
					) {
						// Левая колонка: Тексты
						Column(
							modifier = Modifier
								.weight(1f)
								.fillMaxHeight()
								.verticalScroll(scrollState),
							verticalArrangement = Arrangement.spacedBy(28.dp)
						) {
							ContentBlock(content)
						}

						// Правая колонка: Прогрессбары (теперь снова прижаты к верху)
						Column(
							modifier = Modifier
								.weight(1f)
								.fillMaxHeight(),
							verticalArrangement = Arrangement.Top, // Возвращаем наверх
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							MetricsBlock(results = results, isTablet = true, isLandscape = true)
						}
					}

					// 2. Нижняя часть экрана: Дисклеймер (Всегда по центру снизу)
					// Контент из Row выше никогда физически не сможет на него налезть
					Box(
						modifier = Modifier.fillMaxWidth(),
						contentAlignment = Alignment.BottomCenter
					) {
						Disclaimer(isTablet = true, useExternalPadding = false)
					}
				}
			}
		} else {
			val maxContentWidth = 500.dp
			if (!isLandscape) {
				// ПОРТРЕТНЫЙ РЕЖИМ
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 6.dp), // Отступы экрана 12.dp
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Column(
						modifier = Modifier
							.weight(1f)
							.padding(bottom = 16.dp)
							.verticalScroll(scrollState)
							.widthIn(max = maxContentWidth)
							.fillMaxWidth(),
						verticalArrangement = Arrangement.spacedBy(16.dp)
					) {
						// Объединяем прогрессбары и линию в один блок
						Column(
							modifier = Modifier.fillMaxWidth()
							// Здесь spacedBy НЕТ, отступами управляем вручную
						) {
							MetricsBlock(results = results)

							// Задаем точный, контролируемый отступ сверху для линии
							HorizontalDivider(
								modifier = Modifier.padding(top = 16.dp), // вместо системных 20.dp
								thickness = 2.dp,
								color = MaterialTheme.colorScheme.outlineVariant
							)
						}
						ContentBlock(content)
					}

					// Передаем false, чтобы убрать внутренний padding(16.dp) из самого Disclaimer
					Disclaimer(isTablet = false, useExternalPadding = false)
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
							.padding(bottom = 0.dp)
							.verticalScroll(scrollState)
							.widthIn(max = maxContentWidth),
						verticalArrangement = Arrangement.spacedBy(10.dp)
					) {
						// Объединяем прогрессбары и линию в один блок
						Column(
							modifier = Modifier.fillMaxWidth()
							// Здесь spacedBy НЕТ, отступами управляем вручную
						) {
							MetricsBlock(results = results)

							// Задаем точный, контролируемый отступ сверху для линии
							HorizontalDivider(
								modifier = Modifier.padding(top = 16.dp), // вместо системных 20.dp
								thickness = 2.dp,
								color = MaterialTheme.colorScheme.outlineVariant
							)
						}
						ContentBlock(content)
					}

					// Правая колонка (Дисклеймер)
					Box(
						modifier = Modifier.weight(1f),
						contentAlignment = Alignment.Center
					) {
						// Передаем false, чтобы убрать внутреннее удвоение отступа
						Disclaimer(isTablet = false, useExternalPadding = false, modifier = Modifier.widthIn(max = 320.dp))
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
private fun Disclaimer(
	isTablet: Boolean,
	useExternalPadding: Boolean,
	modifier: Modifier = Modifier // 1. Добавляем параметр по умолчанию
) {
	val verticalSpacing = if (isTablet) 10.dp else 4.dp
	val titleText = stringResource(R.string.disclaimer_title)
	val descriptionText = stringResource(R.string.disclaimer_description)

	Box(
		modifier = modifier.fillMaxWidth(),
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
				//.padding(horizontal = 16.dp, vertical = verticalSpacing),
				.padding(
					horizontal = if (isTablet) 16.dp else 10.dp, // Уменьшили с 16 до 10 для смартфона
					vertical = verticalSpacing
				),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				modifier = Modifier.padding(top = 0.dp, bottom = if (isTablet) 16.dp else 4.dp),
				text = titleText,
				style = MaterialTheme.typography.titleMedium,
				textAlign = TextAlign.Center
			)
			Text(
				modifier = Modifier.padding(bottom = 0.dp),
				text = descriptionText,
				style = if (isTablet) MaterialTheme.typography.labelLarge else MaterialTheme.typography.bodySmall.copy(
					lineHeight = 14.sp
				),
				textAlign = TextAlign.Center,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@LightThemePreviews
@DarkThemePreviews
@Composable
fun ResultComponentPreview() {
	val configuration = LocalConfiguration.current
	val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
	val isTablet = configuration.smallestScreenWidthDp >= 600

	AppTheme(isTablet = isTablet) {
		Scaffold {
			ResultComponent(
				isLandscape = isLandscape,
				isTablet = isTablet,
				content = ArchetypeContent(
					stringResource(R.string.integral_name),
					stringResource(R.string.integral_desc),
					stringResource(R.string.deficit_advice)
				),
				results = listOf(50, 50, 50)
			)
		}
	}
}

@Composable
fun MetricsBlock(
	results: List<Int>,
	modifier: Modifier = Modifier,
	isTablet: Boolean = false,
	isLandscape: Boolean = false
) {
	// Защита на случай, если в списке пришло меньше 3 элементов
	val eqValue = results.getOrNull(0) ?: 0
	val sqValue = results.getOrNull(1) ?: 0
	val aqValue = results.getOrNull(2) ?: 0

	Column(
		modifier = modifier
			.fillMaxWidth(),
			//.padding(top = 8.dp),
		verticalArrangement = if (isTablet) {
			if (isLandscape) {
				Arrangement.spacedBy(50.dp)
			} else {
				Arrangement.spacedBy(28.dp)
			}
		} else Arrangement.spacedBy(12.dp) // Расстояние между строками
	) {
		ProgressBarRow(label = stringResource(R.string.eq), value = eqValue, color = eiBackgroundColor)
		ProgressBarRow(label = stringResource(R.string.sq), value = sqValue, color = siBackgroundColor)
		ProgressBarRow(label = stringResource(R.string.aq), value = aqValue, color = aiBackgroundColor)
	}
}