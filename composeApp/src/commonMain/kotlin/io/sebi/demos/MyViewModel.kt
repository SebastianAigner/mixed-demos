import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.matkovivan.nav_cupcake.CupcakeApp
import com.matkovivan.nav_cupcake.ui.theme.MyAppTheme
import cupcake.composeapp.generated.resources.Res
import cupcake.composeapp.generated.resources.cupcake
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

suspend fun saveToDatabase(s: String) {
    delay(500)
    println("Saved $s to database!")
}

class MyViewModel : ViewModel() {
    private val _savedEntryNumber = MutableStateFlow(0)
    val savedEntryNumber: StateFlow<Int> = _savedEntryNumber.asStateFlow()

    fun saveEntry(s: String) {
        viewModelScope.launch {
            saveToDatabase(s)
            _savedEntryNumber.update { it + 1 }
        }
    }
}