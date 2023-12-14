package day14

import utils.read

const val DAY = "day14"

fun main() {
    check(part1(read(DAY, "test")) == 42)
    check(part2(read(DAY, "test")) == 42)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun part1(input: String): Int {
    return 42
}

fun part2(input: String): Int {
    return 42
}