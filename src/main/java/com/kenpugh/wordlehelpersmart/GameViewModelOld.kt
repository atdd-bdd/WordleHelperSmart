package com.kenpugh.wordlehelpersmart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Game UI state

class GameViewModelOld : ViewModel() {


    private val _uiState = MutableStateFlow(GameModelOld())
    var uiState: StateFlow<GameModelOld> = _uiState

     fun setState(guessIndex: Int, charIndex: Int) {
         val gameState = _uiState.value.clone()
         gameState.setState(guessIndex, charIndex)
         _uiState.update { currentState ->
             currentState.copy(states=gameState.states)
         }
     }
    fun getState(guessIndex: Int, charIndex: Int): CharState {
        return _uiState.value.getState(guessIndex, charIndex)
    }


}


