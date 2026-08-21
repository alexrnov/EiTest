package alexrnov.eitest.presentation

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

// 1. Указываем раннер параметризации JUnit 4
@RunWith(Parameterized::class)
class ArchetypeUnitTest(
	// Параметры передаются через конструктор класса
	private val expected: Archetype,
	private val eqValue: Int,
	private val sqValue: Int,
	private val aqValue: Int,
	private val scenarioName: String
) {

	data class ArchetypeTestCase(
		val expected: Archetype,
		val eq: Int,
		val sq: Int,
		val aq: Int,
		val scenarioName: String
	)

	@Test
	fun get_archetype_based_on_values() {
		val result = getArchetype(eqValue, sqValue, aqValue)
		assertEquals("Ошибка в сценарии: $scenarioName", expected, result)
	}

	companion object {
		@JvmStatic
		@Parameterized.Parameters(name = "{index} ==> {4} (EQ={1}, SQ={2}, AQ={3}) -> Ожидаем {0}")
		fun archetypeCasesProvider(): Collection<Array<Any>> {
			return listOf(
				ArchetypeTestCase(Archetype.INTEGRAL_INTELLIGENCE, 90, 95, 90, "Абсолютный максимум"),
				ArchetypeTestCase(Archetype.TOTAL_CRISIS, 9, 5, 0, "Абсолютный минимум (<10)"),
				ArchetypeTestCase(Archetype.EMOTIONAL_BURNOUT, 25, 35, 20, "Выгорание: все <40, EQ и AQ <30"),
				ArchetypeTestCase(Archetype.SOCIAL_DETACHMENT, 35, 35, 35, "Социальная отстраненность: все <40, но EQ/AQ >=30"),
				ArchetypeTestCase(Archetype.ISOLATED_WORKER, 20, 20, 45, "Изолированный воркер: EQ/SQ <=25, AQ >40"),
				ArchetypeTestCase(Archetype.STRATEGIST, 75, 75, 85, "Стратег: топ, AQ доминирует"),
				ArchetypeTestCase(Archetype.CHARISMATIC, 85, 80, 75, "Харизматик: топ, AQ не доминирует"),
				ArchetypeTestCase(Archetype.VULNERABLE_EXPERT, 60, 60, 30, "Ранимый эксперт: дефицит AQ"),
				ArchetypeTestCase(Archetype.LONELY_SAGE, 60, 30, 60, "Одинокий мудрец: дефицит SQ"),
				ArchetypeTestCase(Archetype.EMPATHY_DEFICIT, 30, 60, 60, "Дефицит эмпатии: дефицит EQ"),
				ArchetypeTestCase(Archetype.HARMONY, 50, 50, 50, "Гармония: все равны"),
				ArchetypeTestCase(Archetype.STONE, 55, 55, 70, "Кремень: AQ преобладает, разрывов нет"),
				ArchetypeTestCase(Archetype.EMPATH, 65, 50, 50, "Эмпат: EQ преобладает"),

				// ИСПРАВЛЕНО: сгладили разрыв до 15%, чтобы не триггерить зону дефицита эмпатии
				ArchetypeTestCase(Archetype.DIPLOMAT, 55, 70, 55, "Дипломат: SQ преобладает")
			).map {
				arrayOf(it.expected, it.eq, it.sq, it.aq, it.scenarioName)
			}
		}
	}
}

