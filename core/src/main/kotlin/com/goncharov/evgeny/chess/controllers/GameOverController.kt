package com.goncharov.evgeny.chess.controllers

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.goncharov.evgeny.chess.logic.PlayerColor

interface GameOverController {

    fun gameOver(removeColor: PlayerColor)
}