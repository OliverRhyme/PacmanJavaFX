package dev.rhyme.pacmanjavafx

import javafx.scene.canvas.GraphicsContext

class Game(
    private val context: GraphicsContext
) {
    companion object {
        const val tileSize = 32
    }

    val gameMap = GameMap(tileSize)


    fun gameLoop() {
      gameMap.draw(context)
    }
}