package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.elements.*
import dev.rhyme.pacmanjavafx.elements.Movable.Companion.inGrid
import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.utils.imageResource
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image

class GameMap(
    context: GameContext
) : GameElement(context) {

    private val tileSize = context.tileSize

    private val wallImage = Image(imageResource("wall.png"))

    companion object {
        private const val POWER_FOOD = 9
        private const val EMPTY = 8
        private const val GHOST = 4
        private const val PACMAN = 2
        private const val WALL = 1
        private const val FOOD = 0
    }

    // Create map outline, just an empty square, with empty path between
    // 9 is power food
    // 8 is empty
    // 4 is ghost
    // 2 is pacman
    // 1 is a wall
    // 0 is a food
    private val map = arrayOf(
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        intArrayOf(1, 2, 0, 9, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 1),
        intArrayOf(1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 9, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    )

    fun getMapCells(): Pair<Int, Int> {
        return Pair(map[0].size, map.size)
    }

    override fun update() = Unit // nothing to update

    fun getRemainingFood(): Int {
        var foodCount = 0
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == FOOD || map[y][x] == POWER_FOOD) {
                    foodCount++
                }
            }
        }
        return foodCount
    }

    override fun GraphicsContext.draw() {
        for (y in map.indices) {
            for (x in map[y].indices) {
                when (map[y][x]) {
                    WALL -> drawWall(x, y)
                    FOOD -> drawFood(x, y)
                    EMPTY -> drawEmpty(x, y)
                    POWER_FOOD -> drawPowerFood(x, y)
                }

//                // draw grid
//                stroke = javafx.scene.paint.Color.GRAY
//                strokeRect(
//                    x * tileSize,
//                    y * tileSize,
//                    tileSize,
//                    tileSize
//                )
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
        drawImage(
            wallImage,
            x * tileSize,
            y * tileSize,
            tileSize,
            tileSize
        )
    }

    private fun GraphicsContext.drawFood(x: Int, y: Int) {
        val scale = 0.4
        val offset = tileSize * (1 - scale) / 2
        // draw circle 20% of tile size and color yellow
        fill = javafx.scene.paint.Color.YELLOW
        fillOval(
            x * tileSize + offset,
            y * tileSize + offset,
            tileSize * scale,
            tileSize * scale
        )
    }

    private fun GraphicsContext.drawEmpty(x: Int, y: Int) {
        fill = javafx.scene.paint.Color.BLACK
        fillRect(
            x * tileSize,
            y * tileSize,
            tileSize,
            tileSize
        )
    }

    private fun GraphicsContext.drawPowerFood(x: Int, y: Int) {
        val scale = 0.7
        val offset = tileSize * (1 - scale) / 2
        // draw circle 70% of tile size and color yellow
        fill = javafx.scene.paint.Color.BROWN
        fillOval(
            x * tileSize + offset,
            y * tileSize + offset,
            tileSize * scale,
            tileSize * scale
        )
    }

    /**
     * Tries to eat food at given position.
     * @return type of food eaten or null if no food was eaten
     */
    fun tryEat(position: Position): FoodType? {
        if (!position.inGrid(tileSize)) {
            return null
        }

        val (x, y) = position.getMapCell()
        return when (map[y][x]) {
            FOOD -> {
                map[y][x] = EMPTY
                FoodType.NORMAL
            }

            POWER_FOOD -> {
                map[y][x] = EMPTY
                FoodType.POWER_UP
            }

            else -> null
        }

    }

    private fun Position.getMapCell(): Pair<Int, Int> {
        val x = (x / tileSize).toInt()
        val y = (y / tileSize).toInt()
        return x to y
    }
}