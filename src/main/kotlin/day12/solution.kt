package day12

import utils.read
import java.lang.IllegalStateException
import kotlin.math.sqrt

const val DAY = "day12"


fun main() {
    check(part1(read(DAY, "test")) == 21L)
    check(part2(read(DAY, "test")) == 525152L)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun permutations(record: String,
                 groups: List<Int>,
                 cache: MutableMap<Pair<String, List<Int>>, Long> = mutableMapOf()): Long {

    if (record to groups in cache)
        return cache[record to groups]!!

    fun addCache(value: Long): Long {
        cache[record to groups] = value
        return value
    }

    if (groups.isEmpty()) {
        return if ('#' !in record) addCache(1) else addCache(0)
    }

    if (record.isEmpty()) return addCache(0)

    val next = record.first()
    val group = groups.first()

    fun handleSpring(): Long {
        val s = record.take(group).replace('?','#')
        if (s != "#".repeat(group)) {
            return 0
        }
        if (record.length == group) {
            if (groups.size == 1)
                return 1
            else
                return 0
        }
        if (record[group] in ".?")
            return permutations(record.drop(group+1), groups.drop(1), cache)
        return 0
    }

    fun handleEmpty(): Long {
        return permutations(record.drop(1), groups, cache)
    }

    val p = when (next) {
        '#' -> handleSpring()
        '.' -> handleEmpty()
        '?' -> handleEmpty() + handleSpring()
        else -> throw IllegalStateException()
    }
    return addCache(p)

}

fun parse(input: String, expand: Boolean = false): List<Pair<String, List<Int>>> {
    return input.lines()
        .map { it.trim().split(" ") }
        .map { parseRecord(it[0], expand) to parseGroups(it[1], expand) }
}

fun parseRecord(input: String, expand: Boolean=false): String {
    return if (expand)
        (input.trim()+"?").repeat(5).dropLast(1)
    else
        input.trim()
}

fun parseGroups(input: String, expand: Boolean=false): List<Int> {
    return if (expand)
        (input.trim()+",").repeat(5).dropLast(1).split(",").map { it.toInt() }
    else
        input.trim().split(",").map { it.toInt() }
}

fun part1(input: String): Long {
    val perms = parse(input)
        .sumOf { permutations(it.first, it.second) }
    return perms
}

fun part2(input: String): Long {
    val perms = parse(input, expand = true)
        .sumOf { permutations(it.first, it.second) }
    return perms
}
