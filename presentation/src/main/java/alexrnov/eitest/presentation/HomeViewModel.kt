package alexrnov.eitest.presentation

import alexrnov.eitest.domain.repository.PropertiesRepository
import alexrnov.eitest.domain.usecase.CalculateValueUseCase
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import alexrnov.eitest.domain.DEFAULT_SLIDER_VALUE

// Модель состояния всего экрана
data class HomeUiState(
	val sphereStates: List<SphereState> = emptyList(),
	val isAllDataLoaded: Boolean = false
)

class HomeViewModel(
	private val repository: PropertiesRepository,
	private val calculateValueUseCase: CalculateValueUseCase
) : ViewModel() {
	private val _selectedCategory = MutableStateFlow(IntellectCategory.EQ)
	val selectedCategory = _selectedCategory.asStateFlow()

	fun selectCategory(value: IntellectCategory) {
		_selectedCategory.value = value
	}





	val defaultSpheres = AppTab.entries.map {
		SphereState(
			currentCalculatedValue = calculateValueUseCase(DEFAULT_SLIDER_VALUE, DEFAULT_SLIDER_VALUE, DEFAULT_SLIDER_VALUE),
			property1 = DEFAULT_SLIDER_VALUE,
			property2 = DEFAULT_SLIDER_VALUE,
			property3 = DEFAULT_SLIDER_VALUE
		)
	}

	val selectedTabIndex = MutableStateFlow(0)

	// БУФЕР: Хранит временные значения слайдеров, которые двигают прямо сейчас.
	// Ключ — строка вида "tabIndex_propertyIndex" (например, "0_2"), значение — Float.
	private val _localUpdates = MutableStateFlow<Map<String, Float>>(emptyMap())

	val uiState: StateFlow<HomeUiState> = combine(
		repository.getAllPropertiesFlow(),
		_localUpdates
	) { allData, localUpdates ->

		val states = AppTab.entries.map { tab ->
			// Получаем дефолтные или дисковые значения для 3-х параметров вкладки
			val dbValues = allData.tabsData[tab.index] ?: listOf(DEFAULT_SLIDER_VALUE, DEFAULT_SLIDER_VALUE, DEFAULT_SLIDER_VALUE)

			// Проверяем: если пользователь СЕЙЧАС двигает слайдер, берем значение из памяти,
			// если нет — берем сохраненное с диска (dbValues)
			val p1 = localUpdates["${tab.index}_0"] ?: dbValues[0]
			val p2 = localUpdates["${tab.index}_1"] ?: dbValues[1]
			val p3 = localUpdates["${tab.index}_2"] ?: dbValues[2]

			SphereState(
				currentCalculatedValue = calculateValueUseCase(p1, p2, p3),
				property1 = p1,
				property2 = p2,
				property3 = p3
			)
		}

		val isLoaded = allData.tabsData.isNotEmpty() && allData.tabsData.size == AppTab.entries.size

		HomeUiState(sphereStates = states, isAllDataLoaded = isLoaded)
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000),
		initialValue = HomeUiState(sphereStates = defaultSpheres, isAllDataLoaded = false)
	)

	// ВАЖНО: При движении слайдера пишем ТОЛЬКО в оперативную память (это мгновенно и плавно)
	fun updateProperty(tabIndex: Int, propertyIndex: Int, value: Float) {
		val key = "${tabIndex}_$propertyIndex"
		_localUpdates.value = _localUpdates.value + (key to value)
	}

	// При отпускании пальца записываем на диск
	fun saveProperty(tabIndex: Int, propertyIndex: Int, value: Float) {
		viewModelScope.launch {
			repository.saveProperty(tabIndex, propertyIndex, value)
			// Дополнительно можно очистить этот ключ из памяти, так как диск обновился:
			// val key = "${tabIndex}_$propertyIndex"
			// _localUpdates.value = _localUpdates.value - key
		}
	}

	fun selectTab(index: Int) {
		selectedTabIndex.value = index
	}

	fun clearLocalBuffer() {
		// Полностью очищаем оперативную память от временных сдвигов слайдеров
		_localUpdates.value = emptyMap()
	}



}

enum class IntellectCategory { EQ, SQ, RQ }

data class SphereState(
	val currentCalculatedValue: Float,
	val property1: Float,
	val property2: Float,
	val property3: Float
)

enum class AppTab(val index: Int, val key: String) {
	RED(0, "red"),
	ORANGE(1, "orange"),
	YELLOW(2, "yellow"),
	GREEN(3, "green"),
	LIGHT_BLUE(4, "light_blue"),
	BLUE(5, "blue"),
	PINK(6, "pink")
}