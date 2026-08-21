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

	packaging {
		resources {
			excludes += "META-INF/LICENSE.md"
			excludes += "META-INF/LICENSE-notice.md"
			excludes += "META-INF/LICENSE*"
			excludes += "META-INF/NOTICE*"
		}
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
	implementation(libs.androidx.compose.ui.text)
	implementation(libs.androidx.core.ktx)
	implementation(libs.material)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.junit)

	// платформа Compose
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.activity.compose)

	// основные компоненты интерфейса
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.material3)

	// инструменты для Preview
	implementation(libs.androidx.compose.ui.tooling.preview)
	debugImplementation(libs.androidx.compose.ui.tooling)

	implementation(libs.androidx.navigation.compose)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.androidx.lifecycle.runtime.ktx)

	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	debugImplementation(libs.androidx.compose.ui.test.manifest)

	implementation(platform("io.insert-koin:koin-bom:4.0.3"))
	implementation("io.insert-koin:koin-android")
	implementation("io.insert-koin:koin-androidx-compose")

	// --- Зависимости для androidTest (Инструментальные тесты) ---
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test:runner:1.6.2")

	// Koin для тестов в Android
	androidTestImplementation("io.insert-koin:koin-test:3.5.6")
	androidTestImplementation("io.insert-koin:koin-android-test:3.5.6")

	// Поддержка сорутин в тестах
	androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")

	// СТАБИЛЬНЫЙ MOCKITO ДЛЯ ANDROID (Взамен капризного MockK)
	androidTestImplementation("org.mockito:mockito-android:5.23.0")

	// --- Зависимости для Jetpack Compose & Lifecycle ---
	implementation("androidx.compose.ui:ui:1.7.2")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")

	// Локальные Unit-тесты на ПК (JUnit 4 + Mockito Core)
	testImplementation(libs.mockito.core)

	implementation(project(":domain"))
}