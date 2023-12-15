package day15

import utils.read

const val DAY = "day15"

fun main() {
    check(part1(read(DAY, "test")) == 1320)
    check(part2(read(DAY, "test")) == 145)

    part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

fun part1(input: String): Int {
    return input.split((",")).sumOf { hash(it) }
}

fun hash(s: String): Int {
    var current = 0
    for (c in s) {
        current +=  c.code
        current *= 17
        current %= 256
    }
    return current
}

sealed interface Step

data class Add(val label: String, val value: Int) : Step

data class Remove(val label: String) : Step

data class Lens(val label: String, val value: Int)

fun parse(input: String): List<Step> {
    return input.trim().split(",").map {
        if (it.contains('=')) {
            Add(it.substringBefore('='), it.substringAfter('=').toInt())
        } else {
            Remove(it.substringBefore('-'))
        }
    }
}

fun part2(input: String): Int {
    val steps = parse(input)
    val table = Array<MutableList<Lens>>(256) { mutableListOf() }
    for (step in steps) {
        when (step) {
            is Add -> {
                table[hash(step.label)].let {
                    val i = it.indexOfFirst { it.label == step.label }
                    if (i>=0)
                        it[i] = Lens(step.label, step.value)
                    else
                        it.add(Lens(step.label, step.value))
                }
            }
            is Remove -> {
                table[hash(step.label)].removeIf { it.label == step.label }
            }
        }
    }
    return table.withIndex().sumOf {
        val i = it.index+1
        it.value.withIndex().sumOf { i * (it.index+1) * it.value.value }
    }
}