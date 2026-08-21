package alexrnov.eitest.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore("settings")

class SettingsDataStoreManager(context: Context) {
	private val dataStore = context.settingsDataStore

	companion object {
		val THEME_KEY = intPreferencesKey("theme")
		const val DEFAULT_THEME_VALUE = 2
	}

	val themeFlow: Flow<Int> = dataStore.data.map { preferences ->
		preferences[THEME_KEY] ?: DEFAULT_THEME_VALUE
	}

	suspend fun saveTheme(value: Int) {
		dataStore.edit { preferences ->
			preferences[THEME_KEY] = value
		}
	}
}