package dev.rhyme.pacmanjavafx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class PacmanApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(PacmanApplication::class.java.getResource("pacman-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 480.0, 530.0)
        stage.title = "Pacman"
        stage.scene = scene
        stage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(PacmanApplication::class.java, *args)
}