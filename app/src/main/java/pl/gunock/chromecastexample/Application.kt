package pl.gunock.chromecastexample

import android.app.Application
import com.google.android.gms.cast.framework.CastContext
import java.util.concurrent.Executors

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initializes CastContext
        CastContext.getSharedInstance(applicationContext, Executors.newSingleThreadExecutor())
    }
}