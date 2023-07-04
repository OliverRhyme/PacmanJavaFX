package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.ai.RandomTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import javafx.scene.canvas.GraphicsContext

class Ghost(
    override var position: Position,
    context: GameContext,
    gameMap: GameMap
) : MovableGameElement(context = context, gameMap = gameMap) {

    private val tileSize = context.tileSize

    override val targetDirectionProvider: TargetDirectionProvider = RandomTargetDirectionProvider()

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