package io.sebi.demos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.eventFlow
import kotlinx.coroutines.launch

@Composable
fun LifecycleLogger() {
    val lc = LocalLifecycleOwner.current
    LaunchedEffect(lc) {
        launch {
            lc.lifecycle.currentStateFlow.collect {
                println("New State: $it")
            }
        }
        launch {
            lc.lifecycle.eventFlow.collect {
                println("New Event: $it")
            }
        }
    }
}