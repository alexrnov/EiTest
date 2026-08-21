plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.compose)

	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "alexrnov.eitest.presentation"
	compileSdk {
		version = release(37)
	}

	defaultConfig {
		minSdk = 24

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.core.ktx) // KTX расширения (базовые функции Kotlin для Android)
	implementation(libs.material)
	testImplementation(libs.junit) // тестирование интерфейса
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.junit)

	// платформа Compose (обязательно для управления версиями)
	implementation(platform(libs.androidx.compose.bom))

	// основные компоненты интерфейса
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.material3)

	// инструменты для Preview в Android Studio
	implementation(libs.androidx.compose.ui.tooling.preview)
	debugImplementation(libs.androidx.compose.ui.tooling)

	//
	implementation(libs.androidx.navigation.compose)
	// Сама библиотека для работы с JSON / навигацией
	implementation(libs.kotlinx.serialization.json)

	// жизненный цикл (нужно для ViewModel и collectAsStateWithLifecycle)
	implementation(libs.androidx.lifecycle.runtime.ktx)

	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	debugImplementation(libs.androidx.compose.ui.test.manifest)

	implementation(platform("io.insert-koin:koin-bom:4.0.3"))
	implementation("io.insert-koin:koin-android")
	implementation("io.insert-koin:koin-androidx-compose")

	implementation(project(":domain"))
}