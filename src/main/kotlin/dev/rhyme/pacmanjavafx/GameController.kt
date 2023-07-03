package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.utils.keyPressFlow
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.javafx.JavaFx

class GameController {

    companion object {
        const val tileSize = 32.0
        const val velocity = 1.0
    }

    private val scope = MainScope()

    @FXML
    private lateinit var gameCanvas: Canvas

    @FXML
    private fun initialize() {
        val gameContext = GameContext(
            tileSize = tileSize,
            velocity = velocity,
            coroutineScope = scope,
            drawingContext = gameCanvas.graphicsContext2D,
            keyEventFlow = gameCanvas.keyPressFlow()
        )

        val game = Game(gameContext)
        game.resizeCanvas()

        scope.launch {
            while (isActive) {
                game.gameLoop()
                delay(1000 / 60)
            }
        }
    }

}