@file:JvmName("Lwjgl3Launcher")

package com.goncharov.evgeny.chess.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.goncharov.evgeny.chess.App

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(App(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("ChessSample")
        setWindowedMode(1280, 720)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
