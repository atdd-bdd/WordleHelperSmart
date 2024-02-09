import android.content.Context


//fun main() {
//    output("Wordle Guess Helper")
//    Game.load()
//    //computeMatchValues()
//    runTurnsWithArrays()
//    //runTurnsStartingWithGuessAndAsk(startingGuess)
//
//}

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

