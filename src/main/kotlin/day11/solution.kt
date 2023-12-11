package day11

import utils.read

const val DAY = "day11"
typealias Point = Pair<Long, Long>

fun main() {
    check(part1(read(DAY, "test")) == 374L)
    check(part2(read(DAY, "test")) == 82000210L)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun part1(input: String): Long {
    val gs = parse(input)
    val expanded = expand(gs)
    var sum = 0L
    for (a in 0 until expanded.size-1) {
        for (b in a + 1 until expanded.size) {
            val g1 = expanded[a]
            val g2 = expanded[b]
            val dist = Math.abs(g1.first-g2.first) + Math.abs(g1.second-g2.second)
            sum += dist
        }
    }
    return sum
}

fun part2(input: String): Long {
    val gs = parse(input)
    val expanded = expand(gs, 1000000-1)
    var sum = 0L
    for (a in 0 until expanded.size-1) {
        for (b in a + 1 until expanded.size) {
            val g1 = expanded[a]
            val g2 = expanded[b]
            val dist = Math.abs(g1.first-g2.first) + Math.abs(g1.second-g2.second)
            sum += dist
        }
    }
    return sum}

fun parse(input: String): List<Point> {
    return input.lines().flatMapIndexed { i, line ->
        line.mapIndexed { j, char ->
            if (char == '#') Point(i.toLong(), j.toLong()) else null
        }.filterNotNull()
    }
}

fun expand(points: List<Point>, shift: Long = 1): List<Point> {
    val expanded = mutableListOf<Point>()
    val expanded2 = mutableListOf<Point>()
    var shiftX = 0L
    var shiftY = 0L

    for (j in 0 ..points.maxX()) {
        val gs = points.filter { it.second == j }
        if (gs.isEmpty()) {
            shiftX += shift
        } else {
            expanded.addAll(gs.map { Point(it.first, it.second+shiftX)})
        }
    }
    for (i in 0 ..points.maxY()) {
        val gs = expanded.filter { it.first == i }
        if (gs.isEmpty()) {
            shiftY += shift
        } else {
            expanded2.addAll(gs.map { Point(it.first+shiftY, it.second)})
        }
    }

    return expanded2
}

private fun List<Point>.print() {
    for (i in 0 .. maxY()) {
        for (j in 0 .. maxX()) {
            if (contains(Point(i, j)))
                print("#")
            else
                print(".")
        }
        println()
    }
    println()
}

private fun List<Point>.minY(): Long {
    return this.minOf { it.first }
}

private fun List<Point>.maxY(): Long {
    return this.maxOf { it.first }
}

private fun List<Point>.minX(): Long {
    return this.minOf { it.second }
}

private fun List<Point>.maxX(): Long {
    return this.maxOf { it.second }
}
