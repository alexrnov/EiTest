package alexrnov.eitest.domain

import alexrnov.eitest.domain.usecase.CalculateValueUseCase
import alexrnov.eitest.domain.usecase.ClearAllDataUseCase
import org.koin.dsl.module

val domainModule = module {
	// factory создает новый экземпляр Use Case каждый раз, когда он запрашивается
	factory { ClearAllDataUseCase(get()) }

	factory { CalculateValueUseCase() }
}