package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.GameMap
import dev.rhyme.pacmanjavafx.ai.KeyPressTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.utils.imageResource
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Color

class Pacman(
    override var position: Position,
    context: GameContext,
    private val gameMap: GameMap
) : MovableGameElement(context = context, gameMap = gameMap) {

    private val state get() = context.state

    private val tileSize = context.tileSize

    override val targetDirectionProvider: TargetDirectionProvider = KeyPressTargetDirectionProvider(context)

    private val pacmanImage = Image(imageResource("pacman.gif"))

    override fun update() {
        if (!(state.isGameOver || state.isGameWon)) {
            super.update()
            tryEat()
        }
    }

    private fun tryEat() {
        when (gameMap.tryEat(position)) {
            FoodType.NORMAL -> state.eatFood()
            FoodType.POWER_UP -> state.eatPowerUp()
            else -> Unit // do nothing
        }
    }

    override fun GraphicsContext.draw(x: Double, y: Double) {
        if (!state.isGameOver) {
            val halfSize = tileSize / 2
            save()
            translate(x + halfSize, y + halfSize)
            val rotationAngle = when (currentDirection) {
                Direction.UP -> 270.0
                Direction.DOWN -> 90.0
                Direction.LEFT -> 180.0
                Direction.RIGHT -> 0.0
                else -> 0.0
            }
            rotate(rotationAngle)
            // Draw relative to the canvas movement
            drawImage(pacmanImage, -halfSize, -halfSize, tileSize, tileSize)

            restore()
        } else {
            fill = Color.YELLOW
            fillOval(x, y, tileSize, tileSize)
        }
    }
}
