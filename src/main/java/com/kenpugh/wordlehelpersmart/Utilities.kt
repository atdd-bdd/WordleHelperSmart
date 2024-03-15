import kotlin.system.exitProcess

@Suppress("UNREACHABLE_CODE")
fun exitWithMessage(message: String): Int {
    output(message)
    exitProcess(-1)
    return -1
}





fun charToIndex(value: Char) : Int {
    val ret = value - 'A'.code
    return ret.code
}
fun index_to_char(index: Int): Char {
    val ret = index + 'A'.code
    return ret.toChar()
}
fun matchWords(guess: Word, answer: Word ): Match {
    val ret = Match("NNNNN")
     val charCount = IntArray(SIZE_ALPHABET)
    for (i in 0 until WORD_SIZE){
         val index = charToIndex(answer.charAt(i))
        charCount[index]++
    }
    for (i in 0 until WORD_SIZE){

        if (guess.charAt(i) == answer.charAt(i)) {
            ret.set(i, EXACT_MATCH)
            val index = charToIndex(guess.charAt(i))
            charCount[index]--
        }
    }
    for (i in 0 until WORD_SIZE){
          if (ret.get(i) != EXACT_MATCH) {
            val index = charToIndex(guess.charAt(i))
            if (charCount[index] > 0) {
                charCount[index]--
                ret.set(i, 'Y')
            }
        }
    }

// Add in additional logic
    return ret
}


