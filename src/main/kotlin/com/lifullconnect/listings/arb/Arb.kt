package com.lifullconnect.listings.arb

data class Arb<A>(val sample: (RandomSource) -> A) {

    fun samples(rs: RandomSource): Sequence<A> = generateSequence { sample(rs) }

    fun samplesList(rs: RandomSource, cases: Int): List<A> =
        samples(rs).take(cases).toList()

}

val positiveIntArb: Arb<Int> = Arb { TODO() }

// Primitive operations

fun <A> constArb(value: A): Arb<A> =  TODO()

fun <A, B> Arb<A>.map(f: (A) -> B): Arb<B> =  TODO()

fun <A, B> Arb<A>.flatMap(f: (A) -> Arb<B>): Arb<B> =  TODO()

// Secondary functions

fun <A, B, C> bind(arbA: Arb<A>, arbB: Arb<B>, f: (A, B) -> C): Arb<C> =  TODO()

fun <A> weightedArb(freq1: Int, arb1: Arb<A>, freq2: Int, arb2: Arb<A>): Arb<A> =  TODO()

// Useful arbs

fun rangedPositiveInt(min: Int, max: Int): Arb<Int> =  TODO()

fun charArb(): Arb<Char> =  TODO()

fun stringArb(minLength: Int, maxLength: Int): Arb<String> =  TODO()

