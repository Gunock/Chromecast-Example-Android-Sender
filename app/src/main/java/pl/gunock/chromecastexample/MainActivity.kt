package pl.gunock.chromecastexample

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import org.json.JSONObject
import pl.gunock.chromecastexample.cast.CustomMediaRouteActionProvider
import pl.gunock.chromecastexample.enums.MoveAction

class MainActivity : AppCompatActivity() {

    private lateinit var titleNamespace: String
    private lateinit var moveNamespace: String
    private lateinit var castContext: CastContext


    private lateinit var textInput: EditText

    private lateinit var sendButton: Button
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var upButton: Button
    private lateinit var downButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))

        titleNamespace = getString(R.string.chromecast_title_namespace)
        moveNamespace = getString(R.string.chromecast_move_namespace)
        castContext = CastContext.getSharedInstance()!!

        textInput = findViewById(R.id.ed_text)

        sendButton = findViewById(R.id.btn_send)
        leftButton = findViewById(R.id.btn_left)
        rightButton = findViewById(R.id.btn_right)
        upButton = findViewById(R.id.btn_up)
        downButton = findViewById(R.id.btn_down)

        setupListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val castActionProvider =
            MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_cast)) as CustomMediaRouteActionProvider

        castActionProvider.routeSelector = castContext.mergedSelector!!

        return true
    }

    private fun setupListeners() {
        sendButton.setOnClickListener {
            val session: CastSession = castContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            val text: String = textInput.text.toString()
            val messageJson: JSONObject = JSONObject().apply { put("text", text) }
            session.sendMessage(titleNamespace, messageJson.toString())
        }

        leftButton.setOnClickListener {
            val session: CastSession = castContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveActionCommand(session, MoveAction.LEFT)
        }

        rightButton.setOnClickListener {
            val session: CastSession = castContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveActionCommand(session, MoveAction.RIGHT)
        }

        upButton.setOnClickListener {
            val session: CastSession = castContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveActionCommand(session, MoveAction.UP)
        }

        downButton.setOnClickListener {
            val session: CastSession = castContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveActionCommand(session, MoveAction.DOWN)
        }
    }

    private fun sendMoveActionCommand(session: CastSession, moveAction: MoveAction) {
        val messageJson: JSONObject = JSONObject().apply {
            put("action", moveAction.toString())
        }
        session.sendMessage(moveNamespace, messageJson.toString())
    }

}