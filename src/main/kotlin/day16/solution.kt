package day16

import day16.Direction.*
import day16.Map
import utils.read
import java.lang.IllegalStateException

const val DAY = "day16"

fun main() {
    check(part1(read(DAY, "test")) == 46)
    check(part2(read(DAY, "test")) == 51)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}
enum class Direction {N, S, W, E}
typealias Position = Pair<Int, Int>
typealias Map = Array<CharArray>
data class Beam(val position: Position, val direction: Direction)

fun move(beam: Beam): Beam {
    return when (beam.direction) {
        S -> { beam.copy(position = Position(beam.position.first + 1, beam.position.second))}
        N -> { beam.copy(position = Position(beam.position.first - 1, beam.position.second))}
        W -> { beam.copy(position = Position(beam.position.first, beam.position.second - 1))}
        E -> { beam.copy(position = Position(beam.position.first, beam.position.second + 1))}
    }
}

fun split(beam: Beam): List<Beam> {
    return when (beam.direction) {
        N, S -> listOf(move(beam.copy(direction = W)), move(beam.copy(direction = E)))
        W, E -> listOf(move(beam.copy(direction = N)), move(beam.copy(direction = S)))
    }
}

fun next(map: Map, beam: Beam): List<Beam> {
    if (beam.position.first !in map.indices || beam.position.second !in map.first().indices)
        return emptyList()
    return when (map[beam.position.first][beam.position.second]) {
        '.' -> { listOf(move(beam)) }
        '-' -> { if (beam.direction in listOf(W, E)) listOf(move(beam)) else split(beam) }
        '|' -> { if (beam.direction in listOf(N, S)) listOf(move(beam)) else split(beam) }
        '/' -> { listOf(mirror(beam, '/')) }
        '\\' -> { listOf(mirror(beam, '\\')) }
        else -> { throw IllegalStateException() }
    }
}

fun mirror(beam: Beam, mirror: Char): Beam {
    return when (beam.direction) {
        N -> if (mirror == '/') move(beam.copy(direction = E)) else move(beam.copy(direction = W))
        S -> if (mirror == '/') move(beam.copy(direction = W)) else move(beam.copy(direction = E))
        W -> if (mirror == '/') move(beam.copy(direction = S)) else move(beam.copy(direction = N))
        E -> if (mirror == '/') move(beam.copy(direction = N)) else move(beam.copy(direction = S))
    }
}

fun energize(map: Map, start: Beam): Int {
    val queue = mutableListOf(start)
    val visited = mutableSetOf<Beam>()
    while (queue.isNotEmpty()) {
        val beam = queue.removeFirst()
        if (!inside(map, beam)) continue
        if (beam in visited) continue
        visited.add(beam)
        val next = next(map, beam).filter { inside(map, beam) }
        queue.addAll(next)
    }
    val v = visited.map { it.position }.toSet()
    return v.size
}

fun part1(input: String): Int {
    val map = input.lines().map {
        it.toCharArray()
    }.toTypedArray()
    return energize(map, Beam(Position(0, 0), E))
}

fun inside(map: Map, beam: Beam): Boolean {
    return beam.position.first in map.indices && beam.position.second in map.first().indices
}

fun part2(input: String): Int {
    val map = input.lines().map {
        it.toCharArray()
    }.toTypedArray()

    val initial = buildList<Beam> {
        for (i in map.indices) {
            add(Beam(Position(i, 0), E))
            add(Beam(Position(i, map.first().lastIndex), W))
        }
        for (j in map.first().indices) {
            add(Beam(Position(0, j), S))
            add(Beam(Position(map.lastIndex, j), N))
        }
    }
    return initial.maxOf { energize(map, it) }
}