package dev.rhyme.pacmanjavafx

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Game(
    private val context: GameContext,
) {

    private val gameMap = GameMap(context = context)

    private val pacman = Pacman(
        position = gameMap.getPacmanInitialPosition(),
        context = context,
        gameMap = gameMap
    )

    private val ghosts = gameMap.getGhostInitialPositions().map {
        Ghost(
            position = it,
            context = context,
            gameMap = gameMap
        )
    }

    private val gameElements = buildList {
        add(gameMap)
        addAll(ghosts)
        add(pacman)
    }

    private val drawingContext = context.drawingContext

    private fun gameLoop() {
        drawingContext.clearRect(0.0, 0.0, drawingContext.canvas.width, drawingContext.canvas.height)
        gameElements.forEach { it.update() }
    }

    fun resizeCanvas() {
        gameMap.resizeCanvas(drawingContext.canvas)
    }

    fun start() {
        context.coroutineScope.launch {
            context.gameLoopTicker.collectLatest {
                gameLoop()
            }
        }
    }
}