package day10

import day10.Pipe.*
import utils.read
import java.lang.IllegalStateException

private val Position.y: Int
    get() = second

private val Position.x: Int
    get() = first

const val DAY = "day10"

fun main() {
    check(part1(read(DAY, "test")) == 8)
    check(part1(read(DAY, "test2")) == 4)
    check(part2(read(DAY, "test3")) == 10)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

/*
| is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal;
 */

enum class Pipe(val char: Char) {
    NS('|'),
    EW('-'),
    NE('L'),
    NW('J'),
    SW('7'),
    SE('F'),
    GR('.'),
    ST('S'),
    IN('O')
}
fun asPipe(char: Char): Pipe = Pipe.values().filter { it.char == char }.first()

typealias Position = Pair<Int, Int>
typealias Map = Array<Array<Pipe>>

fun paths(map: Map, position: Position): Pair<Position, Position> {
    val p = map[position]
    return when (p) {
        NS -> position.north() to position.south()
        NW -> position.north() to position.west()
        NE -> position.north() to position.east()
        EW -> position.east() to position.west()
        SW -> position.south() to position.west()
        SE -> position.south() to position.east()
        else -> throw IllegalStateException()
    }
}

private operator fun Map.get(position: Position): Pipe {
    return this[position.y][position.x]
}

fun parse(input: String): Map {
    return input.lines().map { line ->
        line.map { char -> asPipe(char) }.toTypedArray()
    }.toTypedArray()
}

fun part1(input: String): Int {
    val map = parse(input)
    val width = map[0].size
    val height = map.size
    println("$width x $height")
    val start = findStart(map)
    println(start)
    val next = paths(map, start)
    println("Next: ${next.first} or ${next.second}")
    val n1 = loopLength(map, start, next.first)
    val n2 = loopLength(map, start, next.second)
    println("Loop length=$n1, $n2")
    return n1/2
}

fun loopLength(map: Map, start: Position, prev: Position): Int {
    var prev = prev
    var current = start
    var n = 0
    do {
        val next = paths(map, current).let { if (it.second == prev) it.first else it.second }
        //("n=$n, current=$current, prev=$prev, next=$next, pipe=${map[current].name}")
        //println("${current.second+1}:${current.first+1}")
        prev = current
        current = next
        n += 1
    } while (current != start)
    return n
}

fun findStart(map: Map): Position {
    val width = map[0].size
    val height = map.size
    for (y in 0 until height)
        for (x in 0 until width)
            if (map[y][x] == Pipe.ST) {
                val start = x to y
                map[y][x] = replaceStart(map, start)
                return x to y
            }
    throw IllegalStateException()
}

private fun Position.north(): Position {
    return x to y-1
}

private fun Position.south(): Position {
    return x to y+1
}

private fun Position.west(): Position {
    return x-1 to y
}

private fun Position.east(): Position {
    return x+1 to y
}

fun replaceStart(map: Map, start: Position): Pipe {
    val width = map[0].size
    val height = map.size
    var set = Pipe.values().filter { it !in  setOf(GR, ST) }.toMutableSet()
    if (start.y > 0 && map[start.north()] in listOf(NS, SW, SE)) {
        set = set.intersect(setOf(NS, NE, NW)).toMutableSet()
    }
    if (start.y < height-1 && map[start.south()] in listOf(NS, NE, NW)) {
        set = set.intersect(setOf(NS, SE, SW)).toMutableSet()
    }
    if (start.x > 0 && map[start.west()] in listOf(EW, NE, SE)) {
        set = set.intersect(setOf(EW, SW, NW)).toMutableSet()
    }
    if(start.x < width-1 && map[start.east()] in listOf(EW, NW, SW)) {
        set = set.intersect(setOf(EW, SE, NE)).toMutableSet()
    }
    check(set.size == 1)
    println("Replaced ${map[start]} with ${set.first()}")
    map[start.y][start.x] = set.first()
    return set.first()
}

fun part2(input: String): Int {
    val map = parse(input)
    val start = findStart(map)
    val next = paths(map, start)
    val loop = findLoop(map, start, next.second)
    println(loop.sortedBy { it.y })
    println(loop.size)
    val width = map[0].size
    val height = map.size
    for (y in 0 until height)
        for (x in 0 until width) {
            if (x to y !in loop)
                map[y][x] = GR
        }
    var n = 0
    printMap(map)
    for (y in 0 until height) {
        var inside = false
        var prev = GR
        for (x in 0 until width) {
            val p = map[y][x]
            if (p == EW) {
                continue
            }
            if (inside && p == GR) {
                n += 1
                map[y][x] = IN
                continue
            } else {
                if (p == GR) continue
                if (p == NS) {
                    inside = !inside
                } else if (p == NW && prev == SE) inside = !inside
                else if (p == SW && prev == NE) inside = !inside
            }
            prev = p
        }
    }
    printMap(map)
    println(n)
    return n
}

fun printMap(map: Map) {
    val width = map[0].size
    val height = map.size
    for (y in 0 until height) {
        for (x in 0 until width) {
            print(map[y][x].char)
        }
        println()
    }
    println()
}

fun findLoop(map: Map, start: Position, prev: Position): List<Position> {
    var prev = prev
    var current = start
    var loop = mutableListOf<Position>()
    do {
        val next = paths(map, current).let { if (it.second == prev) it.first else it.second }
        //("n=$n, current=$current, prev=$prev, next=$next, pipe=${map[current].name}")
        //println("${current.second+1}:${current.first+1}")
        prev = current
        current = next
        loop.add(current)
    } while (current != start)
    return loop
}