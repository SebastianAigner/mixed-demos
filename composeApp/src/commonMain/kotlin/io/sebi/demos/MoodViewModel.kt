import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.sebi.demos.Repository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _changeCount = MutableStateFlow(0)
    val changeCount: StateFlow<Int> = _changeCount

    fun storeChange(kodeeMood: String) {
        viewModelScope.launch {
            Repository.save(kodeeMood)
            _changeCount.update { it + 1 }
        }
    }

    override fun onCleared() {
        println("Cleaning up and releasing resources!")
        super.onCleared()
    }
}