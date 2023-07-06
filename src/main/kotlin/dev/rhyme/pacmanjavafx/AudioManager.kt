package dev.rhyme.pacmanjavafx

import dev.rhyme.pacmanjavafx.utils.audioResource
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import kotlinx.coroutines.*
import java.lang.Runnable

class AudioManager(
    private val scope: CoroutineScope
) {
    private val wakaAudio = Media(audioResource("waka.wav"))
    private val eatGhost = Media(audioResource("eatGhost.wav"))
    private val gameOver = Media(audioResource("gameOver.wav"))
    private val gameWon = Media(audioResource("gameWon.wav"))
    private val powerFood = Media(audioResource("powerFood.wav"))

    private val wakaPlayer = MediaPlayer(wakaAudio)
    private val eatGhostPlayer = MediaPlayer(eatGhost)
    private val gameOverPlayer = MediaPlayer(gameOver)
    private val gameWonPlayer = MediaPlayer(gameWon)
    private val powerFoodPlayer = MediaPlayer(powerFood)

    fun playWaka() {
        wakaPlayer.playSafe()
    }

    fun playEatGhost() {
        eatGhostPlayer.playSafe()
    }

    fun playGameOver() {
        gameOverPlayer.playSafe()
    }

    fun playGameWon() {
        gameWonPlayer.playSafe()
    }

    private var powerFoodJob: Job? = null

    fun playPowerFood(time: Long) {
        powerFoodJob?.cancel()
        powerFoodJob = scope.launch(Dispatchers.IO) {
            powerFoodPlayer.playFromStart()
            powerFoodPlayer.onEndOfMedia = Runnable {
                powerFoodPlayer.playSafe()
            }

            delay(time)
            powerFoodPlayer.stop()
        }
    }

    fun stopAll() {
        wakaPlayer.stop()
        eatGhostPlayer.stop()
        gameOverPlayer.stop()
        gameWonPlayer.stop()
        powerFoodPlayer.stop()
    }

    private fun MediaPlayer.playSafe() {
        scope.launch(Dispatchers.IO) {
            playFromStart()
        }
    }

    private suspend fun MediaPlayer.playFromStart() {
        withContext(Dispatchers.IO) {
            seek(Duration.ZERO)
            play()
        }
    }
}