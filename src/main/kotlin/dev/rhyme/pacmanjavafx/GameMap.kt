package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.Movable.Companion.inGrid
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext

class GameMap(
    context: GameContext
) : GameElement(context) {

    private val tileSize = context.tileSize

    companion object {
        private const val EMPTY = 8
        private const val GHOST = 4
        private const val PACMAN = 2
        private const val WALL = 1
        private const val FOOD = 0
    }

    // Create map outline, just an empty square, with empty path between
    // 4 is ghost
    // 2 is pacman
    // 1 is a wall
    // 0 is a path
    private val map = arrayOf(
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        intArrayOf(1, 2, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    )

    override fun update() = Unit // nothing to update

    override fun GraphicsContext.draw() {
        for (y in map.indices) {
            for (x in map[y].indices) {
                when (map[y][x]) {
                    WALL -> drawWall(x, y)
                    FOOD -> drawFood(x, y)
                    EMPTY -> drawEmpty(x, y)
                }

                // draw grid
                stroke = javafx.scene.paint.Color.GRAY
                strokeRect(
                    x * tileSize,
                    y * tileSize,
                    tileSize,
                    tileSize
                )
            }
        }
    }

    fun getPacmanInitialPosition(): Position {
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == PACMAN) {
                    return Position(x * context.tileSize, y * context.tileSize)
                }
            }
        }
        error("Pacman not found")
    }

    fun getGhostInitialPositions(): List<Position> {
        return buildList {
            for (y in map.indices) {
                for (x in map[y].indices) {
                    if (map[y][x] == GHOST) {
                        add(Position(x * context.tileSize, y * context.tileSize))
                    }
                }
            }
        }
    }

    fun willCollide(movable: Movable): Boolean {
        val position = movable.position

        // If not in grid, it will not collide
        if (!movable.inGrid(tileSize)) {
            return false
        }

        val targetDirection = movable.targetDirection ?: return false

        val (currentMapCellX, currentMapCellY) = position.getMapCell()

        // Check if will collide based on target direction
        val targetCell = when (targetDirection) {
            Direction.UP -> map[currentMapCellY - 1][currentMapCellX]
            Direction.DOWN -> map[currentMapCellY + 1][currentMapCellX]
            Direction.LEFT -> map[currentMapCellY][currentMapCellX - 1]
            Direction.RIGHT -> map[currentMapCellY][currentMapCellX + 1]
        }

        return targetCell == WALL
    }

    private fun GraphicsContext.drawWall(x: Int, y: Int) {
        fill = javafx.scene.paint.Color.BLACK
        fillRect(
            x * tileSize,
            y * tileSize,
            tileSize,
            tileSize
        )
    }

    private fun GraphicsContext.drawFood(x: Int, y: Int) {

        val scale = 0.5
        val offset = tileSize * (1 - scale) / 2
        // draw circle 80% of tile size and color yellow
        fill = javafx.scene.paint.Color.YELLOW
        fillOval(
            x * tileSize + offset,
            y * tileSize + offset,
            tileSize * scale,
            tileSize * scale
        )
    }

    private fun GraphicsContext.drawEmpty(x: Int, y: Int) {
        fill = javafx.scene.paint.Color.WHITE
        fillRect(
            x * tileSize,
            y * tileSize,
            tileSize,
            tileSize
        )
    }

    fun tryEatFood(position: Position) {
        if (!position.inGrid(tileSize)) {
            return
        }

        val (x, y) = position.getMapCell()
        if (map[y][x] == FOOD) {
            map[y][x] = EMPTY
        }
    }

    fun resizeCanvas(canvas: Canvas) {
        canvas.width = map[0].size * tileSize
        canvas.height = map.size * tileSize
    }

    private fun Position.getMapCell(): Pair<Int, Int> {
        val x = (x / tileSize).toInt()
        val y = (y / tileSize).toInt()
        return x to y
    }
}