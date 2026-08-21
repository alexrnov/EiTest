package alexrnov.eitest.presentation.navigation

import alexrnov.eitest.presentation.HomeComponent
import alexrnov.eitest.presentation.HomeViewModel
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import alexrnov.eitest.presentation.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Serializable
object Home

fun NavGraphBuilder.homeScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean
) {
	composable<Home> {
		// Достаем общую Activity-вьюмодель
		val activity = LocalActivity.current as? ComponentActivity
		//val viewModel: HomeViewModel = koinViewModel(viewModelStoreOwner = activity ?: error("Activity not found"))


		val owner = activity ?: LocalViewModelStoreOwner.current ?: error("No owner found")
		val viewModel: HomeViewModel = koinViewModel(viewModelStoreOwner = owner)

		val state by viewModel.uiState.collectAsStateWithLifecycle()
		val isPreview = LocalInspectionMode.current
		if (state.isAllDataLoaded || isPreview) {
			HomeComponent(innerPadding, isLandscape, isTablet)
		} else {
			// Пока DataStore читает диск (доли секунды), показываем заглушку или лоадер.
			// Это полностью защитит от падений и уберет "прыжки" интерфейса.
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				//CircularProgressIndicator() // или просто пустой Box(Modifier.fillMaxSize())
				PulsingLoadingText()
			}
		}
	}
}

@Composable
fun PulsingLoadingText() {
	val infiniteTransition = rememberInfiniteTransition(label = "pulse")
	val alpha by infiniteTransition.animateFloat(
		initialValue = 0.3f,
		targetValue = 1f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
			repeatMode = RepeatMode.Reverse
		),
		label = "alpha"
	)

	Text(
		text = stringResource(R.string.loading),
		modifier = Modifier.graphicsLayer(alpha = alpha),
		style = MaterialTheme.typography.titleLarge,
		color = MaterialTheme.colorScheme.onSurface
	)
}