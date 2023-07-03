package dev.rhyme.pacmanjavafx

import javafx.event.EventHandler
import javafx.scene.input.KeyEvent

class Game(
    context: GameContext,
) {

    private val gameMap = GameMap(context = context)

    private val pacman = Pacman(
        position = gameMap.getPacmanInitialPosition(),
        context = context,
        gameMap = gameMap
    )

    private val gameElements = listOf(
        gameMap,
        pacman
    )

    private val drawingContext = context.drawingContext

    fun gameLoop() {
        drawingContext.clearRect(0.0, 0.0, drawingContext.canvas.width, drawingContext.canvas.height)
        gameElements.forEach { it.update() }
    }

    fun resizeCanvas() {
        gameMap.resizeCanvas(drawingContext.canvas)
    }
}