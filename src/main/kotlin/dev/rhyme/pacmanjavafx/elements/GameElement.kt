package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.state.GameContext

abstract class GameElement(
    protected val context: GameContext
) : Drawable {

    open fun tick() {
        update()
        context.drawingContext.draw()
    }

    protected abstract fun update()

}