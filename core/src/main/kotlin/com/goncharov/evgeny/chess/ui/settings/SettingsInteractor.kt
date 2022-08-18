package com.goncharov.evgeny.chess.ui.settings

interface SettingsInteractor {

    fun getTextBoardChecked(): String

    fun getTextFirstMovingChecked(): String

    fun savedBoardTheme(theme: String)

    fun savedFirstMoving(firstMoving: String)
}