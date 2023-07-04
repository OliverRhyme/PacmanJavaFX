package dev.rhyme.pacmanjavafx.ai

import dev.rhyme.pacmanjavafx.Direction
import dev.rhyme.pacmanjavafx.GameContext
import javafx.scene.input.KeyCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class KeyPressTargetDirectionProvider(
    context: GameContext
) : TargetDirectionProvider {

    override val targetDirectionFlow: Flow<Direction?> = context.keyEventFlow
        .map { it.code }
        .map(::toDirection)
        .filterNotNull()

    private fun toDirection(code: KeyCode): Direction? {
        return when (code) {
            KeyCode.UP, KeyCode.W -> Direction.UP
            KeyCode.DOWN, KeyCode.S -> Direction.DOWN
            KeyCode.LEFT, KeyCode.A -> Direction.LEFT
            KeyCode.RIGHT, KeyCode.D -> Direction.RIGHT
            else -> null
        }
    }
}