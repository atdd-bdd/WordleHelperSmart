package com.kenpugh.wordlehelpersmart

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    val MAX_GUESSES = 6
    private val _uiState = MutableStateFlow(GameModel())
    var uiState: StateFlow<GameModel> = _uiState.asStateFlow()
    var guessList = mutableListOf(
        Word("One"), Word("Two"), Word("Three"), Word("Four"),
        Word("Five"), Word("Six"), Word("Seven"), Word("Eight"), Word("Nine")
    )
    var answerList = mutableListOf(
        Word("AOne"), Word("ATwp"), Word("AThree"),
        Word("AFour"), Word("AFive")
    )
    var enterGuessList = arrayOf(
        "a", "b", "c", "d", "e",
        "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o",
        "p", "q", "r", "s", "t",
        "u", "v", "w", "x", "y",
        "0", "1", "2", "3", "4"
    )
    var current_guess_index = 0
    var current_guess_word = Word("     ")
    var game_over = false
    fun setCurrentGuessWord(word: Word) {
        current_guess_word = word
        updateState()
    }

    fun incrementGuessIndex() {
        setCurrentGuess()
        if (!game_over) {
            current_guess_index++
            if (current_guess_index >= MAX_GUESSES) {
                current_guess_index--
                game_over = true
            }
        } else {
            resetGame()
        }
    }

    private fun resetGame() {
        game_over = false
        current_guess_index = 0
        val newStates= CharStates()
        for (i in 1..enterGuessList.size-1) {
            enterGuessList[i] = " "
        }
        val newSequence = _uiState.value.sequence + 1
        _uiState.update { currentState ->
            currentState.copy(states = newStates, sequence = newSequence)
        }
    }

    fun setCurrentGuess() {
        val startIndex = current_guess_index * 5
        for (i in 0..4) {
            enterGuessList[i + startIndex] = current_guess_word.charAt(i).toString()

        }
    }

    fun updateState() {
        val newSequence = _uiState.value.sequence + 1
        _uiState.update { currentState ->
            currentState.copy(sequence = newSequence)
        }

    }

    fun setState(it: Int, state: CharState) {
        val newSequence = _uiState.value.sequence + 1
        val newStates = _uiState.value.states
        newStates.setState(it, state)
        _uiState.update { currentState ->
            currentState.copy(states = newStates, sequence = newSequence)
        }
    }

    fun getState(it: Int): CharState {
        return _uiState.value.oneState
    }
}

class SignUpViewModel() : ViewModel() {

    var username by mutableStateOf("")
        private set

    fun updateUsername(input: String) {
        username = input
    }
}

// SignUpScreen.kt

@Composable
fun SignUpScreen(/*...*/) {

    OutlinedTextField(
        value = viewModel.username,
        onValueChange = { username -> viewModel.updateUsername(username) }
        /*...*/
    )
}


