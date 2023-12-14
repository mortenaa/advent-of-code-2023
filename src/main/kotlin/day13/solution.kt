package day13

import utils.read
import kotlin.math.min

const val DAY = "day13"

fun main() {
    check(part1(read(DAY, "test")) == 405)
    check(part2(read(DAY, "test")) == 400)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun part1(input: String): Int {
    val blocks = parse(input)
    return blocks.sumOf {
        val m = mirror(it)
            if (m > 0) m else mirror(transpose(it))*100
    }
}

fun printBlock(strings: List<String>) {
    strings.forEach { println(it) }
}

fun transpose(block: List<String>): List<String> {
    return (0 until block.first().length).map { i ->
        block.map { it[i] }.joinToString("")
    }
}

fun mirror(block: List<String>): Int {
    val len = block.first().length
    for (i in 1 until len) {

        if (block.all {
                val l = min(i, len - i)
                //println(it.subSequence(i - l, i).toString() + "^" + it.subSequence(i, i + l).reversed())
                it.substring(i - l, i) == it.substring(i, i + l).reversed()
            }) {
            println("Mirror: $i")
            return i
        }
    }
    return 0
}

fun diff(a: String, b: String): Int {
    return a.zip(b).sumOf {
        if (it.first == it.second)
            0 as Int
        else
            1 as Int
    }
}

fun mirrorSmudge(block: List<String>): Int {
    val len = block.first().length
    for (i in 1 until len) {

        if (block.sumOf {
                val l = min(i, len - i)
                //println(it.subSequence(i - l, i).toString() + "^" + it.subSequence(i, i + l).reversed())
                val a = it.substring(i - l, i)
                val b = it.substring(i, i + l).reversed()
                diff(a, b)
            } == 1) {
            println("Mirror: $i")
            return i
        }
    }
    return 0
}

fun part2(input: String): Int {
    val blocks = parse(input)
    return blocks.sumOf {
        val m = mirrorSmudge(it)
        if (m > 0) m else mirrorSmudge(transpose(it))*100
    }}

fun parse(input: String): List<List<String>> {
    return input.split("\n\n").map {
        it.lines()
    }
}