package alexrnov.eitest.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
	fun getThemeFlow(): Flow<Int>
	suspend fun saveTheme(value: Int)
}