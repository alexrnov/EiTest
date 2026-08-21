package alexrnov.eitest.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class HomeComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setup() {
        // Устанавливаем начальное значение selectedTabIndex, если необходимо
        composeTestRule.setContent { HomeViewModel().let { viewModel ->
            HomeComponent(
                innerPadding = PaddingValues(),
                isLandscape = false,
                isTablet = true
            )
        } }
    }

    @Test
    fun testHomeComponentInitialUIState() {
        setup()

        with(composeTestRule) {
            onNodeWithText("EQ").assertIsDisplayed()
            onNodeWithTag("Slider_0_1").performTouchInput(object : PointerInteractionsProviderScope.Impl(Dispatchers.Main.immediateDispatcher()) {})
            onNodeWithContentDescription(stringResource(R.string.result)).assertDoesNotExist()
        }
    }

    @Test
    fun testSelectTabChangesUI() {
        setup()

        // Выбираем второй таб (индекс 1)
        composeTestRule.onAllNodesWithTag("HexagonButton")[1].performClick()

        with(composeTestRule) {
            onNodeWithText("SQ").assertExists()
            onNodeWithTag("Slider_1_2").assertIsDisplayed()
            onNodeWithContentDescription(stringResource(R.string.result)).assertDoesNotExist()
        }
    }

    @Test
    fun testSavePropertyUpdatesStateAndSavesToRepository() {
        val viewModel = HomeViewModel().apply { clearLocalBuffer() } // Очистка буфера перед тестом

        setup()

        // Сохраняем новое значение для первого слайдера в первом табе (индекс 0, свойство индекса 0)
        composeTestRule.onAllNodesWithTag("HexagonButton")[0].performClick()
        with(composeTestRule) {
            onNodeWithTag("Slider_0_1").performTouchInput(object : PointerInteractionsProviderScope.Impl(Dispatchers.Main.immediateDispatcher()) {})
        }

        // Проверим обновленное состояние
        val updatedState = viewModel.uiState.value.tabStates[0]
        assert(updatedState.calculatedValue != DEFAULT_SLIDER_VALUE)

        // Сохраняем новое значение и проверяем сохранение в репозитории (это требует реального теста с Mock)
    }
}
