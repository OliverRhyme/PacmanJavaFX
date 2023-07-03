package dev.rhyme.pacmanjavafx.utils

import javafx.scene.Node
import javafx.scene.input.KeyEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

fun Node.keyPressFlow(): Flow<KeyEvent> = callbackFlow<KeyEvent> {
    setOnKeyPressed {
        trySend(it)
    }

    awaitClose {
        onKeyPressed = null
    }
}.conflate()
