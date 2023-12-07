package day7

import utils.read
import java.lang.IllegalStateException

const val DAY = "day7"

data class Card(val name: Char, val value: Int): Comparable<Card> {

    override fun toString(): String {
        return name.toString()
    }
    override fun compareTo(other: Card): Int =
        compareValuesBy(this, other, { it.value }, { it.value })
}

data class Hand(val cards: List<Card>, val bet: Int, val joker: Boolean = false): Comparable<Hand> {

    override fun toString(): String {
        return cards.joinToString("")
    }
    fun value(): Int {
        val groups = cards.groupBy { it.value }
        return when {
            groups.size == 5 -> 1
            groups.size == 4 -> 2
            groups.size == 3 -> if (groups.filter { it.value.size == 2}.count() == 2) 3 else 4
            groups.size == 2 -> if (groups.filter { it.value.size == 3}.count() == 1) 5 else 6
            groups.size == 1 -> 7
            else -> throw IllegalStateException("")
        }
    }

    override fun compareTo(other: Hand): Int {
        return compareBy<Hand> { if (joker) it.maxValue() else it.value() }
                .thenBy { it.cards[0] }
                .thenBy { it.cards[1] }
                .thenBy { it.cards[2] }
                .thenBy { it.cards[3] }
                .thenBy { it.cards[4] }
                .thenBy { it.cards[5] }.compare(this, other)
    }

    private fun maxValue(): Int {
        val cardString = this.toString()
        if (!cardString.contains('J')) {
            return value()
        } else {
            if (cardString == "JJJJJ") return 7
            val best = cardString.filter { it != 'J'}.groupBy { it }.maxBy { it.value.size }.value.first()
            return cardString.replace('J',best).let { Hand(it, "0", true).value() }
        }
    }
}

fun main() {
    check(part1(read(DAY, "test")) == 6440)
    check(part2(read(DAY, "test")) == 5905)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun part1(input: String): Int {
    return input.lines()
        .map { it.trim().split(" ") }
        .map { Hand(it[0], it[1]) }
        .sorted().withIndex().sumOf { it.value.bet*(it.index+1) }
}

fun Hand(cards: String, bet: String, joker: Boolean = false): Hand =
    Hand(cards.trim().map { Card(it, joker) }, bet.trim().toInt(), joker)

fun Card(char: Char, joker: Boolean = false): Card =
    when(char) {
        in '2' .. '9' -> Card(char, char.digitToInt())
        'T' -> Card(char, 10)
        'J' -> if (joker) Card(char, 1) else Card(char, 11)
        'Q' -> Card(char, 12)
        'K' -> Card(char, 13)
        'A' -> Card(char, 14)
        else -> throw IllegalStateException("")
    }

fun part2(input: String): Int {
    return input.lines()
        .map { it.trim().split(" ") }
        .map { Hand(it[0], it[1], true) }
        .sorted().withIndex().sumOf { it.value.bet*(it.index+1) }}

