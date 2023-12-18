package day17

import day17.Dir.*
import utils.read
import java.util.PriorityQueue
import kotlin.math.min

const val DAY = "day17"

fun main() {
    check(part1(read(DAY, "test")) == 102)
    check(part2(read(DAY, "test")) == 94)

    //part1(read(DAY, "input")).also { println("Part 1: $it") }
    part2(read(DAY, "input")).also { println("Part 2: $it") }
}

enum class Dir {N, S, W, E}
data class State(val pos: Pos, val direction: Dir, val line: Int)

// 1  function Dijkstra(Graph, source):
// 2
// 3      for each vertex v in Graph.Vertices:
// 4          dist[v] ← INFINITY
// 5          prev[v] ← UNDEFINED
// 6          add v to Q
// 7      dist[source] ← 0
// 8
// 9      while Q is not empty:
//10          u ← vertex in Q with min dist[u]
//11          remove u from Q
//12
//13          for each neighbor v of u still in Q:
//14              alt ← dist[u] + Graph.Edges(u, v)
//15              if alt < dist[v]:
//16                  dist[v] ← alt
//17                  prev[v] ← u
//18
//19      return dist[], prev[]

fun part1(input: String): Int {
    val map = input.lines().map {
        it.toCharArray()
    }.toTypedArray()
    val rows = map.size
    val cols = map.first().size
    val start = State(0 to 0, E, 0)
    val queue = mutableListOf(start)
    val dist = mutableMapOf(start to 0)
    //val queue = PriorityQueue<State>(compareBy {dist[it]} )
    fun add(prev: State, new: State, next: Pos) {
        val d = map[next.first][next.second].digitToInt() + dist[prev]!!
        if (d < (dist[new] ?: Int.MAX_VALUE)) {
            dist[new] = d
            queue.add(new)
        }

    }
    while (!queue.isEmpty()) {
        val state = queue.minBy { dist[it] ?: Int.MAX_VALUE }
        queue.remove(state)
        if (state.pos == rows-1 to cols-1) {
            println(state)
            return dist[state]!!
        }
        // South
        var next = state.pos.copy(first = state.pos.first+1)
        if (next.first < rows) {
            if (state.direction in listOf(E, W)) {
                add(state, State(pos = next, direction = S, line = 1), next)
            } else if (state.direction == S && state.line < 3) {
                add(state, State(pos = next, direction = S, line = state.line+1), next)
            }
        }
        // North
        next = state.pos.copy(first = state.pos.first-1)
        if (next.first >= 0) {
            if (state.direction in listOf(E, W)) {
                add(state, State(pos = next, direction = N, line = 1), next)
            } else if (state.direction == N && state.line < 3) {
                add(state, State(pos = next, direction = N, line = state.line+1), next)
            }
        }
        // East
        next = state.pos.copy(second = state.pos.second+1)
        if (next.second < cols) {
            if (state.direction in listOf(N, S)) {
                add(state, State(pos = next, direction = E, line = 1), next)
            } else if (state.direction == E && state.line < 3) {
                add(state, State(pos = next, direction = E, line = state.line+1), next)
            }
        }
        // West
        next = state.pos.copy(second = state.pos.second-1)
        if (next.second >= 0) {
            if (state.direction in listOf(N, S)) {
                add(state, State(pos = next, direction = W, line = 1), next)
            } else if (state.direction == W && state.line < 3) {
                add(state, State(pos = next, direction = W, line = state.line+1), next)
            }
        }

    }
    println(dist.filter { it.key.pos == Pos(rows-1, cols-1) }.values)
    return -1
}

typealias Pos = Pair<Int, Int>
typealias Map = Array<Array<Pos>>


fun part2(input: String): Int {
    val map = input.lines().map {
        it.toCharArray()
    }.toTypedArray()
    val rows = map.size
    val cols = map.first().size
    val start = State(0 to 0, E, 0)
    val queue = mutableListOf(start)
    val dist = mutableMapOf(start to 0)
    //val queue = PriorityQueue<State>(compareBy {dist[it]} )
    fun add(prev: State, new: State, next: Pos) {
        val d = map[next.first][next.second].digitToInt() + dist[prev]!!
        if (d < (dist[new] ?: Int.MAX_VALUE)) {
            dist[new] = d
            queue.add(new)
        }

    }
    while (!queue.isEmpty()) {
        val state = queue.minBy { dist[it] ?: Int.MAX_VALUE }
        queue.remove(state)
        if (state.pos == rows-1 to cols-1 && state.line > 3) {
            println(state)
            return dist[state]!!
        }
        // South
        var next = state.pos.copy(first = state.pos.first+1)
        if (next.first < rows) {
            if (state.direction in listOf(E, W) && state.line > 3) {
                add(state, State(pos = next, direction = S, line = 1), next)
            } else if (state.direction == S && state.line < 10) {
                add(state, State(pos = next, direction = S, line = state.line+1), next)
            }
        }
        // North
        next = state.pos.copy(first = state.pos.first-1)
        if (next.first >= 0) {
            if (state.direction in listOf(E, W) && state.line > 3) {
                add(state, State(pos = next, direction = N, line = 1), next)
            } else if (state.direction == N && state.line < 10) {
                add(state, State(pos = next, direction = N, line = state.line+1), next)
            }
        }
        // East
        next = state.pos.copy(second = state.pos.second+1)
        if (next.second < cols) {
            if (state.direction in listOf(N, S) && state.line > 3) {
                add(state, State(pos = next, direction = E, line = 1), next)
            } else if (state.direction == E && state.line < 10) {
                add(state, State(pos = next, direction = E, line = state.line+1), next)
            }
        }
        // West
        next = state.pos.copy(second = state.pos.second-1)
        if (next.second >= 0) {
            if (state.direction in listOf(N, S) && state.line > 3) {
                add(state, State(pos = next, direction = W, line = 1), next)
            } else if (state.direction == W && state.line < 10) {
                add(state, State(pos = next, direction = W, line = state.line+1), next)
            }
        }

    }
    println(dist.filter { it.key.pos == Pos(rows-1, cols-1) }.values)
    return -1
}
