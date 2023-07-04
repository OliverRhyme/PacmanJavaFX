package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.Direction.Companion.sameAxisWith
import dev.rhyme.pacmanjavafx.Movable.Companion.inGrid
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class MovableGameElement(
    context: GameContext,
    private val velocity: Double = context.defaultVelocity,
    private val gameMap: GameMap
) : GameElement(context), Movable {

    protected abstract val targetDirectionProvider: TargetDirectionProvider

    private val tileSize get() = context.tileSize

    abstract override var position: Position
        protected set

    override var targetDirection: Direction? = null
        protected set

    override var currentDirection: Direction? = null
        protected set

    init {
        context.coroutineScope.launch {
            targetDirectionProvider.targetDirectionFlow.collectLatest(::processTargetDirection)
        }
    }

    open fun processTargetDirection(target: Direction?) {
        if (currentDirection sameAxisWith target) {
            currentDirection = target
        }
        targetDirection = target
    }

    override fun update() {
        move()
    }

    protected open fun move() {
        if (currentDirection != targetDirection && inGrid(tileSize)) {
            // If we are not going to collide, then we can change direction
            // Otherwise, we will just keep going in the current direction
            if (!gameMap.willCollide(this)) {
                currentDirection = targetDirection
            } else {
                targetDirection = currentDirection
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
