package com.kenpugh.wordlehelpersmart

import BitArr3
import Match
import Word
import android.content.Context
import androidx.lifecycle.ViewModel
import computeMatchValues
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import output



class GameViewModel : ViewModel() {
    private var matchesComputed = false
    private var initialScreenShown = false
    private var currentAnswers = BitArr3()
    private var hardMode = false
    init {
        Game.load()
        output("game loaded")
        currentAnswers.setAll(Game.answers.size)
        output("matches maybe loaded")
    }
    fun setInitialScreenShown(){
        initialScreenShown = true
        updateState()
    }

    fun initalize(context : Context){
        loadMatches(context)
        firstGuesses()
        matchesComputed = true
        output(" ended match computation")
        updatedInitialized()


    }
    fun setHardMode(checked: Boolean)
    {
        hardMode = checked
        updateState()
    }
    fun getHardMode(): Boolean {
        return hardMode
    }
    private fun updateAnswerList() {
        answerList.clear()
        for (ac in 0 until currentAnswers.count()) {
            val a = currentAnswers.bitarray[ac]
            answerList.add(Game.answers.words[a])
        }
        if (answerList.size == 0)
            answerList.add(Word(""))
    }

     fun meetsHardMode(guess: Word): Boolean {
        val matchString = _uiState.value.getMatch(current_guess_index)
        val counts = calculateCharCounts(current_guess_word, Match(matchString))
        val equalWord = calculateEqualWord(current_guess_word, Match(matchString))
       val match =  matchHard(guess, counts, equalWord)
        output("Guess $guess matchstring $matchString equalWord $equalWord currentguess word $current_guess_word match $match")
        return match
    }

    private fun updateGuessList(guessIndices: List<Int>) {
        guessList.clear()
        for (i in guessIndices) {
            val guess = Game.guesses.words[i]
            guessList.add(guess)
            }
        if (guessList.size == 0)
            guessList.add(Word(""))
    }

    private fun loadMatches(context: Context) {
        if (matchesComputed)
            return
         output(context.toString())
//        if (context != null) {
            output(" started match computation")
            computeMatchValues(context)


//        }
    }

    private fun updatedInitialized() {
        val newSequence = _uiState.value.sequence + 1
        _uiState.update { currentState ->
            currentState.copy(sequence = newSequence, initialized = true)
        }  }

    private val MAX_GUESSES = 6
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
        " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " ",
        " ", " ", " ", " ", " "
    )
    private var current_guess_index = 0
    var current_guess_word = Word("     ")
    private var game_over = false
    fun setCurrentGuessWord(word: Word) {
        current_guess_word = word
        setCurrentGuess()
        updateState()
    }

    fun incrementGuessIndex() : Boolean{
          setCurrentGuess()

        if (Game.guesses.findIndex(current_guess_word) <0)
            return false
        findNextGuesses()
        if (!game_over) {
            current_guess_index++
            if (current_guess_index >= MAX_GUESSES) {
                current_guess_index--
                game_over = true
            }
        } else {
            resetGame()
        }
        updateState()
        return true
    }



    private fun findNextGuesses() {
        val guessIndex = Game.guesses.findIndex(current_guess_word)
        val matchString = _uiState.value.getMatch(current_guess_index)
        val matchIn = Match(matchString)
        val matchIndex = matchIn.getIndex()
        val matchForGuess = AllWordMatches.all_word_matches[guessIndex]
        val fromCurrentMatch = matchForGuess.getAnswersForMatch(matchIndex)
        currentAnswers = BitArr3.andAll(currentAnswers, fromCurrentMatch)
        firstGuesses()


    }

    private fun firstGuesses() {
        val matchString = _uiState.value.getMatch(current_guess_index)
        val counts = calculateCharCounts(current_guess_word, Match(matchString))
        val equalWord = calculateEqualWord(current_guess_word, Match(matchString))

        val guessIndices = AllWordMatches.determineNextGuessIndices(currentAnswers, hardMode,
            counts, equalWord)
        output("Bits " + (AllWordMatches.last_maximum) / 100)
        output("Answers remaining " + currentAnswers.count())
        updateGuessList(guessIndices)
        updateAnswerList()
    }

    fun resetGame() {
        game_over = false
        current_guess_index = 0
        val newStates= CharStates()
        for (i in enterGuessList.indices) {
            enterGuessList[i] = " "
        }
        val newSequence = _uiState.value.sequence + 1
        _uiState.update { currentState ->
            currentState.copy(states = newStates, sequence = newSequence)
        }
        currentAnswers.setAll(Game.answers.size)
        firstGuesses()
        current_guess_word = Word("     ")
    }

    private fun setCurrentGuess() {
        val startIndex = current_guess_index * 5
        for (i in 0..4) {
            enterGuessList[i + startIndex] = current_guess_word.charAt(i).toString()

        }
    }

    private fun updateState() {
        val newSequence = _uiState.value.sequence + 1
        _uiState.update { currentState ->
            currentState.copy(sequence = newSequence)
        }

    }
    fun indexInCurrentWord(index: Int): Boolean
    {
        val start = current_guess_index *5
        val stop = start + 5
        return index in start until stop
    }
    fun setState(it: Int, state: CharState) {
        if (!indexInCurrentWord(it))
            return
        val newSequence = _uiState.value.sequence + 1
        val newStates = _uiState.value.states
        newStates.setState(it, state)
        _uiState.update { currentState ->
            currentState.copy(states = newStates, sequence = newSequence)
        }
    }

//    fun getState(it: Int): CharState {
//        return _uiState.value.oneState
//    }
}
