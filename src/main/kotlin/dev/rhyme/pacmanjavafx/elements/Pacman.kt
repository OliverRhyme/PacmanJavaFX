package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.GameMap
import dev.rhyme.pacmanjavafx.Position
import dev.rhyme.pacmanjavafx.ai.KeyPressTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import dev.rhyme.pacmanjavafx.state.GameContext
import javafx.scene.canvas.GraphicsContext

class Pacman(
    override var position: Position,
    context: GameContext,
    private val gameMap: GameMap
) : MovableGameElement(context = context, gameMap = gameMap) {

    private val tileSize = context.tileSize

    override val targetDirectionProvider: TargetDirectionProvider = KeyPressTargetDirectionProvider(context)

    override fun update() {
        super.update()
        gameMap.tryEatFood(position)
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
}
