package com.goncharov.evgeny.chess.controllers

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.goncharov.evgeny.chess.components.mappers.game
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.logic.Step
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager.Companion.FIRST_MOVING_WHITE_OPTION

class ChangeOfMovingControllerImpl(
    private val savedSettingsManager: SavedSettingsManager,
    engine: Engine
) : ChangeOfMovingController {

    private var label: Label? = null
    private val gameComponent by lazy {
        game[engine.getEntitiesFor(gameFamily).first()]
    }

    override fun initLabelMessage(label: Label) {
        this.label = label
    }

    override fun initMoving() {
        if (savedSettingsManager.getFirstMoving() == FIRST_MOVING_WHITE_OPTION) {
            label?.setText(TEXT_WHITE_PLAYER_MOVING)
        } else {
            label?.setText(TEXT_BLACK_PLAYER_MOVING)
        }
        showAnimationMessageMoving()
    }

    override fun changeMoving() {
        if (gameComponent.step == Step.WhiteStep) {
            label?.setText(TEXT_WHITE_PLAYER_MOVING)
        } else {
            label?.setText(TEXT_BLACK_PLAYER_MOVING)
        }
        showAnimationMessageMoving()
    }

    private fun showAnimationMessageMoving() {
        label?.addAction(
            Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(1f),
                Actions.fadeOut(1f)
            )
        )
        label?.addAction(
            Actions.sequence(
                Actions.moveTo(
                    X_MOVE_START_POSITION,
                    Y_MOVE_START_POSITION
                ),
                Actions.moveTo(
                    X_MOVE_START_POSITION,
                    Y_MOVE_POSITION,
                    2f
                )
            )
        )
    }

    companion object {
        private const val X_MOVE_START_POSITION = UI_WIDTH / 2f - 109f
        private const val Y_MOVE_START_POSITION = UI_HEIGHT / 2f - 10f
        private const val Y_MOVE_POSITION = UI_HEIGHT / 2f + 10f
    }
}