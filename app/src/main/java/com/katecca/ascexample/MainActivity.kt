package com.katecca.ascexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.katecca.ascexample.service.SocketChannelConnectService
import com.katecca.ascexample.style.AppTheme
import com.katecca.ascexample.style.textFieldStyle1
import com.katecca.ascexample.style.textFieldStyle2
import com.katecca.ascexample.ui.CmdButton
import com.katecca.ascexample.ui.ResultPanel
import com.katecca.ascexample.ui.TextField
import com.katecca.ascexample.viewmodel.ConnectBtnViewModel
import com.katecca.ascexample.viewmodel.ResultPanelViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private val sccs = SocketChannelConnectService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HomeScreen()
    }

    @Composable
    fun HomeScreen() {
        AppTheme { // We create a "AppTheme" composable to style the app
            Scaffold( // A layout for us to add TopBar directly
                topBar = {
                    TopAppBar(title = {
                        Text(text = stringResource(R.string.app_name))
                    })
                 },
                content = {
                    BodyContent()
                }
            )
        }
    }

    @Composable
    fun BodyContent() {

        /*
        Use mutableStateOf to creates an observable MutableState<T>,
        which is an observable type integrated with the compose runtime
        */
        val ipAddrTextState = remember { mutableStateOf(TextFieldValue()) }
        val ipPortTextState = remember { mutableStateOf(TextFieldValue()) }
        val msgTextState = remember { mutableStateOf(TextFieldValue()) }

        // Create viewModel to maintain the view state
        val resultPanelViewModel: ResultPanelViewModel by viewModels()
        val connectBtnViewModel: ConnectBtnViewModel by viewModels()

        /*
        name/enabled is the _current_ value of
        [connectBtnViewModel.name]/[connectBtnViewModel.enabled]
        with an initial value of "Connect"/true

        .observeAsState observes a LiveData<T> and converts it into a State<T> object so Compose
        can react to value changes

        by is the property delegate syntax in Kotlin, it lets us automatically unwrap the
        State<String/Boolean> from observeAsState into a regular String/Boolean
         */
        val name: String by connectBtnViewModel.name.observeAsState(stringResource(R.string.btn_connect))
        val enabled: Boolean by connectBtnViewModel.enabled.observeAsState(true)
        //Use Column to achieve a similar layout as LinearLayout (Vertical)
        Column(Modifier.padding(5.dp).fillMaxWidth()) {
            TextField(stringResource(R.string.tf_ip_addr), ipAddrTextState, textFieldStyle1)
            TextField(stringResource(R.string.tf_ip_port), ipPortTextState, textFieldStyle1)

            //Use Row to achieve a similar layout as LinearLayout (Horizontal)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CmdButton(name, enabled){
                    GlobalScope.launch{
                        sccs.connect(ipAddrTextState.value.text, ipPortTextState.value.text.toInt(),
                            resultPanelViewModel, connectBtnViewModel)
                    }
                }
                CmdButton(stringResource(R.string.btn_disconnect)){
                    GlobalScope.launch{
                        sccs.disconnect(connectBtnViewModel)
                    }
                }
                CmdButton(stringResource(R.string.btn_clear)) { resultPanelViewModel.clear() }
            }

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically){
                TextField(stringResource(R.string.tf_msg), msgTextState, textFieldStyle2)
                CmdButton(stringResource(R.string.btn_send)){
                    GlobalScope.launch {
                        sccs.startWrite(msgTextState.value.text)
                    }
                }
            }

            Column(Modifier.verticalScroll(rememberScrollState())) {
                ResultPanel(resultPanelViewModel)
            }
        }
    }
}