package dev.rhyme.pacmanjavafx.elements

import javafx.scene.canvas.GraphicsContext

interface Drawable {
    fun GraphicsContext.draw()
}