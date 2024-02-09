

//    fun convertToWordArray(input: Array<String>): Array<Word> {
//        val size = input.size
//        val retArray = Array(size) { Word("ZZZZZ") }
//        for (i in 0 until size) {
//            retArray[i] = Word(input[i])
//        }
//        return retArray
//    }



//    fun runTurnsWithArrays() {
//        var currentAnswers = BitArr3()
//        currentAnswers.setAll(Game.answers.size)
//        for (t in 0 until MAX_TRIES) {
//            val guessIndices = AllWordMatches.determineNextGuessIndices(currentAnswers)
//            // Only give first one for the moment
////            var guessIndex  = guessIndices[0]
////            output("Guess index $guessIndex $guessIndex")
////            val guess = Game.guesses.words[guessIndex]
//            output("Bits " + (AllWordMatches.last_maximum) / 100)
//            output("Answers remaining " + currentAnswers.count())
//            if (currentAnswers.count() <= 20)
//                AllWordMatches.printCurrentAnswers(currentAnswers)
//
//            output("Suggested Guesses")
//            for (i in guessIndices) {
//                outputNoLF(Game.guesses.words[i].toString() + " ")
//            }
//            output("")
//            val (guessIn, matchIn) = inputWordAndMatch()
//            output("Your Guess is $guessIn Match is $matchIn")
//            if (matchIn == Match("EEEEE")) {
//
//                break
//            }
//            var guessIndex = Game.guesses.findIndex(guessIn)
//            val matchIndex = matchIn.getIndex()
//            output("Guess index $guessIndex Match index $matchIndex")
//            val matchForGuess = AllWordMatches.all_word_matches[guessIndex]
//            val fromCurrentMatch = matchForGuess.getAnswersForMatch(matchIndex)
//            currentAnswers = BitArr3.andAll(currentAnswers, fromCurrentMatch)
//        }
//        return
//    }



