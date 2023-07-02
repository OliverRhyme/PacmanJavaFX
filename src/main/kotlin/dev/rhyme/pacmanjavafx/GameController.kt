package dev.rhyme.pacmanjavafx

import javafx.fxml.FXML
import javafx.scene.canvas.Canvas

class GameController {
    @FXML
    private lateinit var gameCanvas: Canvas


    @FXML
    private fun initialize() {
        val game = Game(gameCanvas.graphicsContext2D)
        game.gameLoop()
    }

}