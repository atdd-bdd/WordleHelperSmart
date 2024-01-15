package com.kenpugh.wordlehelpersmart

class Word constructor (initial :String)
{
    private var value = arrayOf('Z','Z','Z','Z','Z')
    private val WORD_SIZE = 5
    init {
        fill(initial)
    }
    fun fill(source: String): Boolean{
        var length = source.length
        if (source.length > WORD_SIZE)
           length = WORD_SIZE
        for (i in 0..length -1){
            value[i] = source.get(i)
        }
        return true;
    }
    fun copy(source: String): Boolean{
        if (source.length < WORD_SIZE)
            return false;
        for (i in 0..WORD_SIZE -1){
            value[i] = source.get(i)
        }
        return true;
    }
    fun charAt(index: Int): Char{
        return value[index]
    }
    fun toUpper() {
        for (i in 0..WORD_SIZE - 1) {
            value[i] = value[i].uppercaseChar()
        }
    }

    fun set(index:Int, newValue : Char)
    {
        value[index] = newValue
    }
    fun setAll(newValue: Char) {
        for (i in 0..WORD_SIZE - 1) {
            value[i] = newValue;
        }
    }
    override fun toString(): String {
        var ret= ""
        for (i in 0..WORD_SIZE - 1) {
            ret += value[i]
        }
        return ret;


    }

}

