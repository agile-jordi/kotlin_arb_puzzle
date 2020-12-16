package com.lifullconnect.listings.arb

interface RandomSource{
    // Returns positive Ints only
    fun next(): Int
}

