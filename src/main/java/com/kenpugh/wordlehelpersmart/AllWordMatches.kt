import com.kenpugh.wordlehelpersmart.matchHard

class AllWordMatches {
    companion object {
        var all_word_matches = Array(MAX_GUESSES) { WordMatches() }

        private var average = DoubleArray(MAX_GUESSES)
        private var contains_guess = IntArray(MAX_GUESSES)
        var last_maximum = 0.0

//        fun print_all_matches() {
//            for (g in 0..Game.guesses.size-1) {
//                print("Matches for guess ")
//                print(Game.guesses.words[g])
//                all_word_matches[g].printMatches()
//            }
//        }

//        fun printCurrentAnswers(currentAnswers: BitArr3) {
//            for (ac in 0 until currentAnswers.count()) {
//                val a = currentAnswers.bitarray[ac]
//                outputNoLF(" ")
//                outputNoLF(Game.answers.words[a].toString())
//            }
//            output("")
//        }


        fun determineNextGuessIndex(currentAnswers: BitArr3): Int {
            average.fill(0.0)
            contains_guess.fill(0)
            for (g in 0 until Game.guesses.size) {
                val (avg, cg)  = all_word_matches[g].determineAverage(currentAnswers)
                average[g] = avg
                contains_guess[g] = cg
            }
            var maximum: Double = NO_MATCHES_HERE.toDouble()
            var index = 0
            for (g in 0 until Game.guesses.size) {
                if (average[g] > maximum) {
                    maximum = average[g]
                }
            }
            last_maximum = maximum
            for (g in 0 until Game.guesses.size) {
                if (average[g] == maximum) {
                    index = g
                    if (contains_guess[g] == 1) {
                        break
                    }
                }
            }
            return index

        }

        fun determineNextGuessIndices(currentAnswers: BitArr3, hardMode: Boolean, counts: Array<Int>, equalWord: Word): List<Int> {
            val guessIndices: MutableList<Int> = mutableListOf()
            val guessAverages = Array<Triple<Int, Double, Int>>(Game.guesses.size){Triple(0,0.0, 0)}
            average.fill(0.0)
            contains_guess.fill(0)
            for (g in 0 until Game.guesses.size) {
                val (avg, cg)  = all_word_matches[g].determineAverage(currentAnswers)
                average[g] = avg
                contains_guess[g] = cg
                guessAverages[g]=Triple(g, avg, cg)
                  }
            guessAverages.sortWith(Comparator
               { s1: Triple<Int,Double, Int>, s2: Triple<Int,Double, Int>->
                   ((s2.second+s2.third) - (s1.second+s1.third)).toInt() })
            printaverage(guessAverages)
            last_maximum = guessAverages[0].second
            // may want to alter if contains_guess is a 1
            var maxGuesses = 100
            if (!hardMode)
            {
            for (i in 0 until maxGuesses){
               if (guessAverages[i].second > 0 || guessAverages[i].third > 0)
                    guessIndices.add(guessAverages[i].first)

            }}
            else
            {
                var hardModeCount = 0
                for (i in 0 until guessAverages.size)
                {
                    val guessIndex = guessAverages[i].first
                    if (guessAverages[i].second > 0 || guessAverages[i].third > 0)
                    {
                        val guess = Game.guesses.words[guessIndex]
                        val match =  matchHard(guess, counts, equalWord)
                        output("Guess $guess equalWord $equalWord match $match")
                        if (match)
                        {
                            hardModeCount++
                            guessIndices.add(guessIndex)
                            if (hardModeCount >= maxGuesses)
                                break
                        }
                    }
                }
            }
            return guessIndices
       }

        private fun printaverage(guessAverages: Array<Triple<Int, Double, Int>>) {
            for (i in 0..300) {
                val index = guessAverages[i].first
                val x = Game.guesses.words[index].toString()
                val avg = guessAverages[i].second
                val inanswers = guessAverages[i].third
                output("$x bits $avg index $index  $inanswers answers" +
                        "")
            }
        }


        fun initializeTheWordMatches() {
            for (g in 0 until Game.guesses.size) {
                for (a in 0 until Game.answers.size) {
                    val m = matchWords(Game.guesses.words[g], Game.answers.words[a])
                    all_word_matches[g].set(m, a)
                }
            }
        }
    }
}
//    void AllWordMatches::analyze_matches() {
//        cout << "Analyzing matches \n"
//        for (int g = 0 g < Game::guesses.size g++) {
//            bool has_one = all_word_matches[g].analyze()
//            if (!has_one) {
//                cout << " Guess " << (string)Game::guesses.words[g] << " not have a one match "
//            }
//        }
//    }






