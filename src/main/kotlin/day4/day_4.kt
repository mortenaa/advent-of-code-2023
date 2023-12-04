package day4

import utils.read
import java.math.BigInteger

val DAY = "day4"

fun parseToSet(s: String) = s.trim().split(" +".toRegex()).map { it.toInt() }.toSet()

fun part1(input: String): BigInteger {
    return input.lines()
        .map {
            it.substringAfter(":").split("|").let {
                parseToSet(it[0]) to parseToSet(it[1])
            }
        }.map { it.second.intersect(it.first).size }
        .filter { it > 0 }
        .sumOf { 2.toBigInteger().pow(it - 1) }
}

data class Card(val cardNo: Int, val wins: Int, var num: Int)

fun part2(input: String): Int {
    val cards = input.lines()
        .map {
            it.substringAfter(":").split("|").let {
                parseToSet(it[0]) to parseToSet(it[1])
            }
        }.map { it.second.intersect(it.first).size }.withIndex()
        .map { Card(it.index+1, it.value, 1) }.also { println(it) }
        .toTypedArray()

    for (index in 0 until cards.size) {
        val wins = cards[index].wins
        val num = cards[index].num
        if (wins > 0) {
            for (i in index+1 .. (index+wins).coerceAtMost(cards.size-1)) {
                cards[i].num += num
            }
        }
        println(cards[index])
    }
    return cards.sumOf { it.num }.also { println(it) }
}

fun main() {
    check(part1(read(DAY, "test")).toInt() == 13)
    check(part2(read(DAY, "test")) == 30)

    part1(read(DAY)).let { println("Part 1: $it")}
    part2(read(DAY)).let { println("Part 2: $it")}
}