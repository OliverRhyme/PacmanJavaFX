package dev.rhyme.pacmanjavafx.state

import dev.rhyme.pacmanjavafx.AudioManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameState(private val scope: CoroutineScope) {

    private val audioManager = AudioManager(scope)

    companion object {
        private const val GHOST_SCORE = 100
        private const val POWER_UP_SCORE = 50
        private const val AFRAID_TIME = 5000L
        private const val AFRAID_ENDING_TIME = 3000L
    }

    enum class PowerUpState {
        NORMAL, POWERED_UP, POWERED_UP_ENDING
    }

    private var poweredUpStateJob: Job? = null

    var poweredUpState: PowerUpState = PowerUpState.NORMAL
        private set

    var score = 0
        private set

    var isGameOver: Boolean = false
        private set

    var gameStarted: Boolean = false

    var isGameWon: Boolean = false
        private set

    fun gameWon() {
        reset()
        isGameWon = true
        audioManager.playGameWon()
    }

    fun gameOver() {
        reset()
        isGameOver = true
        audioManager.playGameOver()
    }

    fun eatPowerUp() {
        poweredUpStateJob?.cancel()
        poweredUpStateJob = scope.launch {
            poweredUpState = PowerUpState.POWERED_UP
            delay(AFRAID_ENDING_TIME)
            poweredUpState = PowerUpState.POWERED_UP_ENDING
            delay(AFRAID_TIME - AFRAID_ENDING_TIME)
            poweredUpState = PowerUpState.NORMAL
        }
        score += POWER_UP_SCORE
        audioManager.playPowerFood(AFRAID_TIME)
    }

    fun eatFood() {
        score++
        audioManager.playWaka()
    }

    fun eatGhost() {
        score += POWER_UP_SCORE
        audioManager.playEatGhost()
    }

    fun reset() {
        poweredUpStateJob?.cancel()
        poweredUpStateJob = null
        score = 0
        isGameOver = false
        poweredUpState = PowerUpState.NORMAL
        gameStarted = false
    }
}
