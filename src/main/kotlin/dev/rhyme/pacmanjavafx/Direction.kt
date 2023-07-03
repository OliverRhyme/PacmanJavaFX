package dev.rhyme.pacmanjavafx

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        infix fun Direction?.sameAxisWith(other: Direction?): Boolean {
            if (this == null || other == null) return false
            return when (this) {
                UP, DOWN -> other == UP || other == DOWN
                LEFT, RIGHT -> other == LEFT || other == RIGHT
            }
        }
    }
}