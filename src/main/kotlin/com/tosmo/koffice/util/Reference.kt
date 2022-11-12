package com.tosmo.akcel.util

object Reference {
    val alphaMap = mapOf(
        0 to 'A', 1 to 'B', 2 to 'C', 3 to 'D', 4 to 'E', 5 to 'F', 6 to 'G',
        7 to 'H', 8 to 'I', 9 to 'J', 10 to 'K', 11 to 'L', 12 to 'M', 13 to 'N',
        14 to 'O', 15 to 'P', 16 to 'Q', 17 to 'R', 18 to 'S', 19 to 'T', 20 to 'U',
        21 to 'V', 22 to 'W', 23 to 'X', 24 to 'Y', 25 to 'Z'
    )
    
    fun parseHorizontal(value: Int): String {
        return buildString {
            val refs = mutableListOf<Int>()
            var num = value
            do {
                refs.add(num % alphaMap.size)
                num /= alphaMap.size
                if (num in 1 until alphaMap.size) {
                    refs.add(num - 1)
                }
            } while (num >= alphaMap.size)
            refs.reversed().forEach { append(alphaMap[it]) }
            println(refs.reversed())
        }
    }
}