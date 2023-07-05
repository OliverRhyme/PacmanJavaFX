package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.GameMap
import dev.rhyme.pacmanjavafx.Position
import dev.rhyme.pacmanjavafx.ai.RandomTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.utils.resource
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
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

    private val ghostImage = Image(resource("ghost.png"))
    private val scaredGhostImage = Image(resource("scared_ghost.gif"))

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

    override fun GraphicsContext.draw(x: Double, y: Double) {
        if (context.state.powerUpEatenTime != null) {
            drawImage(scaredGhostImage, x, y, tileSize, tileSize)
        } else {
            drawImage(ghostImage, x, y, tileSize, tileSize)
        }
    }
}