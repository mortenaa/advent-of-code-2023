package day8

import utils.read

const val DAY = "day8"

fun main() {
    check(part1(read(DAY, "test")) == 2)
    check(part2(read(DAY, "test2")) == 6L)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun part1(input: String): Int {
    val (head, rest) = input.split("\n\n")
    val path = head
    val map = rest.lines().associate {
        it.split("=").let {
            it[0].trim() to (it[1].trim().drop(1).dropLast(1).split(",").let { it[0].trim() to it[1].trim()})
        }
    }
    var n = "AAA"
    var i = 0
    do {
        val s = path[i%path.length]
        i += 1
        n = map[n].let { if (s == 'L') it!!.first else it!!.second }
    } while (n != "ZZZ")
    return i
}

fun part2(input: String): Long {
    val (head, rest) = input.split("\n\n")
    val path = head
    val map = rest.lines().associate {
        it.split("=").let {
            it[0].trim() to (it[1].trim().drop(1).dropLast(1).split(",").let { it[0].trim() to it[1].trim()})
        }
    }
    var nodes = map.keys.filter { it.endsWith("A") }
    return nodes.map {
        var n = it
        var i = 0
        do {
            val s = path[i%path.length]
            i += 1
            n = map[n].let { if (s == 'L') it!!.first else it!!.second }
        } while (!n.endsWith("Z"))
        i.toLong()
    }.let { findLCM(it) }
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun findLCM(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}