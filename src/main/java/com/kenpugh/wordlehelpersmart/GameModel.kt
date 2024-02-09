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
    fun getMatch(index : Int) : String {
        var start = index * 5
        var ret = ""
        for (i in start.until(start+5 )) {
            val c =charToString(getState(i))
            ret += c
        }
        return ret
    }
}
data class GameModel (val oneState: CharState = CharState.NO, val states: CharStates = CharStates(),
    val sequence: Int= 0, val initialized : Boolean = false)
    {
    fun setState(index : Int, state: CharState){
        this.states.setState(index, state)
    }

    fun nextState(index : Int){
        this.states.nextState(index)
    }

        fun getMatch(index : Int) : String {
            return states.getMatch(index)
        }
}