package com.kenpugh.wordlehelpersmart

data class GameModelOld(

    val MAX_GUESS: Int = 6,
    val WORD_SIZE: Int = 5,
    var states: Array<Array<CharState>> = arrayOf(
            arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
            arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
            arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
            arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
            arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.EXACT, CharState.YES),
            arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.YES, CharState.EXACT),
        )
    ) {
        fun setState(guessIndex: Int, charIndex: Int) {
            val currentState = states[guessIndex][charIndex]

            val newState = when (currentState) {
                CharState.NO -> CharState.YES
                CharState.YES -> CharState.EXACT
                CharState.EXACT -> CharState.NO
            }
            states[guessIndex][charIndex] = newState
            return

        }
        fun clone(): GameModelOld {
            val states : Array<Array<CharState>> = arrayOf(
                arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
                arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
                arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
                arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.NO, CharState.NO),
                arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.EXACT, CharState.YES),
                arrayOf(CharState.NO, CharState.NO, CharState.NO, CharState.YES, CharState.EXACT),
            )

            for (i in 0..MAX_GUESS-1){
                for (j in 0..WORD_SIZE -1){
                    states[i][j] = this.states[i][j]
                }
            }
            var cloneModel = GameModelOld(this.MAX_GUESS, this.WORD_SIZE, states);
            return cloneModel;
        }
        fun getState(guessIndex: Int, charIndex: Int): CharState {
            return states[guessIndex][charIndex]
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameModelOld

        if (!states.contentDeepEquals(other.states)) return false

        return true
    }

    override fun hashCode(): Int {
        return states.contentDeepHashCode()
    }
}
