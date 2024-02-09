package com.kenpugh.wordlehelpersmart

import EXACT_MATCH
import IN_WORD_MATCH
import NO_MATCH


enum class CharState {NO, YES, EXACT}

fun nextCharState(inValue: CharState): CharState
{
    val outValue = when (inValue) {
    CharState.NO -> CharState.YES
    CharState.YES -> CharState.EXACT
    CharState.EXACT -> CharState.NO
}
    return outValue
}
fun charToString(invalue:CharState): Char {
    val outVal = when(invalue) {
        CharState.NO -> NO_MATCH
        CharState.YES -> IN_WORD_MATCH
        CharState.EXACT -> EXACT_MATCH
    }
    return outVal

    }

