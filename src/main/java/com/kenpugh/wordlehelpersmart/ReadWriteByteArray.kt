@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalUnsignedTypes::class, ExperimentalUnsignedTypes::class)


import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ReadWriteByteArray {
    companion object {
        //    fun File.chunkedSequence(chunk: Int): Sequence<ByteArray> {
//        val input = this.inputStream().buffered()
//        val buffer = ByteArray(chunk)
//        return generateSequence {
//            val red = input.read(buffer)
//            if (red >= 0) buffer.copyOf(red)
//            else {
//                input.close()
//                null
//            }
//        }
        fun writeAllWordMatches(fileOutputStream : FileOutputStream ) {
            val unsignedBuffer = UByteArray(MAX_ANSWERS)
//           val fileout: FileOutputStream = openFileOutput(MATCH_FILENAME, MODE_PRIVATE)

            fileOutputStream.buffered().use { input ->
                for (wm in 0 until MAX_GUESSES) {
                    AllWordMatches.all_word_matches[wm].matchesForAnswers.copyInto(unsignedBuffer)

                    val buffer = unsignedBuffer.toByteArray()
                    input.write(buffer)

                }
            }
        }

        fun readAllWordMatches(fileInputStream : FileInputStream ) : Boolean {
            val buffer = ByteArray(MAX_ANSWERS)
           fileInputStream.buffered().use { input ->
                for (wm in 0 until MAX_GUESSES) {
                    val sz = input.read(buffer)
                    if (sz < MAX_ANSWERS)
                       return false
                    val unsignedBuffer = buffer.toUByteArray()
                    unsignedBuffer.copyInto(
                        AllWordMatches.all_word_matches[wm].matchesForAnswers
                    )
                }
            }
            return true
        }
    }
}