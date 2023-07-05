package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.utils.keyPressFlow
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

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
            defaultVelocity = velocity,
            coroutineScope = scope,
            drawingContext = gameCanvas.graphicsContext2D,
            keyEventFlow = gameCanvas.keyPressFlow().shareIn(scope, SharingStarted.Lazily),
            gameLoopTicker = flow {
                var i = 0L
                while (currentCoroutineContext().isActive) {
                    emit(i++)
                    delay(1000 / 60)
                }
            }.shareIn(scope, SharingStarted.Lazily, 1)
        )

        val game = Game(gameContext)
        game.resizeCanvas()
        game.start()
    }

}