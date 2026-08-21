package alexrnov.eitest.domain

data class AllPropertiesState(
	// Ключ — индекс вкладки (0..6), значение — список из 3-х интенсивностей сфер
	val tabsData: Map<Int, List<Float>> = emptyMap()
)