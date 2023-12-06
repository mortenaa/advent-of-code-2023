package day6

val test = """
    Time:      7  15   30
    Distance:  9  40  200
""".trimIndent()

val input = """
    Time:        46     82     84     79
    Distance:   347   1522   1406   1471
""".trimIndent()

fun part1(input: String): Int {
    val times = input.lines()[0].ints()
    val distance = input.lines()[1].ints()
    val races = times.zip(distance) {a, b -> a to b}
    return races.map {
        solutions(it.first, it.second)
    }.reduce { a, i -> a*i }
}

fun part2(input: String): Long {
    val time = input.lines()[0].long()
    val distance = input.lines()[1].long()
    return solutionsL(time, distance)
}

fun solutions(time: Int, distance: Int): Int {
    return (0 .. time).map { dist(it, time)}.filter { it > distance }.count()
}

fun distL(hold: Long, time: Long): Long {
    return hold * (time - hold)
}

fun solutionsL(time: Long, distance: Long): Long {
    var p = 0L
    (0L .. time).forEach {
        val v = distL(it, time)
        if (v > distance) {
            p += 1
        }
    }
    return p
}

fun dist(hold: Int, time: Int): Int {
    return hold * (time - hold)
}
private fun String.ints(): List<Int> =
    this.substringAfter(":").trim().split("\\s+".toRegex()).map { it.toInt() }

private fun String.long(): Long =
    this.substringAfter(":").trim().split("\\s+".toRegex()).joinToString("").toLong()

fun main() {
    part1(test).also { println(it) }
    part1(input).also { println(it) }
    part2(test).also { println(it) }
    part2(input).also { println(it) }
}

