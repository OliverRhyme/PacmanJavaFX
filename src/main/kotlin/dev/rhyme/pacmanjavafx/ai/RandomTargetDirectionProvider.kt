package dev.rhyme.pacmanjavafx.ai

import dev.rhyme.pacmanjavafx.elements.Direction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class RandomTargetDirectionProvider(
    ticker: Flow<Long>,
    private val defaultTicksBeforeChange: Int = (10..25).random()
) : TargetDirectionProvider {

    override val targetDirectionFlow: Flow<Direction> = ticker.transform {
        if (it % defaultTicksBeforeChange == 0L) {
            emit(Direction.values().random())
        }
    }

}
