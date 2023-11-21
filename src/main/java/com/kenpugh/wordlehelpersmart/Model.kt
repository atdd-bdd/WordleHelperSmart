package com.kenpugh.wordlehelpersmart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Game UI state

class GameViewModel : ViewModel() {

    val uiState: StateFlow<Model> = MutableStateFlow(Model())


    data class Model(
        val MAX_GUESS: Int = 6,
        val WORD_SIZE: Int = 5,
        var states: Array<Array<State>> = arrayOf(
            arrayOf(State.NO, State.NO, State.NO, State.NO, State.NO),
            arrayOf(State.NO, State.NO, State.NO, State.NO, State.NO),
            arrayOf(State.NO, State.NO, State.NO, State.NO, State.NO),
            arrayOf(State.NO, State.NO, State.NO, State.NO, State.NO),
            arrayOf(State.NO, State.NO, State.NO, State.EXACT, State.YES),
            arrayOf(State.NO, State.NO, State.NO, State.YES, State.EXACT),
        )
    ) {
        fun setState(guessIndex: Int, charIndex: Int) {
            val currentState = states[guessIndex][charIndex]

            val newState = when (currentState) {
                State.NO -> State.YES
                State.YES -> State.EXACT
                State.EXACT -> State.NO
            }
            states[guessIndex][charIndex] = newState
            return

        }

        fun getState(guessIndex: Int, charIndex: Int): State {
            return states[guessIndex][charIndex]
        }
    }
}