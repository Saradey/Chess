package com.goncharov.evgeny.chess.controllers

import com.goncharov.evgeny.chess.logic.PlayerColor

interface GameInteractor {

    fun turnChanged()

    fun checkingThePlayer(type: PlayerColor): Boolean
}