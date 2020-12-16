package com.lifullconnect.listings.arb

import io.kotest.core.spec.style.StringSpec
import org.junit.jupiter.api.Assertions.assertEquals

class ArbTest : StringSpec() {

    // Returns 0, 1, 2, 3, 4...
    fun testRandomSource(): RandomSource = object : RandomSource {
        private var v = 0
        override fun next(): Int = v++
    }

    init {

        // Base Arb

        "positiveIntArb"{
            assertEquals(listOf(0, 1, 2, 3, 4), positiveIntArb.samplesList(testRandomSource(), 5))
        }

        // Primitives

        "const"{
            val exclamations = constArb("!")
            assertEquals(listOf("!", "!", "!", "!", "!"), exclamations.samplesList(testRandomSource(), 5))
        }

        "map"{
            val negativeOr0IntArb: Arb<Int> = positiveIntArb.map { -it }
            assertEquals(listOf(0, -1, -2, -3, -4), negativeOr0IntArb.samplesList(testRandomSource(), 5))
        }

        "charArb"{
            assertEquals(listOf('a', 'b', 'c', 'd', 'e'), charArb().samplesList(testRandomSource(), 5))
        }

        // Secondary functions

        "bind"{
            val charNum: Arb<String> = bind(positiveIntArb, charArb()) { i, s -> i.toString() + s }
            assertEquals(listOf("0b", "2d", "4f", "6h", "8j"), charNum.samplesList(testRandomSource(), 5))
        }

        "weightedArb"{
            val negativeOr0IntArb: Arb<Int> = positiveIntArb.map { -it }
            val probablyPositiveArb: Arb<Int> = weightedArb(3, negativeOr0IntArb, 2, positiveIntArb)
            // 0, 1: 0 % 5 < 3 --> Generate negative -1
            // 2, 3: 2 % 5 < 3 --> Generate negative -3
            // 4, 5: 4 % 5 >= 3 --> Generate positive 5
            // 6, 7: 6 % 5 < 3 --> Generate negative -7
            // 8,9: 8 % 5 >= 3 --> Generate negative 9
            assertEquals(listOf(-1, -3, 5, -7, 9), probablyPositiveArb.samplesList(testRandomSource(), 5))
        }

        // All together now

        "rangedPositiveIntArb"{
            assertEquals(
                listOf(2, 3, 4, 2, 3, 4, 2), rangedPositiveInt(2, 4).samplesList(testRandomSource(), 7)
            )
        }
        "stringArb"{
            val shortStringArb: Arb<String> = stringArb(0, 4)
            // 0: Length will be 0
            // 1: Length will be 1
            // 2: "c"
            // 3: Length will be 3
            // 4, 5, 6: "efg"
            // 7: Length will be 7 % 5 = 2 (lengths can be 0, 1, 2, 3 or 4, so there are 5 options)
            // 8, 9: "ij"
            assertEquals(listOf("", "c", "efg", "ij"), shortStringArb.samplesList(testRandomSource(), 4))
        }
    }
}
