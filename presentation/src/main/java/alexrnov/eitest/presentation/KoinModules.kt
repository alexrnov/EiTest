package alexrnov.eitest.presentation

import alexrnov.eitest.presentation.menu.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
	viewModel {
		HomeViewModel(
			repository = get(),
			calculateValueUseCase = get()
		)
	}

	viewModel {
		SettingsViewModel(
			repository = get(), // Koin сам найдет SettingsRepository из dataModule
			//clearAllDataUseCase = get() // Koin сам найдет ClearAllData из domainModule
		)
	}
}