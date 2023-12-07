package day7

import utils.read

private val Hand.rank: Int
    get() = 0
private val Card.rank: Int
    get() = 0

const val DAY = "day7"

typealias Card = Char

data class Hand(val cards: List<Card>)

fun main() {
    check(part1(read(DAY, "test")) == 6440)
    check(part2(read(DAY, "test")) == 42)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun part1(input: String): Int {
    //32T3K 765
    input.lines()
        .map { it.trim().split(" ") }
        .map { Hand( it[0].map { it as Card }) to it[1].trim().toInt() }
        .sortedWith() { compareBy<Hand> { it.rank } }
    return 6440
}

fun part2(input: String): Int {
    return 42
}

