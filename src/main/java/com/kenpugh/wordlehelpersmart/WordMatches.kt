@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalUnsignedTypes::class, ExperimentalUnsignedTypes::class)


class WordMatches {

    var matchesForAnswers = UByteArray(MAX_ANSWERS)


     fun computeScoreForMatches(counts: IntArray): Int {

        var totalMatches = 0

        for (count in counts) {
 //           print("$count ")
            if (count > 0) {
                totalMatches += count
            }
        }
 //       output()
 //       output("Total matches $totalMatches")
        var total = 0.0
        // bits =  log2(1/percentage)  1/.11)  10
        for (count in counts) {
            if (count > 0) {
                val percentInverse = ((100 * totalMatches) / count)
                val value = LogTwo.log2t100(percentInverse)
                val percent = count.toDouble() / totalMatches
                total += percent * value
                //output("percentInverse $percentInverse value $value percent $percent Total $total")
            }
        }
        return total.toInt()
    }

    fun set(match: Match, answerIndex: Int) {
        if (answerIndex >= MAX_ANSWERS) {
            exitWithMessage("Answer index out of bounds")
            return
        }
        val matchIndex = match.getIndex()
        if (matchIndex >= NUMBER_MATCHES.toUByte()) {
            exitWithMessage("match index out of bounds")
            return
        }
        matchesForAnswers[answerIndex] = matchIndex

    }

   fun printMatches() {
       for (mi in 0 until NUMBER_MATCHES) {
           val m = mi.toUByte()
           val s = Match.get_match(m)
           outputNoLF("$s ")
           var count = 0
           for (a in 0 until Game.answers.size) {
               if (matchesForAnswers[a] == m) {
                   val w = Game.answers.words[a]
                   outputNoLF(" $w")
                   count++
               }
           }
           output("Count $count")
       }
   }
//    fun analyze(): Boolean {
//        var counts = IntArray(NUMBER_MATCHES.toInt()) { 0 }
//        for (a in 0..Game.answers.size-1) {
//            val m  = matches_for_answers[a].toInt()
//            counts[m]++
//        }
//        var has_a_one: Boolean = false
//        for (m in 0..NUMBER_MATCHES-1) {
//            if (counts[m] == 1) {
//                has_a_one = true
//            }
//        }
//        return has_a_one
//
//    }

    fun determineAverage(currentAnswers: BitArr3): Pair<Double, Int> {
        var containsGuess = 0    // If one of the matches is EEEEE
        val counts = IntArray(NUMBER_MATCHES) { 0 }
        val sizeCurrentAnswers = currentAnswers.count()
        for (ac in 0 until sizeCurrentAnswers) {
            val a = currentAnswers.bitarray[ac]
            val m = matchesForAnswers[a].toInt()
            counts[m]++
        }

        val average = computeScoreForMatches(counts)

         if (counts[INDEX_ALL_EXACT] == 1)  // this guess could be an answer
            containsGuess = 1
        return Pair(average.toDouble(), containsGuess)
    }


    fun getAnswersForMatch(matchIndex: UByte): BitArr3 {
        val ret = BitArr3()
        ret.clearAll()
        output("Size is ")
        output(Game.answers.size.toString())
        for (a in 0 until Game.answers.size) {

            if (matchesForAnswers[a] == matchIndex) {
                ret.set(a)
            }
        }
        return ret
    }
}

