package dev.rhyme.pacmanjavafx.utils

import dev.rhyme.pacmanjavafx.PacmanApplication

fun resource(path: String) = PacmanApplication::class.java.getResource(path)!!.toExternalForm()