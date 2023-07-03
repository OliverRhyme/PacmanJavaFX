package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.Direction.Companion.sameAxisWith
import dev.rhyme.pacmanjavafx.Movable.Companion.inGrid
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class Pacman(
    position: Position,
    context: GameContext,
    private val gameMap: GameMap
) : GameElement(context), Movable {

    override var position: Position = position
        private set

    private val tileSize = context.tileSize
    private val velocity = context.velocity

    override var currentDirection: Direction? = null
        private set
    override var targetDirection: Direction? = null
        private set

    init {
        context.coroutineScope.launch {
            context.keyEventFlow
                .map { it.code }
                .collectLatest(::processKeyPress)
        }
    }

    override fun update() {
        move()
        super.update()
    }

    override fun GraphicsContext.draw() {
        fill = javafx.scene.paint.Color.BLUE
        val (x, y) = position
        fillOval(
            /* x = */ x,
            /* y = */ y,
            /* w = */ tileSize,
            /* h = */ tileSize
        )
    }

    private fun processKeyPress(code: KeyCode) {
        when (code) {
            KeyCode.UP, KeyCode.W -> {
                if (currentDirection sameAxisWith Direction.DOWN) {
                    currentDirection = Direction.UP
                }
                targetDirection = Direction.UP
            }

            KeyCode.DOWN, KeyCode.S -> {
                if (currentDirection sameAxisWith Direction.UP) {
                    currentDirection = Direction.DOWN
                }
                targetDirection = Direction.DOWN
            }

            KeyCode.LEFT, KeyCode.A -> {
                if (currentDirection sameAxisWith Direction.RIGHT) {
                    currentDirection = Direction.LEFT
                }
                targetDirection = Direction.LEFT
            }

            KeyCode.RIGHT, KeyCode.D -> {
                if (currentDirection sameAxisWith Direction.LEFT) {
                    currentDirection = Direction.RIGHT
                }
                targetDirection = Direction.RIGHT
            }

            else -> {}
        }
    }

    private fun move() {
        if (currentDirection != targetDirection && inGrid(tileSize)) {
            // If we are not going to collide, then we can change direction
            // Otherwise, we will just keep going in the current direction
            if (!gameMap.willCollide(this)) {
                currentDirection = targetDirection
            }
        }

        // If we are going to collide, then we don't move
        if (gameMap.willCollide(this)) {
            return
        }

        val (x, y) = position
        position = when (currentDirection) {
            Direction.UP -> position.copy(y = y - velocity)
            Direction.DOWN -> position.copy(y = y + velocity)
            Direction.LEFT -> position.copy(x = x - velocity)
            Direction.RIGHT -> position.copy(x = x + velocity)
            null -> position
        }
    }

}
