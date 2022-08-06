package com.goncharov.evgeny.chess.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences

class SavedSettingsManager {

    private val prefs: Preferences by lazy {
        Gdx.app.getPreferences(PREFS_NAME)
    }

    fun getBoardTheme(): String {
        return prefs.getString(BOARD_THEME_KEY, WHITE_BOARD_OPTION)
    }

    fun savedBoardTheme(boardTheme: String) {
        prefs.putString(BOARD_THEME_KEY, boardTheme)
        prefs.flush()
    }

    fun getFirstMoving(): String {
        return prefs.getString(FIRST_MOVING_KEY, FIRST_MOVING_WHITE_OPTION)
    }

    fun savedFirstMoving(firstMoving: String) {
        prefs.putString(FIRST_MOVING_KEY, firstMoving)
        prefs.flush()
    }

    companion object {
        private const val PREFS_NAME = "chessSettingsSaved"

        private const val BOARD_THEME_KEY = "BOARD_THEME_KEY"
        private const val FIRST_MOVING_KEY = "FIRST_MOVING_KEY"

        const val BLACK_BOARD_OPTION = "blackBoard"
        const val WHITE_BOARD_OPTION = "whiteBoard"

        const val FIRST_MOVING_WHITE_OPTION = "firstMovingWhite"
        const val FIRST_MOVING_BLACK_OPTION = "firstMovingBlack"
    }
}