package alexrnov.eitest.presentation.menu.settings

import alexrnov.eitest.domain.repository.SettingsRepository
import alexrnov.eitest.domain.usecase.ClearAllDataUseCase // Примерный путь к вашему Use Case
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
	private val repository: SettingsRepository,
	//private val clearAllDataUseCase: ClearAllDataUseCase
) : ViewModel() {

	// Теперь поток идет из репозитория через абстракцию
	val radioButtonsState: StateFlow<Int> = repository.getThemeFlow()
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = 2
		)

	var isDialogVisible by mutableStateOf(false)
		private set

	fun showDialog() {
		isDialogVisible = true
	}

	fun hideDialog() {
		isDialogVisible = false
	}

	fun setTheme(value: Int) {
		viewModelScope.launch {
			repository.saveTheme(value) // Логика сохранения скрыта внутри data-модуля
		}
	}

	/*
	fun clearAllData() {
		viewModelScope.launch {
			clearAllDataUseCase.invoke()
		}
	}


	 */
}