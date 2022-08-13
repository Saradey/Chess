package com.goncharov.evgeny.chess.interactors

interface SettingsInteractor {

    fun getTextBoardChecked(): String

    fun getTextFirstMovingChecked(): String

    fun savedBoardTheme(theme: String)

    fun savedFirstMoving(firstMoving: String)
}