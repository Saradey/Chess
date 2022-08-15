package com.goncharov.evgeny.chess.controllers

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.goncharov.evgeny.chess.components.mappers.game
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.logic.PlayerColor
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager.Companion.FIRST_MOVING_WHITE_OPTION
import com.goncharov.evgeny.chess.ui.game.GameStage

class ChangeOfMovingControllerImpl(
    private val gameStage: GameStage,
    private val savedSettingsManager: SavedSettingsManager,
    engine: Engine
) : ChangeOfMovingController {

    private val gameComponent by lazy {
        game[engine.getEntitiesFor(gameFamily).first()]
    }

    override fun initMessageMoving() {
        val messageMoving =
            if (savedSettingsManager.getFirstMoving() == FIRST_MOVING_WHITE_OPTION) {
                TEXT_WHITE_PLAYER_MOVING
            } else {
                TEXT_BLACK_PLAYER_MOVING
            }
        gameStage.showMessage(messageMoving)
    }

    override fun showMessageMoved() {
        val messageMoving = if (gameComponent.step == PlayerColor.White) {
            TEXT_WHITE_PLAYER_MOVING
        } else {
            TEXT_BLACK_PLAYER_MOVING
        }
        gameStage.showMessage(messageMoving)
    }
}