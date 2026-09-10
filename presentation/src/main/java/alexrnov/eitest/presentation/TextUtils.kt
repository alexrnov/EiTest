package alexrnov.eitest.presentation

import alexrnov.eitest.presentation.Archetype.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlin.math.roundToInt

@Composable
fun getTabsText(): List<String> {
	return listOf(
		stringResource(R.string.eq_tab),
		stringResource(R.string.sq_tab),
		stringResource(R.string.rq_tab)
	)
}

fun categoryLevel(value: Float): String {
	val percent = ((value / 12.0) * 100).roundToInt()
	return ": $percent%"
}

data class TabContent(
	val questions1: List<Question>,
	val questions2: List<Question>,
	val questions3: List<Question>
)

data class ArchetypeContent(val name: String, val description: String, val advice: String)

data class Question(val text: String, val forward: Boolean)

@Composable
fun getArchetypeContent(type: Archetype): ArchetypeContent {
	return when (type) {
		HARMONY -> ArchetypeContent(
			stringResource(R.string.harmony_name),
			stringResource(R.string.harmony_desc),
			stringResource(R.string.harmony_advice)
		)
		INTEGRAL_INTELLIGENCE -> ArchetypeContent(
			stringResource(R.string.integral_name),
			stringResource(R.string.integral_desc),
			stringResource(R.string.integral_advice)
		)
		TOTAL_CRISIS -> ArchetypeContent(
			stringResource(R.string.crisis_name),
			stringResource(R.string.crisis_desc),
			stringResource(R.string.crisis_advice)
		)
		STRATEGIST -> ArchetypeContent(
			stringResource(R.string.strategist_name),
			stringResource(R.string.strategist_desc),
			stringResource(R.string.strategist_advice)
		)
		CHARISMATIC -> ArchetypeContent(
			stringResource(R.string.charismatic_name),
			stringResource(R.string.charismatic_desc),
			stringResource(R.string.charismatic_advice)
		)
		EMOTIONAL_BURNOUT -> ArchetypeContent(
			stringResource(R.string.burnout_name),
			stringResource(R.string.burnout_desc),
			stringResource(R.string.burnout_advice)
		)
		SOCIAL_DETACHMENT -> ArchetypeContent(
			stringResource(R.string.detachment_name),
			stringResource(R.string.detachment_desc),
			stringResource(R.string.detachment_advice)
		)
		STONE -> ArchetypeContent(
			stringResource(R.string.stone_name),
			stringResource(R.string.stone_desc),
			stringResource(R.string.stone_advice)
		)
		EMPATH -> ArchetypeContent(
			stringResource(R.string.empath_name),
			stringResource(R.string.empath_desc),
			stringResource(R.string.empath_advice)
		)
		DIPLOMAT -> ArchetypeContent(
			stringResource(R.string.diplomat_name),
			stringResource(R.string.diplomat_desc),
			stringResource(R.string.diplomat_advice)
		)
		VULNERABLE_EXPERT -> ArchetypeContent(
			stringResource(R.string.vulnerable_name),
			stringResource(R.string.vulnerable_desc),
			stringResource(R.string.vulnerable_advice)
		)
		LONELY_SAGE -> ArchetypeContent(
			stringResource(R.string.lonely_name),
			stringResource(R.string.lonely_desc),
			stringResource(R.string.lonely_advice)
		)
		EMPATHY_DEFICIT -> ArchetypeContent(
			stringResource(R.string.deficit_name),
			stringResource(R.string.deficit_desc),
			stringResource(R.string.deficit_advice)
		)
		ISOLATED_WORKER -> ArchetypeContent(
			stringResource(R.string.isolated_name),
			stringResource(R.string.isolated_desc),
			stringResource(R.string.isolated_advice)
		)
	}
}

@Composable
fun getTabContent(index: Int): TabContent {
	return when (index) {
		0 -> TabContent(
			questions1 = listOf(
				Question(stringResource(R.string.eq_q1_v1), forward(R.string.eq_q1_v1_forward)),
				Question(stringResource(R.string.eq_q1_v2), forward(R.string.eq_q1_v2_forward)),
				Question(stringResource(R.string.eq_q1_v3), forward(R.string.eq_q1_v3_forward))
			),
			questions2 = listOf(
				Question(stringResource(R.string.eq_q2_v1), forward(R.string.eq_q2_v1_forward)),
				Question(stringResource(R.string.eq_q2_v2), forward(R.string.eq_q2_v2_forward)),
				Question(stringResource(R.string.eq_q2_v3), forward(R.string.eq_q2_v3_forward))
			),
			questions3 = listOf(
				Question(stringResource(R.string.eq_q3_v1), forward(R.string.eq_q3_v1_forward)),
				Question(stringResource(R.string.eq_q3_v2), forward(R.string.eq_q3_v2_forward)),
				Question(stringResource(R.string.eq_q3_v3), forward(R.string.eq_q3_v3_forward))
			)
		)
		1 -> TabContent(
			questions1 = listOf(
				Question(stringResource(R.string.sq_q1_v1), forward(R.string.sq_q1_v1_forward)),
				Question(stringResource(R.string.sq_q1_v2), forward(R.string.sq_q1_v2_forward)),
				Question(stringResource(R.string.sq_q1_v3), forward(R.string.sq_q1_v3_forward)),
			),
			questions2 = listOf(
				Question(stringResource(R.string.sq_q2_v1), forward(R.string.sq_q2_v1_forward)),
				Question(stringResource(R.string.sq_q2_v2), forward(R.string.sq_q2_v2_forward)),
				Question(stringResource(R.string.sq_q2_v3), forward(R.string.sq_q2_v3_forward))
			),
			questions3 = listOf(
				Question(stringResource(R.string.sq_q3_v1), forward(R.string.sq_q3_v1_forward)),
				Question(stringResource(R.string.sq_q3_v2), forward(R.string.sq_q3_v2_forward)),
				Question(stringResource(R.string.sq_q3_v3), forward(R.string.sq_q3_v3_forward))
			)
		)
		else -> TabContent(
			questions1 = listOf(
				Question(stringResource(R.string.rq_q1_v1), forward(R.string.rq_q1_v1_forward)),
				Question(stringResource(R.string.rq_q1_v2), forward(R.string.rq_q1_v2_forward)),
				Question(stringResource(R.string.rq_q1_v3), forward(R.string.rq_q1_v3_forward)),
			),
			questions2 = listOf(
				Question(stringResource(R.string.rq_q2_v1), forward(R.string.rq_q2_v1_forward)),
				Question(stringResource(R.string.rq_q2_v2), forward(R.string.rq_q2_v2_forward)),
				Question(stringResource(R.string.rq_q2_v3), forward(R.string.rq_q2_v3_forward))
			),

			questions3 = listOf(
				Question(stringResource(R.string.rq_q3_v1), forward(R.string.rq_q3_v1_forward)),
				Question(stringResource(R.string.rq_q3_v2), forward(R.string.rq_q3_v2_forward)),
				Question(stringResource(R.string.rq_q3_v3), forward(R.string.rq_q3_v3_forward))
			)
		)
	}
}

@Composable
fun forward(resource: Int) = stringResource(resource).toBoolean()
