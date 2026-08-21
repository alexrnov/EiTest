plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.compose)
}

android {
	namespace = "alexrnov.eitest"
	compileSdk {
		version = release(37)
	}

	defaultConfig {
		applicationId = "alexrnov.eitest"
		minSdk = 24
		targetSdk = 37
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			optimization {
				enable = false
			}
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		compose = true
	}
}

dependencies {
	implementation(libs.androidx.adaptive)
	// BOM и Базовый Compose для запуска Activity
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.activity.compose) // для ComponentActivity и setContent
	implementation(libs.androidx.compose.ui) // для @Composable и Modifier

	// Превью (нужно, так как в MainActivity остался @DevicePreviews)
	implementation(libs.androidx.compose.ui.tooling.preview)
	debugImplementation(libs.androidx.compose.ui.tooling)

	// Тесты (оставляем базовые)
	testImplementation(libs.junit)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.junit)

	implementation(platform("io.insert-koin:koin-bom:4.0.3"))
	implementation("io.insert-koin:koin-android")
	implementation("io.insert-koin:koin-androidx-compose")

	implementation(project(":presentation"))
	implementation(project(":domain"))
	implementation(project(":data"))
}