package alexrnov.eitest.data

import alexrnov.eitest.data.local.PropertiesDataStoreManager
import alexrnov.eitest.data.local.SettingsDataStoreManager
import alexrnov.eitest.data.repository.PropertiesRepositoryImpl
import alexrnov.eitest.data.repository.SettingsRepositoryImpl
import alexrnov.eitest.domain.repository.PropertiesRepository
import alexrnov.eitest.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
	// single создает синглтон. Передаем androidContext() для DataStoreManager
	single { SettingsDataStoreManager(context = androidContext()) }
	// Связываем интерфейс из domain с реализацией из data
	single<SettingsRepository> { SettingsRepositoryImpl(dataStoreManager = get()) }

	// Создаем менеджер (нужен Context, Koin подставит androidContext())
	single { PropertiesDataStoreManager(get()) }
	// Связываем интерфейс репозитория с его реализацией
	single<PropertiesRepository> { PropertiesRepositoryImpl(get()) }
}