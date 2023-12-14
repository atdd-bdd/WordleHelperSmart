package com.kenpugh.wordlehelpersmart

enum class CharState {NO, YES, EXACT}

fun nextCharState(inValue: CharState): CharState
{
    val outValue = when (inValue) {
    CharState.NO -> CharState.YES
    CharState.YES -> CharState.EXACT
    CharState.EXACT -> CharState.NO
}
    return outValue;
}
