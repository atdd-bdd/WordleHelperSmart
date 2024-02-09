


class Words {
    val words = Array(MAX_WORDS) {Word("ZZZZZ")}
    var size =  0

    fun load(filename: String) {
        if (filename == ANSWER_FILENAME) {
            size = WordConstantsA.answers.size
            System.arraycopy(WordConstantsA.answers, 0, words, 0, WordConstantsA.answers.size)
        }
        else {
            var total_size = 0
           total_size = addToArray(guesses01, total_size)
            total_size = addToArray(guesses02, total_size)
            total_size = addToArray(guesses03, total_size)
            total_size = addToArray(guesses04, total_size)
            total_size = addToArray(guesses05, total_size)
            size = total_size

        }
        //        readWords(filename)
    }

    private fun addToArray(guesses: Array<Word>, start : Int): Int {
        val thisSize = guesses.size
        val total_size = start + thisSize
        System.arraycopy(guesses, 0, words, start, thisSize)
        return total_size
    }


    fun findIndex(word: Word) : Int{
        var ret = -1
        for (w in 0 until size) {
            if (word == words[w]) {
                ret = w
                break
            }
        }
        return ret
    }

//   private fun readWords(dataFilename : String){
//
//        val file = File(dataFilename)
//        if (!file.canRead())
//            exitWithMessage("FileNotExist")
//       var index = 0
//       file.forEachLine {
//           if (it.length == 5)
//           {
//               words[index] = Word(it)
//               index++
//           }
//        }
//        if (index < 1) {
//            exitWithMessage("EmptyFile")
//        }
//       size = index
//    }


}