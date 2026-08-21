package alexrnov.eitest.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import alexrnov.eitest.domain.AllPropertiesState
import alexrnov.eitest.domain.DEFAULT_SLIDER_VALUE

val Context.propertiesDataStore by preferencesDataStore(name = "properties")

class PropertiesDataStoreManager(context: Context) {
	private val dataStore = context.propertiesDataStore

	companion object {
		// red tab
		val LIFE_ENERGY_KEY = floatPreferencesKey("life_energy")
		val CREATIVE_KEY = floatPreferencesKey("creative")
		val JOY_KEY = floatPreferencesKey("joy")

		// orange tab
		val HEALTH_KEY = floatPreferencesKey("health")
		val SAFETY_KEY = floatPreferencesKey("safety")
		val FINANCE_KEY = floatPreferencesKey("finance")

		// yellow tab
		val CONFIDENCE_KEY = floatPreferencesKey("confidence")
		val VOLITION_KEY = floatPreferencesKey("volition")
		val SOCIAL_KEY = floatPreferencesKey("social")

		// green tab
		val KINDNESS_KEY = floatPreferencesKey("kindness")
		val HEART_KEY = floatPreferencesKey("heart")
		val NATURE_KEY = floatPreferencesKey("nature")

		// light blue tab
		val COMMUNICATION_KEY = floatPreferencesKey("communication")
		val HONESTY_KEY = floatPreferencesKey("honesty")
		val THROAT_KEY = floatPreferencesKey("throat")

		// blue tab
		val INTELLIGENCE_KEY = floatPreferencesKey("intelligence")
		val INTUITION_KEY = floatPreferencesKey("intuition")
		val IMAGINATION_KEY = floatPreferencesKey("imagination")

		// pink tab
		val UNIVERSE_KEY = floatPreferencesKey("universe")
		val SLEEP_KEY = floatPreferencesKey("sleep")
		val MENTAL_KEY = floatPreferencesKey("mental")
	}

	private val keys: Map<Int, List<Preferences.Key<Float>>> = mapOf(
		0 to listOf(HEALTH_KEY, SAFETY_KEY, FINANCE_KEY),
		1 to listOf(LIFE_ENERGY_KEY, CREATIVE_KEY, JOY_KEY),
		2 to listOf(CONFIDENCE_KEY, VOLITION_KEY, SOCIAL_KEY),
		3 to listOf(KINDNESS_KEY, HEART_KEY, NATURE_KEY),
		4 to listOf(COMMUNICATION_KEY, HONESTY_KEY, THROAT_KEY),
		5 to listOf(INTELLIGENCE_KEY, INTUITION_KEY, IMAGINATION_KEY),
		6 to listOf(UNIVERSE_KEY, SLEEP_KEY, MENTAL_KEY)
	)

	suspend fun saveProperty(tabIndex: Int, propertyIndex: Int, value: Float) {
		val key = keys[tabIndex]?.get(propertyIndex)?: return
		dataStore.edit { preferences ->
			preferences[key] = value
		}
	}

	suspend fun getProperty(tabIndex: Int, propertyIndex: Int): Float {
		val key = keys[tabIndex]?.get(propertyIndex)?: return DEFAULT_SLIDER_VALUE
		return dataStore.data.map { preferences ->
			preferences[key] ?: DEFAULT_SLIDER_VALUE
		}.first()
	}

	suspend fun getPropertiesForTab(tabIndex: Int): List<Float> {
		val keyList = keys[tabIndex] ?: return listOf(DEFAULT_SLIDER_VALUE, DEFAULT_SLIDER_VALUE, DEFAULT_SLIDER_VALUE)

		// Читаем файл всего ОДИН раз целиком
		return dataStore.data.map { preferences ->
			keyList.map { key -> preferences[key] ?: DEFAULT_SLIDER_VALUE }
		}.first() // Один быстрый запрос к диску вместо трёх
	}

	// Возвращает единый поток настроек для всего приложения
	fun getAllPropertiesFlow(): Flow<AllPropertiesState> {
		return dataStore.data.map { preferences ->
			val resultMap = mutableMapOf<Int, List<Float>>()

			keys.forEach { (tabIndex, keyList) ->
				// Для каждой вкладки собираем список из 3-х значений
				val sphereValues = keyList.map { key ->
					preferences[key] ?: DEFAULT_SLIDER_VALUE
				}
				resultMap[tabIndex] = sphereValues
			}

			AllPropertiesState(tabsData = resultMap)
		}
	}

	suspend fun clearAllProperties() {
		dataStore.edit { preferences ->
			// Просто полностью очищаем файл настроек DataStore.
			// При следующем чтении все ваши ключи вернут null,
			// и автоматически подставится DEFAULT_SLIDER_VALUE!
			preferences.clear()
		}
	}
}