import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ViewModelDemoApp() {
    val vm = viewModel { KodeeViewModel() }
    val changeMood = vm::storeChange
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Current mood?")
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