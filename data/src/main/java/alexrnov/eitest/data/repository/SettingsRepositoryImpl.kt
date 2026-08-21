package alexrnov.eitest.data.repository

import alexrnov.eitest.data.local.SettingsDataStoreManager
import alexrnov.eitest.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
	private val dataStoreManager: SettingsDataStoreManager
) : SettingsRepository {

	override fun getThemeFlow(): Flow<Int> = dataStoreManager.themeFlow

	override suspend fun saveTheme(value: Int) {
		dataStoreManager.saveTheme(value)
	}
}