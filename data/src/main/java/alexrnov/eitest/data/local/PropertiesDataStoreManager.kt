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
import alexrnov.eitest.domain.DEFAULT_QUESTION_INDEX
import alexrnov.eitest.domain.DEFAULT_SLIDER_VALUE
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.distinctUntilChanged

val Context.propertiesDataStore by preferencesDataStore(name = "properties")

class PropertiesDataStoreManager(context: Context) {
	private val dataStore = context.propertiesDataStore

	companion object {
		// EQ tab
		val EQ1 = floatPreferencesKey("eq1")
		val EQ2 = floatPreferencesKey("eq2")
		val EQ3 = floatPreferencesKey("eq3")

		// SQ tab
		val SQ1 = floatPreferencesKey("sq1")
		val SQ2 = floatPreferencesKey("sq2")
		val SQ3 = floatPreferencesKey("sq3")

		// RQ tab
		val RQ1 = floatPreferencesKey("rq1")
		val RQ2 = floatPreferencesKey("rq2")
		val RQ3 = floatPreferencesKey("rq3")

		val QUESTION_INDEX = intPreferencesKey("question_index")
	}

	private val keys: Map<Int, List<Preferences.Key<Float>>> = mapOf(
		0 to listOf(EQ1, EQ2, EQ3),
		1 to listOf(SQ1, SQ2, SQ3),
		2 to listOf(RQ1, RQ2, RQ3)
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
			// пропускает UI-событие дальше только если данные реально изменились,
			// чтобы при изменении questionIndex не обновлялись ползунки
		}.distinctUntilChanged()
	}

	suspend fun clearAllProperties() {
		/*
		dataStore.edit { preferences ->
			// Просто полностью очищаем файл настроек DataStore.
			// При следующем чтении все ваши ключи вернут null,
			// и автоматически подставится DEFAULT_SLIDER_VALUE!
			preferences.clear()
		}

		 */

		dataStore.edit { preferences ->
			// Вместо полной очистки файла, удаляем только ключи слайдеров
			keys.values.flatten().forEach { key ->
				preferences.remove(key)
			}
		}
	}

	suspend fun saveQuestionIndex(index: Int) {
		dataStore.edit { preferences ->
			preferences[QUESTION_INDEX] = index
		}
	}

	fun getQuestionIndex(): Flow<Int> {
		return dataStore.data.map { preferences ->
			preferences[QUESTION_INDEX] ?: DEFAULT_QUESTION_INDEX
		}
	}
}