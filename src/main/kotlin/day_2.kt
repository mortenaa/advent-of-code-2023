import java.io.File

val testData = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""".trimIndent()

data class Draw(val red: Int, val green: Int, val blue: Int)
data class Game(val index: Int, val draws: List<Draw>)

fun main() {
    println(parseData(testData))
    println(part1(Draw(12, 13, 14), parseData(testData)))

    val input = File("src/main/kotlin/day_2_data.txt").readText()
    println(part1(Draw(12, 13, 14), parseData(input)))

    println(part2(parseData(testData)))
    println(part2(parseData(input)))

}

fun part1(limit: Draw, games: List<Game>): Int {
    return games.filter { it.draws.all { possible(limit, it) } }
        .sumOf { it.index }
}

fun part2(games: List<Game>): Long {
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
        (r * g * b).toLong()
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