package alexrnov.eitest.presentation.navigation

import alexrnov.eitest.presentation.menu.settings.SettingsComponent
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable object Settings

fun NavGraphBuilder.settingsScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean,
) {
	composable<Settings> {

		SettingsComponent(
			isLandscape = isLandscape,
			innerPadding = innerPadding,
			isTablet = isTablet,
			clearLocalBuffer = {
				//homeViewModel.clearLocalBuffer()
			}
		)
	}
}