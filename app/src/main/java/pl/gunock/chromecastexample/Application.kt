package pl.gunock.chromecastexample

import android.app.Application
import com.google.android.gms.cast.framework.CastContext
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executors

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        // Initializes CastContext
        CastContext.getSharedInstance(applicationContext, Executors.newSingleThreadExecutor())
    }
}