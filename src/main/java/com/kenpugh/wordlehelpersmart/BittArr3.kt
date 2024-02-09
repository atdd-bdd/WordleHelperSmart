class BitArr3 {
    companion object {

        fun andAll(one: BitArr3, other: BitArr3): BitArr3 {
            val result = BitArr3()
            result.clearAll()
            val sizeOther = other.size
            val sizeThis = one.size
            var iThis = 0
            var iOther = 0
            var iResult = 0
            while (iThis < sizeThis && iOther < sizeOther) {
                if (one.bitarray[iThis] < other.bitarray[iOther]) iThis++
                else if (other.bitarray[iOther] < one.bitarray[iThis]) iOther++
                else {
                    result.bitarray[iResult] = other.bitarray[iOther]
                    iThis++
                    iOther++
                    iResult++
                }
            }
            result.size = iResult
            return result
        }
        fun orAll(one: BitArr3, other: BitArr3): BitArr3 {
            val result = BitArr3()
            result.clearAll()
            val sizeOther = other.size
            val sizeThis = one.size
            var iThis = 0
            var iOther = 0
            var iResult = 0
            while (iThis < sizeThis && iOther < sizeOther) {
                if (one.bitarray[iThis] < other.bitarray[iOther]) {
                    result.bitarray[iResult] = one.bitarray[iThis]
                    iThis++
                }
                else if (other.bitarray[iOther] < one.bitarray[iThis]) {
                    result.bitarray[iResult] = other.bitarray[iOther]
                    iOther++
                }

                else {
                    result.bitarray[iResult] = other.bitarray[iOther]
                    iThis++
                    iOther++
                    iResult++
                }
            }
            result.size = iResult
            return result
        }
    }


    var size = 0
    var bitarray = IntArray(MAX_ANSWERS) { 0 }


//    fun copy_to(destination: BitArr3) {
//        destination.bitarray = bitarray.copyOf()
//        destination.size = size
//
//
//    }

//    fun print() {
//        for (a in 0..size-1) {
//            output(bitarray[a])
//        }
//    }


    fun clearAll() {
        for (i in 0 until MAX_ANSWERS) {
            bitarray[i] = 0
        }
        size = 0 /// This was just added
    }

    fun setAll() {
        for (i in 0 until MAX_ANSWERS) {
            bitarray[i] = i
        }
        size = MAX_ANSWERS
    }

    fun setAll(last: Int) {
        for (i in 0 until last) {
            bitarray[i] = i
        }
        size = last
    }


    fun count(): Int {
        return size
    }

    fun set(word: Int) {
        bitarray[size] = word
        size++
    }

//    fun get_bitarray(): IntArray {
//        return this.bitarray
//    }
}

//bool BitArr3::get(unsigned int word) {
//	unsigned value = bitarray[word];
//	bool ret = (value != 0);
//	return ret;
//}




