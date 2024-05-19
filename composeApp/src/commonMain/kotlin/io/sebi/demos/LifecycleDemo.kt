package io.sebi.demos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

val counterFlow = flow {
    var x = 0
    while (true) {
        emit(x++)
        delay(500)
    }
}

@Composable
fun LifecycleApp() {
    LifecycleLogger()
    val counter by counterFlow.collectAsStateWithLifecycle(
        initialValue = 0, // Show the overloads here
        minActiveState = Lifecycle.State.CREATED // CREATED even survives phone lock and background!
    )
    Box(Modifier.fillMaxSize(), contentAlignment = Center) {
        Column(horizontalAlignment = CenterHorizontally) {
            Text("I am an App, I have a lifecycle!")
            Text(counter.toString(), fontSize = 30.sp)
        }
    }
}