package com.goncharov.evgeny.chess.controllers

import com.goncharov.evgeny.chess.logic.PlayerColor

interface GameOverController {

    fun gameOver(removeColor: PlayerColor)
}