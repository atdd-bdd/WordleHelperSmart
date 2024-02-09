

    fun convertToWordArray(input: Array<String>): Array<Word> {
        val size = input.size
        val retArray = Array(size) { Word("ZZZZZ") }
        for (i in 0 until size) {
            retArray[i] = Word(input[i])
        }
        return retArray
    }

    fun inputWord(prompt: String): Word {
        var good = false
        var ret = Word("ZZZZZ")
        while (!good) {
            print(prompt)
            val input = readln()
            if (input.length == 5 && (Game.guesses.findIndex(Word(input)) >= 0 )) {
                ret = Word(input)
                good = true
            } else {
                output("Not a word: $input")
            }
        }
        return ret

    }

    fun inputMatch(prompt: String): Match {
        var good = false
        var ret = Word("ZZZZZ")
        var match = Match("NNNNN")
        while (!good) {
            print(prompt)
            val input = readln()
            if (input.length == 5) {
                ret = Word(input)
                match = Match(ret.toString())
                good = true
            } else {
                output("Not a match value: $input")
            }
        }
             return match

    }


    fun inputWordAndMatch(): Pair<Word, Match> {
        val guess = inputWord("Enter guess : ")
        val match = inputMatch("Enter match: ")
        return Pair(guess, match)
    }


    fun runTurnsStartingWithGuessAndAsk(guessFirst: Word) {
        var guess = guessFirst

        var currentAnswers = BitArr3()
        currentAnswers.setAll(Game.guesses.size)
        for (t in 0 until MAX_TRIES) {
            output("Suggested Guess: $guess")
            val (guessIn, matchIn) = inputWordAndMatch()
            output("Your Guess is $guessIn Match is $matchIn")
            var guessIndex = Game.guesses.findIndex(guessIn)
            val matchIndex = matchIn.getIndex()
            output("Guess index $guessIndex Match index $matchIndex")
            if (matchIn == Match("EEEEE")) {

                break
            }
            val matchForGuess = AllWordMatches.all_word_matches[guessIndex]
            val fromCurrentMatch = matchForGuess.getAnswersForMatch(matchIndex)
           currentAnswers = BitArr3.andAll(currentAnswers, fromCurrentMatch)
//        Timing t1;
            guessIndex = AllWordMatches.determineNextGuessIndex(currentAnswers)
            val guessIndices = AllWordMatches.determineNextGuessIndices(currentAnswers)
            val index = guessIndices[0]
            output("Guess index $guessIndex $index")
            //      t1.stop();
            guess = Game.guesses.words[guessIndex]
            output("Bits " + (AllWordMatches.last_maximum) / 100)
            output("Answers remaining " + currentAnswers.count())
            if (currentAnswers.count() <= 6)
                AllWordMatches.printCurrentAnswers(currentAnswers)
        }
        return
    }
    fun runTurnsWithArrays() {
        var currentAnswers = BitArr3()
        currentAnswers.setAll(Game.answers.size)
        for (t in 0 until MAX_TRIES) {
            val guessIndices = AllWordMatches.determineNextGuessIndices(currentAnswers)
            // Only give first one for the moment
//            var guessIndex  = guessIndices[0]
//            output("Guess index $guessIndex $guessIndex")
//            val guess = Game.guesses.words[guessIndex]
            output("Bits " + (AllWordMatches.last_maximum) / 100)
            output("Answers remaining " + currentAnswers.count())
            if (currentAnswers.count() <= 20)
                AllWordMatches.printCurrentAnswers(currentAnswers)

            output("Suggested Guesses")
            for (i in guessIndices) {
                outputNoLF(Game.guesses.words[i].toString() + " ")
            }
            output("")
            val (guessIn, matchIn) = inputWordAndMatch()
            output("Your Guess is $guessIn Match is $matchIn")
            if (matchIn == Match("EEEEE")) {

                break
            }
            var guessIndex = Game.guesses.findIndex(guessIn)
            val matchIndex = matchIn.getIndex()
            output("Guess index $guessIndex Match index $matchIndex")
            val matchForGuess = AllWordMatches.all_word_matches[guessIndex]
            val fromCurrentMatch = matchForGuess.getAnswersForMatch(matchIndex)
            currentAnswers = BitArr3.andAll(currentAnswers, fromCurrentMatch)
            guessIndex = AllWordMatches.determineNextGuessIndex(currentAnswers)
        }
        return
    }



