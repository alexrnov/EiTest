package alexrnov.eitest.presentation.navigation

import alexrnov.eitest.presentation.HomeComponent
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable

@Serializable
object Home

fun NavGraphBuilder.homeScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean
) {
	composable<Home> {
		HomeComponent(innerPadding, isLandscape, isTablet)
	}
}