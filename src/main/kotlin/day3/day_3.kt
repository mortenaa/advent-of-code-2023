package day3

import utils.read

val DAY = "day3"

val partNumber = """\d\d?\d?""".toRegex()
val symbol = """[^.\d]""".toRegex()
val gear = """\*""".toRegex()

fun part1(input: String): Int {
    val symbols = input.lines().withIndex().flatMap { line ->
        symbol.findAll(line.value).map { line.index to it.range.start }
    }.toSet()
    return input.lines().withIndex().flatMap { line ->
        partNumber.findAll(line.value).mapNotNull { part ->
            if (isNextTo(symbols, line.index, part.range))
                part.value
            else
                null
        }
    }.sumOf { it.toInt() }
}

fun part2(input: String): Int {
    val gears = input.lines().withIndex().flatMap { line ->
        gear.findAll(line.value).map { line.index to it.range.start }
    }.toSet()
    return input.lines().withIndex().flatMap { line ->
        partNumber.findAll(line.value).mapNotNull { part ->
            val m = isNextTo2(gears, line.index, part.range)
            if (m != null) println("$m ${part.value}")
            if (m != null) m to part.value.toInt()
            else null
        }
    }.groupBy({ it.first }, { it.second })
        .map { it.value }
        .filter { it.size == 2 }
        .map { it[0] * it[1] }
        .sumOf { it }
}

fun isNextTo(set: Set<Pair<Int, Int>>, line: Int, range: IntRange): Boolean =
    set.contains(line to range.start-1) ||
            set.contains(line to range.endInclusive+1) ||
            range.extend().map { line-1 to it }.any { set.contains(it) } ||
            range.extend().map { line+1 to it }.any { set.contains(it) }

fun isNextTo2(set: Set<Pair<Int, Int>>, line: Int, range: IntRange): Pair<Int, Int>? {
    if (set.contains(line to range.start-1))
        return line to range.start-1
    else if (set.contains(line to range.endInclusive+1))
        return line to range.endInclusive+1
    else {
        val m = range.extend().map { line - 1 to it }.find { set.contains(it) }
        if (m != null) return m
        val n = range.extend().map { line + 1 to it }.find { set.contains(it) }
        if (n != null) return n
    }
    return null
}

private fun IntRange.extend(): IntRange = IntRange(this.start-1, this.endInclusive+1)

fun main() {
    check(part1(read(DAY, "test")) == 4361)
    check(part2(read(DAY, "test")) == 467835)
    part1(read(DAY)).let { println("Part 1: $it")}
    part2(read(DAY)).let { println("Part 2: $it")}
}