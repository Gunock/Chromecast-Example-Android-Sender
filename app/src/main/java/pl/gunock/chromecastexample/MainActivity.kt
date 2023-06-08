package pl.gunock.chromecastexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import org.json.JSONObject
import pl.gunock.chromecastexample.constants.CastConstants
import pl.gunock.chromecastexample.enums.MoveAction
import pl.gunock.chromecastexample.ui.theme.ChromecastExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChromecastExampleTheme {
                ContentView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentView() {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(),
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_cast_24),
                        contentDescription = stringResource(id = R.string.menu_main_cast)
                    )
                }
            })
    }) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleCommandView()
            Spacer(modifier = Modifier.height(20.dp))
            MoveCommandView()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ChromecastExampleTheme {
        ContentView()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleCommandView() {
    var text by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(value = text, onValueChange = { text = it })
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { sendTitleCommand(text) }) {
            Text(text = stringResource(R.string.main_activity_button_send))
        }
    }
}

@Composable
fun MoveCommandView() {
    Card(
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier.width(90.dp), onClick = {
                sendMoveActionCommand(MoveAction.UP)
            }) {
                Text(text = stringResource(R.string.main_activity_button_up))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(modifier = Modifier.width(90.dp), onClick = {
                    sendMoveActionCommand(MoveAction.LEFT)
                }) {
                    Text(text = stringResource(R.string.main_activity_button_left))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(modifier = Modifier.width(90.dp), onClick = {
                    sendMoveActionCommand(MoveAction.RIGHT)
                }) {
                    Text(text = stringResource(R.string.main_activity_button_right))
                }
            }

            Button(modifier = Modifier.width(90.dp), onClick = {
                sendMoveActionCommand(MoveAction.DOWN)
            }) {
                Text(text = stringResource(R.string.main_activity_button_down))
            }
        }
    }
}

//override fun onCreateOptionsMenu(menu: Menu): Boolean {
//    menuInflater.inflate(R.menu.menu_main, menu)
//
//    val castActionProvider =
//        MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_cast)) as CustomMediaRouteActionProvider
//
//    castActionProvider.routeSelector = castContext.mergedSelector!!
//
//    return true
//}

private fun sendTitleCommand(text: String) {
    val session: CastSession =
        CastContext.getSharedInstance()?.sessionManager?.currentCastSession ?: return

    val messageJson: JSONObject = JSONObject().apply {
        put("text", text)
    }
    session.sendMessage(CastConstants.TITLE_NAMESPACE, messageJson.toString())
}

private fun sendMoveActionCommand(moveAction: MoveAction) {
    val session: CastSession =
        CastContext.getSharedInstance()?.sessionManager?.currentCastSession ?: return

    val messageJson: JSONObject = JSONObject().apply {
        put("action", moveAction.toString())
    }
    session.sendMessage(CastConstants.MOVE_NAMESPACE, messageJson.toString())
}
