package com.goncharov.evgeny.chess.interactors

import com.goncharov.evgeny.chess.consts.TEXT_BOARD_COLOR_BROWN
import com.goncharov.evgeny.chess.consts.TEXT_BOARD_COLOR_GRAY
import com.goncharov.evgeny.chess.consts.TEXT_FIRST_MOVE_BLACK
import com.goncharov.evgeny.chess.consts.TEXT_FIRST_MOVE_WHITE
import com.goncharov.evgeny.chess.managers.SavedSettingsManager

class SettingsInteractorImpl(
    private val savedSettingsManager: SavedSettingsManager
) : SettingsInteractor {

    override fun getTextBoardChecked(): String {
        return if (savedSettingsManager.getBoardTheme() == SavedSettingsManager.GRAY_BOARD_OPTION) {
            TEXT_BOARD_COLOR_GRAY
        } else {
            TEXT_BOARD_COLOR_BROWN
        }
    }

    override fun getTextFirstMovingChecked(): String {
        return if (savedSettingsManager.getFirstMoving() == SavedSettingsManager.FIRST_MOVING_WHITE_OPTION) {
            TEXT_FIRST_MOVE_WHITE
        } else {
            TEXT_FIRST_MOVE_BLACK
        }
    }

    override fun savedBoardTheme(theme: String) {
        savedSettingsManager.savedBoardTheme(theme)
    }

    override fun savedFirstMoving(firstMoving: String) {
        savedSettingsManager.savedFirstMoving(firstMoving)
    }
}