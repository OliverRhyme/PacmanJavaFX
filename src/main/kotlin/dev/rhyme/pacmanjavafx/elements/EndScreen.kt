package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.utils.fontResource
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font

class EndScreen(
    context: GameContext
) : GameElement(context) {

    private val marioFont by lazy {
        Font.loadFont(
            fontResource("SuperMario256.ttf"),
            50.0
        )
    }

    private val marioFontSmall by lazy {
        Font.loadFont(
            fontResource("SuperMario256.ttf"),
            15.0
        )
    }

    override fun update() = Unit // nothing to update

    override fun GraphicsContext.draw() {
        val state = context.state

        val message = if (state.isGameWon) "You Win!"
        else if (state.isGameOver) "You Lose!"
        else return

        val score = "Score: ${state.score}"

        // center the background
        val backgroundHeight = 100.0
        fill = Color.BLACK
        fillRect(
            0.0,
            (canvas.height - backgroundHeight) / 2,
            canvas.width,
            backgroundHeight
        )
        fill = Color.WHITE
        font = marioFont
        fillText(
            message,
            (canvas.width - message.length * 30) / 2,
            (canvas.height + 20) / 2
        )
        font = marioFontSmall
        fillText(
            score,
            (canvas.width - score.length * 10) / 2,
            (canvas.height + 70) / 2
        )
    }
}
