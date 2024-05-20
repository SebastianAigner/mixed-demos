import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ViewModelDemoApp() {

    val changeMood: (String) -> Unit = {}
    val storedMoodChanges = 0

    MaterialTheme {
        Column(
            Modifier
                .fillMaxSize()
                .windowInsetsBottomHeight(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Current mood?")
            Text("Stored $storedMoodChanges mood changes")
            Button(onClick = { changeMood("happy") }) {
                Text("Happy")
            }
            Button(onClick = { changeMood("neutral") }) {
                Text("Neutral")
            }
            Button(onClick = { changeMood("sad") }) {
                Text("Sad")
            }
        }
    }
}