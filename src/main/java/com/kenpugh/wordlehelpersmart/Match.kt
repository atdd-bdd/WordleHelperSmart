class Match(aValue: String) {
    private var value = Word("NNNNN")

    companion object {
//        fun get_match(indexIn: Int): String{
//            val inp = indexIn.toUByte()
//            return get_match(inp)
//        }
        fun get_match(indexIn: UByte): String {
            var index = indexIn.toInt()
            val values = arrayOf(1, 3, 9, 27, 81)
            val ret = Word("NNNNN")
            for (i in WORD_SIZE - 1 downTo 0) {
                val this_digit = index / values[i]
                ret.set(i, indexValue(this_digit))
                index -= values[i] * this_digit
            }
            //ret.set(0, indexValue(remainder))
            return ret.toString()
        }

        fun indexValue(index: Int): Char {
            return when (index) {
                0 -> NO_MATCH
                1 -> IN_WORD_MATCH
                2 -> EXACT_MATCH
                else -> 'X'
            }

        }

    }

    override fun toString(): String {
        return value.toString()
    }

    init {
        value.copy(aValue)
    }

    override fun hashCode(): Int {
        return 1
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other !is Match) return false
        return value == other.value
    }

    fun set(index: Int, aValue: Char) {
        value.set(index, aValue)
    }

    fun get(index: Int): Char {
        return value.charAt(index)
    }

    private fun matchValue(matchChar: Char): Int {
        return when (matchChar) {
            NO_MATCH -> 0
            IN_WORD_MATCH -> 1
            EXACT_MATCH -> 2
            else -> exitWithMessage("Match values char not found ")
        }
    }


    fun getIndex(): UByte {
        var multiplier = 1
        var ret = 0
        for (i in 0 until WORD_SIZE) {
            ret += multiplier * matchValue(value.charAt(i))
            multiplier *= 3
        }
        return ret.toUByte()
    }

}