package day2

import utils.read
import java.io.File

val DAY = "day2"

data class Draw(val red: Int, val green: Int, val blue: Int)
data class Game(val index: Int, val draws: List<Draw>)

fun main() {
    check(part1(Draw(12, 13, 14), parseData(read(DAY, "test"))) == 8)
    check(part2(parseData(read(DAY, "test"))) == 2286)

    println(part1(Draw(12, 13, 14), parseData(read(DAY, "input"))))
    println(part2(parseData(read(DAY, "input"))))

}

fun part1(limit: Draw, games: List<Game>): Int {
    return games.filter { it.draws.all { possible(limit, it) } }
        .sumOf { it.index }
}

fun part2(games: List<Game>): Int {
    return games.map {
        val draws = it.draws
        var r = 0
        var g = 0
        var b = 0
        draws.forEach {
            if (it.red > r) r = it.red
            if (it.green > g) g = it.green
            if (it.blue > b) b = it.blue
        }
        (r * g * b)
    }.sumOf { it }
}

fun possible(limit: Draw, actual: Draw) =
    actual.red <= limit.red && actual.green <= limit.green && actual.blue <= limit.blue

fun parseData(input: String): List<Game> {
    return input.lines().map { it.trim() }
        .map {
            val (head, rest) = it.split(":")
            val index = head.split(" ")[1].trim().toInt()
            val draws = rest.split(";")
            val d = draws.map {
                val m = it.split(",").associate {
                    val (num, colour) = it.trim().split(" ")
                    colour.trim() to num.trim().toInt()
                }.toMap()
                Draw(m.getOrDefault("red", 0), m.getOrDefault("green", 0), m.getOrDefault("blue", 0))
            }
            Game(index, d)
        }
}