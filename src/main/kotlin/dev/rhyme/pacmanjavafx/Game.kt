package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.elements.Ghost
import dev.rhyme.pacmanjavafx.elements.Pacman
import dev.rhyme.pacmanjavafx.state.GameContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Game(
    private val context: GameContext,
) {

    companion object {
        const val AFRAID_TIME = 5000L
        const val AFRAID_ALMOST_DONE_TIME = 3000L
    }

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
        checkState()
        gameElements.forEach { it.tick() }
    }

    private fun checkState() {
        val currentTime = System.currentTimeMillis()
        val eatenTime = context.state.powerUpEatenTime

        if (eatenTime != null) {
            val timePassed = currentTime - eatenTime
            if (timePassed > AFRAID_TIME) {
                context.state.powerUpEatenTime = null
            }
        }
    }

    fun resizeCanvas() {
        val (gridX, gridY) = gameMap.getMapCells()
        drawingContext.canvas.width = gridX * context.tileSize
        drawingContext.canvas.height = gridY * context.tileSize
    }

    fun start() {
        context.coroutineScope.launch {
            context.gameLoopTicker.collectLatest {
                gameLoop()
            }
        }
    }
}