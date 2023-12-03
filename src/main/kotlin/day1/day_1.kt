package day1

import utils.println
import utils.read
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.readText

val DAY = "day1"

val digits = """(?=(1|one)|(2|two)|(3|three)|(4|four)|(5|five)|(6|six)|(7|seven)|(8|eight)|(9|nine))""".toRegex()

fun part1(input: String): Int {

    val lines = input.lines().map { it.trim() }
    var sum = 0
    lines.map {
        val first = it.first { it.isDigit() }.digitToInt()
        val last = it.last { it.isDigit() }.digitToInt()
        sum += (10 * first + last)
    }

    return sum
}

fun part2(input: String): Int {
    val lines = input.lines().map { it.trim() }
    var sum = 0
    lines.map {
        val matches = digits.findAll(it)
        val first = matches.first().groupValues.drop(1).indexOfFirst { it.isNotEmpty() } + 1
        val last = matches.last().groupValues.drop(1).indexOfFirst { it.isNotEmpty() } + 1
        sum += (10 * first + last)
    }
    return sum
}

fun main() {
    check(part1(read(DAY, "test1")) == 142)
    check(part2(read(DAY, "test2")) == 281)
    part1(read(DAY)).println()
    part2(read(DAY)).println()
}