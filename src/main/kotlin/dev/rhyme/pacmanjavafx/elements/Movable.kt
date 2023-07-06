package dev.rhyme.pacmanjavafx.elements

interface Movable {
    val position: Position
    val currentDirection: Direction?
    val targetDirection: Direction?

    companion object {
        fun Movable.inGrid(tileSize: Double): Boolean {
            return position.inGrid(tileSize)
        }

        fun Position.inGrid(tileSize: Double): Boolean {
            val (x, y) = this
            return x % tileSize == 0.0 && y % tileSize == 0.0
        }
    }

    fun collidesWith(movable: Movable): Boolean
}
