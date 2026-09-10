package alexrnov.eitest.presentation.navigation

import alexrnov.eitest.presentation.HomeViewModel
import alexrnov.eitest.presentation.getArchetypeContent
import alexrnov.eitest.presentation.menu.ResultComponent
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object ResultTest

fun NavGraphBuilder.resultScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean
) {
	composable<ResultTest> {
		val activity = LocalActivity.current as? ComponentActivity
		val homeViewModel: HomeViewModel = koinViewModel(viewModelStoreOwner = activity ?: error("Activity not found"))

		val softSkills by homeViewModel.softSkills.collectAsStateWithLifecycle()
		val content = getArchetypeContent(softSkills.type)

		ResultComponent(
			isLandscape = isLandscape,
			isTablet = isTablet,
			innerPadding = innerPadding,
			content = content
		)
	}
}