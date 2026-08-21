package alexrnov.eitest

import alexrnov.eitest.data.dataModule
import alexrnov.eitest.domain.domainModule
import alexrnov.eitest.presentation.presentationModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EiTestApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@EiTestApplication) // передать applicationContext в Koin
			modules(
				presentationModule,
				domainModule,
				dataModule
			)
		}
	}
}