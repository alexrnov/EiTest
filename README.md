# SkillUp – Soft Skills Test

The app determines your soft skills level in just a few questions. The test evaluates three key areas: emotional intelligence (EQ) — the ability to understand your own and others' emotions, social intelligence (SQ) — communication and social interaction skills, and adaptability quotient (AQ) — the ability to adapt to changes and handle stress.

<em>The application was developed using Jetpack Compose and Kotlin.</em>

<h3 align="center">Light theme</h3>

<p align="center">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_1.png" hspace="10" width="180" title="UI">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_2.png" hspace="10" width="180" title="UI">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_3.png" hspace="10" width="180" title="UI">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_4.png" hspace="10" width="180" title="UI">
</p>

<h3 align="center">Dark theme</h3>

<p align="center">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_9.png" hspace="10" width="180" title="UI">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_5.png" hspace="10" width="180" title="UI">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_6.png" hspace="10" width="180" title="UI">
  <img src="https://github.com/alexrnov/Files/blob/master/ei_test_8.png" hspace="10" width="180" title="UI">
</p>

Code example:

```kotlin
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    isTablet: Boolean,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = adaptiveTypography(isTablet),
        content = content
    )
}
```
