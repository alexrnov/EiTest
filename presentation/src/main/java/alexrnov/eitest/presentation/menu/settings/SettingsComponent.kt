package alexrnov.eitest.presentation.menu.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import alexrnov.eitest.presentation.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsComponent(
	isLandscape: Boolean,
	innerPadding: PaddingValues,
	isTablet: Boolean,
	clearLocalBuffer: () -> Unit
) {

	val settingsViewModel: SettingsViewModel = koinViewModel()

	val index = settingsViewModel.radioButtonsState.collectAsStateWithLifecycle().value
	val isDialogVisible = settingsViewModel.isDialogVisible

	val content = @Composable {
		ThemeRadioGroupContainer(
			selectedIndex = index,
			onThemeSelected = settingsViewModel::setTheme,
			isTablet = isTablet
		)
		ResetDataContainer(
			isDialogVisible = isDialogVisible,
			showDialog = settingsViewModel::showDialog,
			hideDialog = settingsViewModel::hideDialog,
			//clearAllData = settingsViewModel::clearAllData,
			clearAllData = {},
			isTablet = isTablet,
			clearLocalBuffer = clearLocalBuffer
		)
	}

	val layoutModifier = Modifier
		.fillMaxSize()
		.padding(innerPadding)
		.padding(16.dp)

	if (isLandscape) {
		Row(
			modifier = layoutModifier,
			horizontalArrangement = Arrangement.SpaceEvenly,
			verticalAlignment = Alignment.CenterVertically
		) {
			content()
		}
	} else {
		Column(
			modifier = layoutModifier,
			verticalArrangement = Arrangement.SpaceEvenly,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			content()
		}
	}
}

@Composable
fun ThemeRadioGroupContainer(
	selectedIndex: Int,
	onThemeSelected: (Int) -> Unit,
	isTablet: Boolean
) {
	Column(
		modifier = Modifier
			.wrapContentSize()
			.clip(RoundedCornerShape(16.dp))
			.background(MaterialTheme.colorScheme.surfaceContainer),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			modifier = Modifier
				.widthIn(max = if (isTablet) 450.dp else 260.dp)
				.padding(start = 16.dp, top = 16.dp, end = 16.dp),
			text = stringResource(R.string.app_theme),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.titleMedium
		)
		ThemeRadioGroup(selectedIndex, onThemeSelected, isTablet)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThemeRadioGroup(
	selectedIndex: Int,
	onThemeSelected: (Int) -> Unit,
	isTablet: Boolean
) {
	val radioOptions = listOf(
		stringResource(R.string.light_theme),
		stringResource(R.string.dark_theme),
		stringResource(R.string.system_theme)
	)

	Box(
		modifier = Modifier
			// 1. Сначала считаем идеальную ширину по тексту
			.requiredWidth(IntrinsicSize.Max)
			// 2. Затем втискиваем полученный результат в наши безопасные рамки
			.widthIn(
				min = if (isTablet) 360.dp else 260.dp,
				max = if (isTablet) 480.dp else 280.dp
			)
			.padding(16.dp)
			.clip(RoundedCornerShape(16.dp))
			.background(MaterialTheme.colorScheme.surfaceContainerHighest)
	) {
		Column {
			radioOptions.forEachIndexed { index, text ->
				Row(
					Modifier
						// Для Row оставляем только max, чтобы зажать текст при прокрутке.
						// Минимальные 260.dp (минус отступы) придут сверху от Box автоматически.
						.widthIn(max = if (isTablet) 460.dp else 260.dp)
						.fillMaxWidth()
						.selectable(
							selected = (index == selectedIndex),
							onClick = { onThemeSelected(index) },
							role = Role.RadioButton
						)
						.padding(
							vertical = if (isTablet) 24.dp else 12.dp,
							horizontal = if (isTablet) 24.dp else 16.dp,
						),
					verticalAlignment = Alignment.CenterVertically
				) {
					RadioButton(
						selected = (index == selectedIndex),
						onClick = null
					)
					Text(
						text = text,
						style = MaterialTheme.typography.bodyLarge,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						maxLines = 1,
						modifier = Modifier
							.padding(start = 16.dp)
							.weight(1f)
							.basicMarquee(
								iterations = Int.MAX_VALUE,
								repeatDelayMillis = 1000
							)
					)
				}
			}
		}
	}
}

@Composable
fun ResetDataContainer(
	isDialogVisible: Boolean,
	showDialog: () -> Unit,
	hideDialog: () -> Unit,
	clearAllData: () -> Unit,
	isTablet: Boolean,
	clearLocalBuffer: () -> Unit
) {
	Column(
		modifier = Modifier
			// 1. Измеряем ширину по минимально необходимому размеру контента
			.width(IntrinsicSize.Min)
			// 2. Втискиваем в рамки: не меньше 260.dp и не больше 320.dp
			.widthIn(
				min = if (isTablet) 360.dp else 260.dp,
				max = if (isTablet) 480.dp else 320.dp
			)
			.clip(RoundedCornerShape(16.dp))
			.background(MaterialTheme.colorScheme.surfaceContainer),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text(
			text = stringResource(R.string.clear_data_title),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier
				// Заменяем fillMaxWidth() на обычную заливку, чтобы текст не раздувал родителя насильно
				.padding(start = 16.dp, top = 16.dp, end = 16.dp)
		)

		Box(
			modifier = Modifier.padding(start = 16.dp, top = 28.dp, end = 16.dp, bottom = 16.dp)
		) {
			Button(
				onClick = showDialog,
				modifier = Modifier
					.heightIn(min = 64.dp) // Соответствует минимальным гайдлайнам Android для тач-зон
					.widthIn(min = if (isTablet) 200.dp else 150.dp),
				shape = RoundedCornerShape(8.dp)
			) {
				Text(
					text = stringResource(R.string.clear_data_button),
					style = MaterialTheme.typography.titleMedium
				)
			}
		}
	}

	if (isDialogVisible) {
		AlertDialog(
			onDismissRequest = hideDialog,
			// 2. Ограничиваем ширину: на телефоне по умолчанию, на планшете делаем чуть солиднее
			modifier = Modifier.widthIn(
				min = if (isTablet) 360.dp else 280.dp,
				max = if (isTablet) 560.dp else 400.dp
			),
			text = {
				Text(
					text = stringResource(R.string.clear_data_question),
					// 3. Увеличиваем шрифт для планшета для лучшей читаемости
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			},
			confirmButton = {
				Button(
					onClick = {
						clearAllData()
						clearLocalBuffer()
						hideDialog()
					},
					shape = RoundedCornerShape(8.dp),
					// 4. Кнопки на планшете делаем крупнее, чтобы по ним было легче попасть
					contentPadding = if (isTablet) PaddingValues(horizontal = 24.dp, vertical = 16.dp) else ButtonDefaults.ContentPadding
				) {
					Text(
						text = stringResource(R.string.yes_button),
						style = MaterialTheme.typography.titleMedium
					)
				}
			},
			dismissButton = {
				TextButton(
					onClick = hideDialog,
					shape = RoundedCornerShape(8.dp),
					contentPadding = if (isTablet) PaddingValues(horizontal = 24.dp, vertical = 16.dp) else ButtonDefaults.TextButtonContentPadding
				) {
					Text(
						text = stringResource(R.string.cancel_button),
						style = MaterialTheme.typography.titleMedium
					)
				}
			},
			shape = RoundedCornerShape(if (isTablet) 24.dp else 16.dp)
		)
	}
}