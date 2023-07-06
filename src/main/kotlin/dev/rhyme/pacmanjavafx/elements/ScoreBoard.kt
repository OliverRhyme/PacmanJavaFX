package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.utils.fontResource
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font

class ScoreBoard(context: GameContext) : GameElement(context) {

    private val marioFont by lazy {
        Font.loadFont(
            fontResource("SuperMario256.ttf"),
            15.0
        )
    }

    override fun update() = Unit // nothing to update

    override fun GraphicsContext.draw() {
        val score = context.state.score
        val message = "Score: $score"

        fill = Color.WHITE
        font = marioFont
        fillText(
            message,
            10.0,
            canvas.height - 20.0
        )
    }
}