package dev.rhyme.pacmanjavafx.state

data class GameState(
    var powerUpEatenTime: Long? = null, // null means no power up is active
    var score: Int = 0,
)
