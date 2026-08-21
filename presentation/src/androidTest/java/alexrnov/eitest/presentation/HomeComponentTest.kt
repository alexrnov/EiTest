package alexrnov.eitest.presentation

import alexrnov.eitest.domain.AllPropertiesState
import alexrnov.eitest.domain.repository.PropertiesRepository
import alexrnov.eitest.domain.usecase.CalculateValueUseCase
import alexrnov.eitest.domain.usecase.ClearAllDataUseCase
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

// 1. Возвращаем стандартный Android-раннер без всякой параметризации
@RunWith(AndroidJUnit4::class)
class HomeComponentTest {
	@get:Rule
	val composeTestRule = createComposeRule()

	private val mockRepository = mock(PropertiesRepository::class.java)
	private val mockCalculateUseCase = mock(CalculateValueUseCase::class.java)
	private val mockClearUseCase = mock(ClearAllDataUseCase::class.java)

	private lateinit var realViewModel: HomeViewModel

	@Before
	fun setUp() {
		stopKoin() // Очищаем контекст Koin перед стартом

		// Наполняем мапу безопасными данными, чтобы ViewModel успешно инициализировалась
		val safeData = mapOf(
			0 to listOf(2f, 2f, 2f),
			1 to listOf(2f, 2f, 2f),
			2 to listOf(2f, 2f, 2f)
		)

		val fakeFlow = MutableStateFlow(AllPropertiesState(safeData))
		val fakeIndexFlow = MutableStateFlow(0)

		`when`(mockRepository.getAllPropertiesFlow()).thenReturn(fakeFlow)
		`when`(mockRepository.getQuestionIndex()).thenReturn(fakeIndexFlow)

		// Создаем реальную ViewModel
		realViewModel = HomeViewModel(
			repository = mockRepository,
			calculateValueUseCase = mockCalculateUseCase,
			clearAllDataUseCase = mockClearUseCase
		)

		startKoin {
			modules(module {
				single<HomeViewModel> { realViewModel }
			})
		}
	}

	@After
	fun tearDown() {
		stopKoin() // Выгружаем контекст
	}

	@Test
	fun homeComponent_rendersAndWorksCorrectly_inAllOrientations() {
		// Создаем изменяемый стейт для ориентации (по умолчанию Portrait - false)
		val landscapeState = mutableStateOf(false)

		// Вызываем setContent ВСЕГО ОДИН РАЗ
		composeTestRule.setContent {
			HomeComponent(
				innerPadding = PaddingValues(0.dp),
				isLandscape = landscapeState.value, // Передаем значение стейта
				isTablet = false
			)
		}

		// ==========================================
		// 1. Проверка вертикального режима (Portrait)
		// ==========================================
		// Проверяем, что в портретном режиме отображаются гексагоны EQ и SQ
		composeTestRule.onNodeWithText("EQ").assertIsDisplayed()
		composeTestRule.onNodeWithText("SQ").assertIsDisplayed()

		// Кликаем по табу SQ (Индекс 1) и проверяем, что стейт во ViewModel обновился
		composeTestRule.onNodeWithText("SQ").performClick()
		assertEquals(1, realViewModel.selectedTabIndex.value)

		// ==========================================
		// 2. Проверка горизонтального режима (Landscape)
		// ==========================================
		// Сбрасываем выбранный таб обратно на 0 для чистоты следующей проверки
		realViewModel.selectTab(0)

		// Меняем значение стейта прямо посреди теста!
		// Compose сам мгновенно перерисует экран и переключится на MinHexagonList
		landscapeState.value = true

		// Проверяем, что в горизонтальном режиме элементы MinHexagonList тоже успешно отображаются
		composeTestRule.onNodeWithText("EQ").assertIsDisplayed()
		composeTestRule.onNodeWithText("SQ").assertIsDisplayed()

		// Проверяем клик в горизонтальной ориентации
		composeTestRule.onNodeWithText("SQ").performClick()
		assertEquals(1, realViewModel.selectedTabIndex.value)
	}
}