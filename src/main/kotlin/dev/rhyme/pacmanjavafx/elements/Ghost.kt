package dev.rhyme.pacmanjavafx.elements

import dev.rhyme.pacmanjavafx.GameMap
import dev.rhyme.pacmanjavafx.Position
import dev.rhyme.pacmanjavafx.ai.RandomTargetDirectionProvider
import dev.rhyme.pacmanjavafx.ai.TargetDirectionProvider
import dev.rhyme.pacmanjavafx.state.GameContext
import dev.rhyme.pacmanjavafx.state.GameState
import dev.rhyme.pacmanjavafx.utils.resource
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image

class Ghost(
    override var position: Position,
    context: GameContext,
    gameMap: GameMap
) : MovableGameElement(context = context, gameMap = gameMap) {
    private val tileSize = context.tileSize

    override val targetDirectionProvider: TargetDirectionProvider =
        RandomTargetDirectionProvider(context.gameLoopTicker)

    private val ghostImage = Image(resource("ghost.png"))
    private val scaredAlmostDoneGhostImage = Image(resource("scared_ghost_almost_done.gif"))
    private val scaredGhostImage = Image(resource("scared_ghost.png"))

    override fun update() {
        val state = context.state
        if (state.gameStarted && !state.isGameOver) {
            super.update()
        }
    }

    override fun GraphicsContext.draw(x: Double, y: Double) {
        when (context.state.poweredUpState) {
            GameState.PowerUpState.NORMAL -> drawImage(ghostImage, x, y, tileSize, tileSize)
            GameState.PowerUpState.POWERED_UP -> drawImage(scaredGhostImage, x, y, tileSize, tileSize)
            GameState.PowerUpState.POWERED_UP_ENDING -> drawImage(scaredAlmostDoneGhostImage, x, y, tileSize, tileSize)
        }
    }
}