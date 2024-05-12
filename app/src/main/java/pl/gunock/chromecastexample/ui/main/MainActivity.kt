package pl.gunock.chromecastexample.ui.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.cast.framework.CastContext
import dagger.hilt.android.AndroidEntryPoint
import pl.gunock.chromecastexample.R
import pl.gunock.chromecastexample.cast.CustomMediaRouteActionProvider
import pl.gunock.chromecastexample.enums.MoveAction
import pl.gunock.chromecastexample.ui.theme.ChromecastExampleTheme

// TODO: Change to ComponentActivity after MediaRouter update
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<ComposeView>(R.id.compose_view).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ChromecastExampleTheme {
                    ContentView()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.menu_main, menu)

        // TODO: Migrate to composable when MediaRouter library is updated
        val castActionProvider =
            MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_cast)) as CustomMediaRouteActionProvider

        castActionProvider.routeSelector = CastContext.getSharedInstance()!!.mergedSelector!!

        return true
    }

}

@Composable
fun ContentView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleCommandView()
        Spacer(modifier = Modifier.height(20.dp))
        MoveCommandView()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ChromecastExampleTheme {
        ContentView()
    }
}


@Composable
fun TitleCommandView(
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    var text by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            placeholder = { Text(text = stringResource(id = R.string.main_activity_text_input_hint)) },
            onValueChange = { text = it })
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = {
            handleCommandResult(context) {
                viewModel.sendTitleCommand(text)
            }
        }) {
            Text(text = stringResource(R.string.main_activity_button_send))
        }
    }
}

@Composable
fun MoveCommandView(
    viewModel: MainViewModel = viewModel()
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current

            Button(modifier = Modifier.width(90.dp), onClick = {
                handleCommandResult(context) {
                    viewModel.sendMoveActionCommand(MoveAction.UP)
                }
            }) {
                Text(text = stringResource(R.string.main_activity_button_up))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(modifier = Modifier.width(90.dp), onClick = {
                    handleCommandResult(context) {
                        viewModel.sendMoveActionCommand(MoveAction.LEFT)
                    }
                }) {
                    Text(text = stringResource(R.string.main_activity_button_left))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(modifier = Modifier.width(90.dp), onClick = {
                    handleCommandResult(context) {
                        viewModel.sendMoveActionCommand(MoveAction.RIGHT)
                    }
                }) {
                    Text(text = stringResource(R.string.main_activity_button_right))
                }
            }

            Button(modifier = Modifier.width(90.dp), onClick = {
                handleCommandResult(context) {
                    viewModel.sendMoveActionCommand(MoveAction.DOWN)
                }
            }) {
                Text(text = stringResource(R.string.main_activity_button_down))
            }
        }
    }
}

private fun handleCommandResult(context: Context, command: () -> Boolean) {
    if (!command()) {
        Toast.makeText(context, R.string.chromecast_toast_no_session, Toast.LENGTH_SHORT).show()
    }

}
