package dev.rhyme.pacmanjavafx

import javafx.scene.canvas.GraphicsContext

interface Drawable {
    fun GraphicsContext.draw()
}