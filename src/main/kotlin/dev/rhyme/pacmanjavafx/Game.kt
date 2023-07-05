package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.elements.Ghost
import dev.rhyme.pacmanjavafx.elements.Pacman
import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.state.GameState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class Game(
    private val context: GameContext,
) {

    init {
        context.coroutineScope.launch {
            context.keyEventFlow.first()
            context.state.gameStarted = true
        }
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
    }.toMutableSet()

    private val gameElements
        get() = buildList {
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
        val collidedWith = checkCollidedWith()

        if (collidedWith.isEmpty()) {
            return
        }

        // if we are powered up, we can eat the ghosts
        if (context.state.poweredUpState != GameState.PowerUpState.NORMAL) {
            ghosts.removeAll(collidedWith)
        } else {
            if (collidedWith.isNotEmpty()) {
                context.state.gameOver()
                // TODO: show game over screen
            }
        }
    }

    private fun checkCollidedWith(): Set<Ghost> {
        return ghosts.filterTo(hashSetOf()) { it.collidesWith(pacman) }
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