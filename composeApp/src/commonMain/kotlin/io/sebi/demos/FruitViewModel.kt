package io.sebi.demos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FruitViewModel() : ViewModel() {
    private val _grownFruitCount = MutableStateFlow(0)
    val grownFruitCount: StateFlow<Int> = _grownFruitCount.asStateFlow()
    
    private val isGrowing = MutableStateFlow(false)

    fun startGrowing(fruit: String) {
        val shouldStartGrowing = isGrowing.compareAndSet(expect = false, update = true)
        if(!shouldStartGrowing) return
        viewModelScope.launch {
            blockPrint("[now growing $fruit]")
            while (true) {
                delay(1000)
                blockPrint(fruit)
                _grownFruitCount.update { it + 1 }
            }
        }
    }
}
