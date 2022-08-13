package com.goncharov.evgeny.chess.controllers

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.logic.PlayerColor

class GameOverControllerImpl(
    private val engine: Engine
) : GameOverController {

    private var messageLabel: Label? = null

    override fun gameOver(removeColor: PlayerColor) {
        engine.getEntitiesFor(allPiecesAndCells).forEach { cell ->
            sprites[cell].sprite.setAlpha(0.5f)
        }
        if (removeColor == PlayerColor.White) {
            messageLabel?.setText(TEXT_GAME_OVER_BLACK_WIN)
        } else {
            messageLabel?.setText(TEXT_GAME_OVER_WHITE_WIN)
        }
        messageLabel?.addAction(
            Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(1f)
            )
        )
    }

    override fun initLabelMessage(label: Label) {
        messageLabel = label
    }
}