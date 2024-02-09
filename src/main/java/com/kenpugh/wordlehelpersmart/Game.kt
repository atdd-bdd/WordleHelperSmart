class Game {
    companion object {
        val guesses = Words()
        val answers = Words()
        fun load() {

            guesses.load(GUESS_FILENAME)
            answers.load(ANSWER_FILENAME)
            val size = guesses.size
            val size1 = answers.size
            output("Guesses $size Answers $size1")

//            Trace::write((string) " Guess count " +
//                    int_to_string(guesses.size) + " answer count "
//                    + int_to_string(answers.size) + "\n");
        }
    }
}