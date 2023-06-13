package pl.gunock.chromecastexample.cast

import android.content.Context
import android.view.View
import androidx.mediarouter.app.MediaRouteActionProvider
import androidx.mediarouter.app.MediaRouteButton
import androidx.mediarouter.app.MediaRouteDialogFactory

class CustomMediaRouteActionProvider(context: Context) : MediaRouteActionProvider(context) {

    private var factory: MediaRouteDialogFactory = CustomMediaRouteDialogFactory()

    init {
        setAlwaysVisible(true)
    }

    override fun onCreateActionView(): View {
        val castButton = super.onCreateActionView() as MediaRouteButton
        castButton.dialogFactory = dialogFactory
        return castButton
    }

    override fun getDialogFactory(): MediaRouteDialogFactory {
        return factory
    }

    override fun setDialogFactory(factory: MediaRouteDialogFactory) {
        this.factory = factory
    }

}