package pl.gunock.chromecastexample.cast

import android.content.Context
import android.os.Bundle
import androidx.mediarouter.app.MediaRouteControllerDialog
import androidx.mediarouter.app.MediaRouteControllerDialogFragment

class CustomMediaRouteControllerDialogFragment : MediaRouteControllerDialogFragment() {
    override fun onCreateControllerDialog(
        context: Context,
        savedInstanceState: Bundle?
    ): MediaRouteControllerDialog {
        val dialog = super.onCreateControllerDialog(context, savedInstanceState)
        dialog.isVolumeControlEnabled = false
        dialog.setTitle("")
        return dialog
    }
}