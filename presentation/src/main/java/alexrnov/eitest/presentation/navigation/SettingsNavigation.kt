package alexrnov.eitest.presentation.navigation

import alexrnov.eitest.presentation.menu.settings.SettingsComponent
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

import alexrnov.eitest.presentation.HomeViewModel
import alexrnov.eitest.presentation.menu.settings.SettingsComponent
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity

import org.koin.androidx.compose.koinViewModel

@Serializable object Settings

fun NavGraphBuilder.settingsScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean,
) {
	composable<Settings> {
		// Достаем общую Activity-вьюмодель
		val activity = LocalActivity.current as? ComponentActivity
		val homeViewModel: HomeViewModel = koinViewModel(viewModelStoreOwner = activity ?: error("Activity not found"))

		SettingsComponent(
			isLandscape = isLandscape,
			innerPadding = innerPadding,
			isTablet = isTablet,
			clearLocalBuffer = {
				homeViewModel.clearLocalBuffer()
			}
		)
	}
}