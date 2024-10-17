package com.wearetriple.workshop.hilt

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wearetriple.workshop.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

data class HomeUiState(
    val messageText: Int = R.string.home_welcome_text,
    val buttonText: Int = R.string.home_timer_button,
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState()) // StateFlow for states
    val uiState = _uiState.asStateFlow()

    private var _navigateToTimer = MutableSharedFlow<Unit>() // SharedFlow for events
    val navigateToTimer = _navigateToTimer.asSharedFlow()

    /*
    Navigation can also be considered logic and could be moved to the viewmodel for various reasons
    such as testability, analytics tracking or other side effects.
     */
    fun navigateToTimer() {
        viewModelScope.launch {
            _navigateToTimer.emit(Unit)
        }
    }
}

@Composable
fun HomeScreen(
    onNavigateToTimer: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel() // `hiltViewModel()` automatically creates your scoped viewmodel
) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) { // Execute once, collect as long as composable lives
        // Will be called everytime `_navigateToTimer.emit(Unit)` is executed in the viewmodel
        viewModel.navigateToTimer.collectLatest { onNavigateToTimer() }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = stringResource(state.messageText))
        Button(
            onClick = viewModel::navigateToTimer // equivalent to `onClick = { viewModel.navigateToTimer() }`
        ) {
            Text(text = stringResource(state.buttonText))
        }
    }
}

data class TimerUiState(
    val seconds: Int = 0,
    val timerButtonText: Int = R.string.timer_button_start
)

@HiltViewModel
class TimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState()) // StateFlow for states
    val uiState = _uiState.asStateFlow()

    private var timerActive: Boolean = false

    fun onTimerButtonClicked() {
        if (timerActive) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        /*
        `viewModelScope.launch` will run on separate thread to prevent blocking the Main thread
        and therefore prevent freezing the screen while calling `delay()`
        */
        _uiState.update { state ->
            state.copy(
                seconds = 0, // Reset seconds
                timerButtonText = R.string.timer_button_stop // Change button text
            )
        }
        timerActive = true
        viewModelScope.launch {
            while (timerActive) {
                delay(timeMillis = 1000) // Delay 1 second
                _uiState.update { state ->
                    state.copy(seconds = state.seconds + 1) // Increment seconds
                }
            }
        }
    }

    private fun stopTimer() {
        timerActive = false
        _uiState.update { state ->
            state.copy(
                timerButtonText = R.string.timer_button_start // Change button text
            )
        }
    }
}

@Composable
fun TimerScreen(viewModel: TimerViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = state.seconds.toString())
        Button(
            onClick = viewModel::onTimerButtonClicked // equivalent to `onClick = { viewModel.onTimerButtonClicked() }`
        ) {
            Text(text = stringResource(state.timerButtonText))
        }
    }
}

/*
Create this class and reference it in your Manifest:
<application
    android:name=".MyApp"
    ...
>
 */
@HiltAndroidApp
class MyApp : Application()

@AndroidEntryPoint // Add this to the MainActivity in order for Hilt to magically inject your classes
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Serializable
data object Home

@Serializable
data object Timer

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Home,
    ) {
        composable<Home> {
            HomeScreen(
                onNavigateToTimer = { navController.navigate(Timer) }
            ) // ViewModel already injected as default argument by `hiltViewModel()`
        }
        composable<Timer> {
            TimerScreen() // ViewModel already injected as default argument by `hiltViewModel()`
        }
    }
}