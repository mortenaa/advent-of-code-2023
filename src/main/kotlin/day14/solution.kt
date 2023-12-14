package day14

import utils.read

const val DAY = "day14"

fun main() {
    check(part1(read(DAY, "test")) == 136)
    check(part2(read(DAY, "test")) == 64)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun parse(input: String): Array<CharArray> {
    return input.lines().map { line ->
        line.toCharArray()
    }.toTypedArray()
}

fun score(platform: Array<CharArray>): Int {
    val len = platform.size
    return platform.indices.sumOf {
        platform[it].count { it == 'O' }*(len-it)
    }
}

fun tilt(platform: Array<CharArray>): Array<CharArray> {
    for (i in platform.indices.drop(1)) {
        for (j in platform[i].indices) {
            if (platform[i][j] == 'O') {
                var t = -1
                for (k in i-1 downTo 0) {
                    if (platform[k][j] == '.') t = k
                    else break
                }
                if (t >= 0) {
                    platform[t][j] = 'O'
                    platform[i][j] = '.'
                }
            }
        }
    }
    return platform
}

fun cycle(platform: Array<CharArray>): Array<CharArray> {
    // North
    for (i in platform.indices.drop(1)) {
        for (j in platform[i].indices) {
            if (platform[i][j] == 'O') {
                var t = -1
                for (k in i-1 downTo 0) {
                    if (platform[k][j] == '.') t = k
                    else break
                }
                if (t >= 0) {
                    platform[t][j] = 'O'
                    platform[i][j] = '.'
                }
            }
        }
    }
    // West
    val len = platform.size
    for (j in 1 until len) {
        for (i in 0 until len) {
            if (platform[i][j] == 'O') {
                var t = -1
                for (k in j-1 downTo 0) {
                    if (platform[i][k] == '.') t = k
                    else break
                }
                if (t >= 0) {
                    platform[i][t] = 'O'
                    platform[i][j] = '.'
                }
            }
        }
    }
    // South
    for (i in len-2 downTo 0) {
        for (j in platform[i].indices) {
            if (platform[i][j] == 'O') {
                var t = -1
                for (k in i+1 until len) {
                    if (platform[k][j] == '.') t = k
                    else break
                }
                if (t >= 0) {
                    platform[t][j] = 'O'
                    platform[i][j] = '.'
                }
            }
        }
    }
    // East
    for (j in len-2 downTo  0) {
        for (i in 0 until len) {
            if (platform[i][j] == 'O') {
                var t = -1
                for (k in j+1 until len) {
                    if (platform[i][k] == '.') t = k
                    else break
                }
                if (t >= 0) {
                    platform[i][t] = 'O'
                    platform[i][j] = '.'
                }
            }
        }
    }
    return platform
}

fun print(platform: Array<CharArray>) {
    platform.forEach { println(it.joinToString("")) }
}

fun stringify(platform: Array<CharArray>): String {
    return platform.map { it.joinToString("") }.joinToString("")
}

fun part1(input: String): Int {
    val platform = parse(input)
    tilt(platform)
    return score(platform)
}

fun part2(input: String): Int {
    val platform = parse(input)
    val values = mutableListOf<Int>()
    val cache = mutableMapOf<String, Int>()
    var cycle = 0
    var first = 0
    for (i in 0 .. 1000000000) {
        cycle(platform)
        val v = score(platform)
        val s = stringify(platform)
        if (s in cache) {
            first = i
            cycle = i-cache[s]!!
            break
        } else {
            cache[s] = i
        }
        values.add(score(platform))
    }
    val n = 1000000000
    val ofset = first-cycle
    val index = (n - 1 - ofset) % cycle + ofset
//    println("Cycle: $cycle")
//    println("First: $first")
//    println("Index: $index")
//    println("Score: ${values[index]}")
    return values[index]
}
