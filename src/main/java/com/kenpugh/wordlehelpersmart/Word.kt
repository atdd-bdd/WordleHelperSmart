
class Word(initial :String)
{
    private var value = arrayOf('.','.','.','.','.')
     val wordSize = 5
    override fun hashCode(): Int {
        return 1
    }
    override fun equals(other: Any?): Boolean{
        if (other == null) return false
        if (this === other) return true
        if (other !is Word) return false
        return (value.contentEquals(other.value))
    }
    init {
        fill(initial)
    }
    private fun fill(source: String){
        var length = source.length
        if (source.length > wordSize)
            length = wordSize
        for (i in 0 until length){
            value[i] = source[i].uppercaseChar()
        }
        return
    }
    fun copy(source: String): Boolean{
        if (source.length < wordSize)
            return false
        for (i in 0 until wordSize){
            value[i] = source[i]
        }
        return true
    }
    fun charAt(index: Int): Char{
        return value[index]
    }
//    fun toUpper() {
//        for (i in 0 until WORD_SIZE) {
//            value[i] = value[i].uppercaseChar()
//        }
//    }

    fun set(index:Int, newValue : Char)
    {
        value[index] = newValue
    }
//    fun setAll(newValue: Char) {
//        for (i in 0 until WORD_SIZE) {
//            value[i] = newValue
//        }
//    }
    override fun toString(): String {
        var ret= ""
        for (i in 0 until wordSize) {
            ret += value[i]
        }
        return ret


    }

}