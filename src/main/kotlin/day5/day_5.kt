package day5

import utils.read
import java.math.BigInteger

val DAY = "day5"

data class Function(val transformations: List<Transformation>) {
    val t: LongRange = transformations.first().destination
    fun apl(num: Long): Long {
        val res = transformations.firstOrNull { num in it.source }
            .let { num + (it?.shift ?: 0) }
        // println("$name: $transformations")
        // println("$num -> $res")
        return res
    }
    fun reverse(): Function = Function(
        transformations.map { Transformation(
            LongRange(it.source.first-it.shift, it.source.last-it.shift), -it.shift) })
}

data class Transformation(val source: LongRange, val shift: Long) {
//    fun intersect(other: Transformation): List<Transformation> {
//        if (this.destination.first)
//    }
}

private val Transformation.destination: LongRange
    get() = LongRange(this.source.first+this.shift, this.source.last+this.shift)


//fun merge(f1: Function, f2: Function): Function {
//    f1.transformations.flatMap { f ->
//        f
//    }
//}

fun parseBlock(block: String): Function {
    val lines = block.lines()
    val name = lines.first().substringBefore(":")
    val transformations = lines.drop(1).map {
        parseLine(it)
    }
    return Function(transformations)
}

fun parseLine(line: String): Transformation {
    val (destination, source, length) = line.trim().split(" ").map { it.toLong() }
    return Transformation(LongRange(source, source+length-1), destination-source)
}

fun part1(input: String): Long {
    val blocks = input.split("\n\n")
    val input = blocks.first().split(":")[1].trim().split(" ").map { it.toLong() }
    val functions = blocks.drop(1).map { parseBlock(it) }
    return input.map { input -> functions.fold(input) { num, func -> func.apl(num) } }.min().also { println(it) }
}


fun part2(input: String): Long {

    val blocks = input.split("\n\n")
    val input = blocks.first().split(":")[1].trim().split(" ").map { it.toLong() }
        .chunked(2).map { LongRange(it[0], it[0]+it[1]-1) }

    val functions = blocks.drop(1).map { parseBlock(it) }
    var min = Long.MAX_VALUE
    for (range in input) {
        println(range)
        for (i in range) {
            val v = functions.fold(i) { num, func -> func.apl(num) }
            if (v < min) min = v
        }
    }

    return min.also { println(it) }
}

fun main() {
    val f = Function(listOf(Transformation(LongRange(0, 9), 42)))
    //check(f.reverse().apl(f.apl(6502)) == 6502L)
    check(part1(read(DAY, "test")) == 35L)
    check(part2(read(DAY, "test")) == 46L)

    part1(read(DAY)).let { println("Part 1: $it")}
    part2(read(DAY)).let { println("Part 2: $it")}
}