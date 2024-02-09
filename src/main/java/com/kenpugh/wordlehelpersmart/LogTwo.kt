import kotlin.math.log2

class LogTwo {
    companion object {
        private const val MAX_LOGS = 2000000
        private var logs = IntArray(MAX_LOGS)
        private var initialized = false


        fun log2t100(value: Int): Int {
            if (!initialized) {
                output("Starting log two")
                logs[0] = 0
                logs[1] = 50
                for (i in 2 until MAX_LOGS) {
                    val v = log2((i.toDouble() / 100))
                    logs[i] = (v * 100).toInt()
                }
                output("Ending log two")
                initialized = true
            }
            return if (value < MAX_LOGS) {
                logs[value] // value is 100 times
            } else {
                logs[MAX_LOGS - 1]
            }
        }
    }
}