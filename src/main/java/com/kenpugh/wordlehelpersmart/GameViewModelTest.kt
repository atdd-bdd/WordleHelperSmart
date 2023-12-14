package com.kenpugh.wordlehelpersmart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModelTest : ViewModel() {


    private val _uiState = MutableStateFlow(GameModelTest())
    var uiState: StateFlow<GameModelTest> = _uiState.asStateFlow()
    var gueessList  = listOf("One", "Two", "Three", "Four", "Five")
    fun setState(it: Int, state: CharState) {
        val newSequence = _uiState.value.sequence+ 1
        val newStates = _uiState.value.states
        newStates.setState(it, state)
        _uiState.update { currentState ->
            currentState.copy(states = newStates, sequence = newSequence);
        }
    }

    fun getState(it: Int): CharState {
        return _uiState.value.oneState
    }
}


