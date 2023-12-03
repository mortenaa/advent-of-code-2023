package utils

import kotlin.io.path.Path
import kotlin.io.path.readText

fun read(day: String, name: String = "input"): String =
    Path("src/main/kotlin/$day/$name.txt").readText()

fun Any?.println() = println(this)
