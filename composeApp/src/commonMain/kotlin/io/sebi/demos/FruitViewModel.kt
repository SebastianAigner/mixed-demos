package io.sebi.demos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FruitViewModel() : ViewModel() {
    private val _grownFruitCount = MutableStateFlow(0)
    val grownFruitCount: StateFlow<Int> = _grownFruitCount.asStateFlow()

    private var growFruitJob: Job? = null
    private var fruit = "No fruit"

    fun startGrowing(fruit: String) {
        if (growFruitJob != null) return
        this.fruit = fruit
        growFruitJob = viewModelScope.launch {
            blockPrint("[now growing $fruit]")
            while (true) {
                delay(1000)
                blockPrint(fruit)
                _grownFruitCount.update { it + 1 }
            }
        }
    }

    override fun onCleared() {
        println("Cleared ViewModel growing $fruit")
        super.onCleared()
    }
}
