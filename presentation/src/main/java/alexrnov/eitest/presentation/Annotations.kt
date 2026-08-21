package alexrnov.eitest.presentation

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Phone Portrait", showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp", locale = "ru")
@Preview(name = "Phone Landscape", showBackground = true, showSystemUi = true, device = "spec:width=891dp,height=411dp", locale = "ru")
@Preview(name = "Tablet Landscape", showBackground = true, showSystemUi = true, device = "spec:width=1280dp,height=800dp,dpi=240", locale = "ru")
@Preview(name = "Tablet Portrait", showBackground = true, showSystemUi = true, device = "spec:width=800dp,height=1280dp,dpi=240", locale = "ru")
annotation class LightThemePreviews

@Preview(name = "Phone Portrait - Night", showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
@Preview(name = "Phone Landscape - Night", showBackground = true, showSystemUi = true, device = "spec:width=891dp,height=411dp", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
@Preview(name = "Tablet Landscape - Night", showBackground = true, showSystemUi = true, device = "spec:width=1280dp,height=800dp,dpi=240", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
@Preview(name = "Tablet Portrait - Night", showBackground = true, showSystemUi = true, device = "spec:width=800dp,height=1280dp,dpi=240", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
annotation class DarkThemePreviews