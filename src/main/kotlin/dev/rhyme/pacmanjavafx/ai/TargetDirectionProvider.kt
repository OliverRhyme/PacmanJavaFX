package dev.rhyme.pacmanjavafx.ai

import dev.rhyme.pacmanjavafx.Direction
import kotlinx.coroutines.flow.Flow

interface TargetDirectionProvider {
    val targetDirectionFlow: Flow<Direction?>
}
