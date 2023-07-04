package dev.rhyme.pacmanjavafx.ai

import dev.rhyme.pacmanjavafx.Direction
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

class RandomTargetDirectionProvider(
    private val randomDelayRangeSec: IntRange = 3..10
) : TargetDirectionProvider {

    override val targetDirectionFlow: Flow<Direction> = flow {
        while (currentCoroutineContext().isActive) {
            emit(Direction.values().random())
            delay((randomDelayRangeSec).random() * 1000L)
        }
    }
}
