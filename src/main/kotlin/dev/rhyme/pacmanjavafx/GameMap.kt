package dev.rhyme.pacmanjavafx

import javafx.scene.canvas.GraphicsContext
import kotlinx.coroutines.runBlocking

class GameMap(
    private val tileSize: Int,
) {

    val map = arrayOf(
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 1, 1, 1, 1, 1, 1, 0, 1),
        intArrayOf(1, 0, 1, 0, 0, 0, 0, 1, 0, 1),
        intArrayOf(1, 0, 1, 0, 1, 1, 0, 1, 0, 1),
        intArrayOf(1, 0, 1, 0, 1, 1, 0, 1, 0, 1),
        intArrayOf(1, 0, 1, 0, 0, 0, 0, 1, 0, 1),
        intArrayOf(1, 0, 1, 1, 1, 1, 1, 1, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
    )

    fun draw(context: GraphicsContext) {
        runBlocking {  }
        for (y in map.indices) {
            for (x in map[y].indices) {
                when (map[y][x]) {
                    1 -> context.fillRect(
                        x * tileSize.toDouble(),
                        y * tileSize.toDouble(),
                        tileSize.toDouble(),
                        tileSize.toDouble()
                    )
                }
            }
        }
    }
}