package com.goncharov.evgeny.chess.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20

fun clearScreen() {
    Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}