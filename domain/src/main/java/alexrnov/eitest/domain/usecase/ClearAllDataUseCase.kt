package alexrnov.eitest.domain.usecase

import alexrnov.eitest.domain.repository.PropertiesRepository

class ClearAllDataUseCase(private val repository: PropertiesRepository) {
	suspend operator fun invoke() {
		// Домен управляет процессом, но делегирует физическое удаление репозиторию
		// withContext(Dispatchers.IO)  здесь не нужен, поскольку функция edit сама переключает выполнение на фоновый поток
		// Вызываем метод полной очистки репозитория
		repository.clearAllProperties()
	}
}