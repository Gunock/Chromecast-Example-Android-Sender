package pl.gunock.chromecastexample.ui.main

import androidx.lifecycle.ViewModel
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import pl.gunock.chromecastexample.constants.CastConstants
import pl.gunock.chromecastexample.enums.MoveAction
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    fun sendTitleCommand(text: String): Boolean {
        val session: CastSession = getCastSession() ?: return false

        val messageJson: JSONObject = JSONObject().apply {
            put("text", text)
        }
        session.sendMessage(CastConstants.TITLE_NAMESPACE, messageJson.toString())
        return true
    }

    fun sendMoveActionCommand(moveAction: MoveAction): Boolean {
        val session: CastSession = getCastSession() ?: return false

        val messageJson: JSONObject = JSONObject().apply {
            put("action", moveAction.toString())
        }
        session.sendMessage(CastConstants.MOVE_NAMESPACE, messageJson.toString())
        return true
    }

    private fun getCastSession(): CastSession? {
        return CastContext.getSharedInstance()?.sessionManager?.currentCastSession
    }
}