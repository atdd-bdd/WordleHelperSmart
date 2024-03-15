package com.kenpugh.wordlehelpersmart

import EXACT_MATCH
import IN_WORD_MATCH
import Match
import Word
import charToIndex
import index_to_char
import output


private fun checkTheMatch(guess: Word, match: Match) {
    var guess1 = guess
    var match1 = match
    output("************************")
    guess1 = Word("ABCDE")
    match1 = Match("YNENY")
    val counts = calculateCharCounts(guess1, match1)
    val equalWord = calculateEqualWord(guess1, match1)
    output("Equal word $equalWord")
    output("Counts $counts")
    var result = matchHard(Word("BACED"), counts, equalWord)
    output("Result is $result")
    result = matchHard(Word("BADEC"), counts, equalWord)
    output("Result is $result")
}

fun matchHard(guess : Word, counts: Array<Int>, equalWord : Word ): Boolean {
    var isCount = matchCounts(guess, counts)
//    output("Is Count $isCount")
    var isWord =matchEqualWord(guess, equalWord)
//    output("Is equal $isWord ")
    return isCount && isWord

}

fun matchCounts(guess: Word, counts: Array<Int>): Boolean {
    val thisCounts = calculateCharCounts(guess, Match("YYYYY"))
    var ret = true
//    printCounts(thisCounts)
    for (i in 0 until counts.size){
        if (counts[i] > 0)
            if (counts[i] != thisCounts[i])
                ret = false
    }
    return ret
}

fun matchEqualWord(guess: Word, wordEqual : Word) : Boolean {
    var ret = true
    for (i in 0 until guess.wordSize)
    {
        val e = wordEqual.charAt(i)
        val c = guess.charAt(i)
        if (e != ' ' && e != c)
            ret = false
    }
    return ret
}
private fun doOne(guess: Word, match: Match) {
    val counts = calculateCharCounts(guess, match)
    val equalWord = calculateEqualWord(guess, match)
    output("Equal word $equalWord")
    output("Counts $counts")
    printCounts(counts)
}

fun printCounts(counts: Array<Int>){
    for (i in 0 until counts.size){
        val c = index_to_char(i)
        val count = counts[i]
        output("c $c is $count")
    }

}
fun calculateEqualWord(guess: Word, match : Match) : Word {
    var equalWord = Word("     " )
    for (i in 0 until guess.wordSize)
    {
        val m =match.get(i)
        val c = guess.charAt(i)
        if (m == EXACT_MATCH){
            equalWord.set(i, c)
        }
    }
    return equalWord
}
fun calculateCharCounts(guess: Word, match: Match) : Array<Int> {
    val ret = Array<Int>(26){0}
    for (i in 0 until guess.wordSize)
    {
        val m =match.get(i)
        val c = guess.charAt(i)
        if (m == IN_WORD_MATCH || m == EXACT_MATCH){
            val i = charToIndex(c)
            ret[i]++
        }
    }

    return ret
}