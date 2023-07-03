package dev.rhyme.pacmanjavafx

import javafx.scene.canvas.GraphicsContext

abstract class GameElement(
    protected val context: GameContext
): Drawable {

    open fun update() {
       context.drawingContext.draw()
    }

}