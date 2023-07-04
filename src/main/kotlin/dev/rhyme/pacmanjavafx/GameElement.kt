package dev.rhyme.pacmanjavafx

abstract class GameElement(
    protected val context: GameContext
): Drawable {

    open fun tick() {
        update()
        context.drawingContext.draw()
    }

    protected abstract fun update()

}