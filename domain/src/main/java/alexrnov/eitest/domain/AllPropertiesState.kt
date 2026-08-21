package alexrnov.eitest.domain

data class AllPropertiesState(
	// Ключ — индекс вкладки (0..2), значение — список из 3-х значений для шкалы каждого вопроса
	val tabsData: Map<Int, List<Float>> = emptyMap()
)