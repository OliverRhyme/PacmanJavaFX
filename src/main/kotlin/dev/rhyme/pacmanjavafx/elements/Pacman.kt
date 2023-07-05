package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.GameMap
import dev.rhyme.pacmanjavafx.Position
import dev.rhyme.pacmanjavafx.ai.KeyPressTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.utils.resource
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image

class Pacman(
    override var position: Position,
    context: GameContext,
    private val gameMap: GameMap
) : MovableGameElement(context = context, gameMap = gameMap) {

    private val state get() = context.state

    private val tileSize = context.tileSize

    override val targetDirectionProvider: TargetDirectionProvider = KeyPressTargetDirectionProvider(context)

    private val pacmanImage = Image(resource("pacman.gif"))

    override fun update() {
        super.update()
        tryEat()
    }

    private fun tryEat() {
        when (gameMap.tryEat(position)) {
            FoodType.NORMAL -> state.score++
            FoodType.POWER_UP -> {
                state.score += 10
                state.powerUpEatenTime = System.currentTimeMillis()
            }

            else -> Unit // do nothing
        }
    }

    override fun GraphicsContext.draw(x: Double, y: Double) {
        drawImage(pacmanImage, x, y, tileSize, tileSize)
    }
}
