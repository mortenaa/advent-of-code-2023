package day9

import utils.read

const val DAY = "day9"

fun main() {
    check(part1(read(DAY, "test")) == 114)
    check(part2(read(DAY, "test")) == 2)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun reduce(list: List<Int>): List<Int> {
    return list.zipWithNext { a, b -> b - a }
}

fun next(list: List<Int>): Int {
    return if (list.all { it == 0 }) 0 else list.last() + next(reduce(list))
}

fun prev(list: List<Int>): Int {
    return if (list.all { it == 0 }) 0 else list.first() - prev(reduce(list))
}

fun part1(input: String): Int {
    return input.lines().sumOf {
        next(it.trim().split(" ").map { it.trim().toInt() })
    }

}

fun part2(input: String): Int {
    return input.lines().sumOf {
        prev(it.trim().split(" ").map { it.trim().toInt() })
    }}