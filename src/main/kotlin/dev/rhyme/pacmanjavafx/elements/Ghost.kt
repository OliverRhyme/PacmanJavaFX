package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.GameMap
import dev.rhyme.pacmanjavafx.Position
import dev.rhyme.pacmanjavafx.ai.RandomTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import dev.rhyme.pacmanjavafx.state.GameContext
import javafx.scene.canvas.GraphicsContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class Ghost(
    override var position: Position,
    context: GameContext,
    gameMap: GameMap
) : MovableGameElement(context = context, gameMap = gameMap) {
    private val tileSize = context.tileSize

    override val targetDirectionProvider: TargetDirectionProvider =
        RandomTargetDirectionProvider(context.gameLoopTicker)

    private val gameStarted = AtomicBoolean(false)

    init {
        context.coroutineScope.launch {
            context.keyEventFlow.first()
            gameStarted.set(true)
        }
    }

    override fun update() {
        if (gameStarted.get()) {
            super.update()
        }
    }

    override fun GraphicsContext.draw() {
        fill = javafx.scene.paint.Color.RED
        val (x, y) = position
        fillOval(
            /* x = */ x,
            /* y = */ y,
            /* w = */ tileSize,
            /* h = */ tileSize
        )
    }
}