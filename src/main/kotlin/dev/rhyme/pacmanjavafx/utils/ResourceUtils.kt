package dev.rhyme.pacmanjavafx.utils

import dev.rhyme.pacmanjavafx.PacmanApplication

fun imageResource(path: String): String = PacmanApplication::class.java.getResource("images/$path")!!.toExternalForm()

fun audioResource(path: String): String = PacmanApplication::class.java.getResource("audio/$path")!!.toExternalForm()