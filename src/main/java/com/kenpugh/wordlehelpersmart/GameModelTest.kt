package com.kenpugh.wordlehelpersmart

data class CharStates (
    val data: Array<CharState> =
        arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO,
            CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO,
            CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO,
            CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO,
            CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO,
            CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO,)
){
    fun setState(index : Int, state: CharState){
        data[index] = state;
    }
    fun getState(index : Int) : CharState {
        return data[index]
    }
    fun nextState(index : Int){
        data[index] = nextCharState(data[index])
    }
    fun clone() : CharStates {
        return CharStates(this.data.clone())
    }
}
data class GameModelTest (val oneState: CharState = CharState.NO, val states: CharStates = CharStates(), val sequence: Int= 0)
    {
    fun setState(index : Int, state: CharState){
        this.states.setState(index, state)
    }

    fun nextState(index : Int){
        this.states.nextState(index)
    }
}