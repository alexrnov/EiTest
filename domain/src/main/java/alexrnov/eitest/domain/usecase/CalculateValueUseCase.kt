package alexrnov.eitest.domain.usecase

class CalculateValueUseCase {
	operator fun invoke(prop1: Float, prop2: Float, prop3: Float): Float {
		return prop1 + prop2 + prop3
	}
}