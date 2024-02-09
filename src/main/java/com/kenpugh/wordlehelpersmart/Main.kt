import android.content.Context
import java.io.File
import java.io.FileNotFoundException

fun main() {
    output("Wordle Guess Helper")
    Game.load()
    //computeMatchValues()
    val startingString = arrayOf(
        "SOARE", "TRACE", "CRATE", "SLATE", "CARET","CRANE", "CARTE",
        "SAINT","LEAST", "STARE", "ROATE","RAILE", "IRATE",
         "REAST", "AUDIO", "ADIEU", "ALERT","ARTEL","LATER", "CANOE",
        "RAISE", "ORATE", "SLANE",  "SALET",
    )
    val startingWords = convertToWordArray(startingString)
    val startingGuess = Word("SOARE")
    runTurnsWithArrays()
    //runTurnsStartingWithGuessAndAsk(startingGuess)

}

fun computeMatchValues(context: Context?) {
    var valuesRead = false
    if (context != null){
        try {
            val input = context.openFileInput(MATCH_FILENAME)
            output("Reading file")
            valuesRead =ReadWriteByteArray.readAllWordMatches(input)
            output("Done Reading")
        }
        catch(exception: Exception){
            output("Unable to open or read input file")
        }
        if (!valuesRead){
            output("Starting computation")
            AllWordMatches.initializeTheWordMatches()
            output("Done computation")
            try {
                    val output = context.openFileOutput(MATCH_FILENAME, Context.MODE_PRIVATE)
                    output("Writing file")
                    ReadWriteByteArray.writeAllWordMatches(output)
                    output("Done Writing")
                }
            catch(exception: Exception){
                    output("Unable to open or write output file")
                }
            }
        }
    else {
            // context is null, so file cannot be accesses
            AllWordMatches.initializeTheWordMatches()
            output("File I/O not working ")
        }
}

