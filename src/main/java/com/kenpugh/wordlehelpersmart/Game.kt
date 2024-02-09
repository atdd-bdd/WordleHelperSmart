
import java.nio.file.Paths

class Game {
    companion object {
        val guesses = Words()
        val answers = Words()
        fun load() {
            val path = Paths.get("").toAbsolutePath().toString()
            output("Working Directory = $path")
            output("filename $GUESS_FILENAME $ANSWER_FILENAME")
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