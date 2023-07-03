package dev.rhyme.pacmanjavafx

interface Movable {
    val position: Position
    val currentDirection: Direction?
    val targetDirection: Direction?

    companion object {
        fun Movable.inGrid(tileSize: Double): Boolean {
            val (x, y) = position
            return x % tileSize == 0.0 && y % tileSize == 0.0
        }
    }
}
