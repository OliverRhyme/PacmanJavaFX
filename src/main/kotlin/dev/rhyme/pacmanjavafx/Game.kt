package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.elements.EndScreen
import dev.rhyme.pacmanjavafx.elements.Ghost
import dev.rhyme.pacmanjavafx.elements.Pacman
import dev.rhyme.pacmanjavafx.elements.ScoreBoard
import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.state.GameState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class Game(
    private val context: GameContext,
) {

    private val gameState: GameState
        get() = context.state

    init {
        context.coroutineScope.launch {
            context.keyEventFlow.first()
            gameState.gameStarted = true
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

    private val endScreen = EndScreen(
        context = context
    )

    private val scoreBoard = ScoreBoard(
        context = context
    )

    private val gameElements
        get() = buildList {
            add(gameMap)
            add(scoreBoard)
            addAll(ghosts)
            add(pacman)
            add(endScreen)
        }

    private val drawingContext = context.drawingContext

    private fun gameLoop() {
        drawingContext.clearRect(0.0, 0.0, drawingContext.canvas.width, drawingContext.canvas.height)
        checkState()
        gameElements.forEach { it.tick() }
    }

    private fun checkState() {
        if (gameState.isGameOver || gameState.isGameWon) {
            return
        }

        checkCollisions()
        checkFoods()
    }

    private fun checkCollisions() {
        val collidedWith = checkCollidedWith()

        if (collidedWith.isEmpty()) {
            return
        }

        // if we are powered up, we can eat the ghosts
        if (gameState.poweredUpState != GameState.PowerUpState.NORMAL) {
            ghosts.removeAll(collidedWith)
            repeat(collidedWith.size) {
                gameState.eatGhost()
            }
        } else {
            if (collidedWith.isNotEmpty()) {
                gameState.gameOver()
            }
        }
    }

    private fun checkFoods() {
        if (gameMap.getRemainingFood() == 0) {
            gameState.gameWon()
        }
    }

    private fun checkCollidedWith(): Set<Ghost> {
        return ghosts.filterTo(hashSetOf()) { it.collidesWith(pacman) }
    }

    fun resizeCanvas() {
        val (gridX, gridY) = gameMap.getMapCells()
        drawingContext.canvas.width = gridX * context.tileSize
        drawingContext.canvas.height = gridY * context.tileSize + 50
    }

    fun start() {
        context.coroutineScope.launch {
            context.gameLoopTicker.collectLatest {
                gameLoop()
            }
        }
    }
}