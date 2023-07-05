package dev.rhyme.pacmanjavafx.state

import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

data class GameContext(
    val tileSize: Double,
    val defaultVelocity: Double,
    val coroutineScope: CoroutineScope,
    val drawingContext: GraphicsContext,
    val keyEventFlow: Flow<KeyEvent>,
    val gameLoopTicker: Flow<Long> // Loop iterations
)
