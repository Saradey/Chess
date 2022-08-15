package com.goncharov.evgeny.chess.ui.game

interface GameStage {

    fun showMessage(message: String)

    fun showEndGame(messageEndGame: String)
}