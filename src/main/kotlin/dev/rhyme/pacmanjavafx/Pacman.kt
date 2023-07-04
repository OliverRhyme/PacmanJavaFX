package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.ai.KeyPressTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import javafx.scene.canvas.GraphicsContext

class Pacman(
    override var position: Position,
    context: GameContext,
    private val gameMap: GameMap
) : MovableGameElement(context = context, gameMap = gameMap) {

    private val tileSize = context.tileSize

    override val targetDirectionProvider: TargetDirectionProvider = KeyPressTargetDirectionProvider(context)

    override fun update() {
        move()
        gameMap.tryEatFood(position)
        context.drawingContext.draw()
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
