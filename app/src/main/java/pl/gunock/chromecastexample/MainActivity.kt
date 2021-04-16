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

    private lateinit var mTitleNamespace: String
    private lateinit var mMoveNamespace: String
    private lateinit var mCastContext: CastContext


    private lateinit var mTextInput: EditText

    private lateinit var mSendButton: Button
    private lateinit var mLeftButton: Button
    private lateinit var mRightButton: Button
    private lateinit var mUpButton: Button
    private lateinit var mDownButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))

        mTitleNamespace = getString(R.string.chromecast_title_namespace)
        mMoveNamespace = getString(R.string.chromecast_move_namespace)
        mCastContext = CastContext.getSharedInstance(applicationContext)

        mTextInput = findViewById(R.id.ed_text)

        mSendButton = findViewById(R.id.btn_send)
        mLeftButton = findViewById(R.id.btn_left)
        mRightButton = findViewById(R.id.btn_right)
        mUpButton = findViewById(R.id.btn_up)
        mDownButton = findViewById(R.id.btn_down)

        setupListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val castActionProvider =
            MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_cast)) as CustomMediaRouteActionProvider

        castActionProvider.routeSelector = mCastContext.mergedSelector

        return true
    }

    private fun setupListeners() {
        mSendButton.setOnClickListener {
            val session: CastSession = mCastContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            val text: String = mTextInput.text.toString()
            val messageJson: JSONObject = JSONObject().apply { put("text", text) }
            session.sendMessage(mTitleNamespace, messageJson.toString())
        }

        mLeftButton.setOnClickListener {
            val session: CastSession = mCastContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveAction(session, MoveAction.LEFT)
        }

        mRightButton.setOnClickListener {
            val session: CastSession = mCastContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveAction(session, MoveAction.RIGHT)
        }

        mUpButton.setOnClickListener {
            val session: CastSession = mCastContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveAction(session, MoveAction.UP)
        }

        mDownButton.setOnClickListener {
            val session: CastSession = mCastContext.sessionManager.currentCastSession
                ?: return@setOnClickListener

            sendMoveAction(session, MoveAction.DOWN)
        }
    }

    private fun sendMoveAction(session: CastSession, moveAction: MoveAction) {
        val messageJson: JSONObject = JSONObject().apply { put("action", moveAction.toString()) }
        session.sendMessage(mMoveNamespace, messageJson.toString())
    }

}