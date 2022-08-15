package com.goncharov.evgeny.chess.controllers

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.logic.PlayerColor
import com.goncharov.evgeny.chess.ui.game.GameStage

class GameOverControllerImpl(
    private val engine: Engine,
    private val gameStage: GameStage
) : GameOverController {

    override fun gameOver(removeColor: PlayerColor) {
        engine.getEntitiesFor(allPiecesAndCells).forEach { cell ->
            sprites[cell].sprite.setAlpha(0.5f)
        }
        if (removeColor == PlayerColor.White) {
            gameStage.showEndGame(TEXT_GAME_OVER_BLACK_WIN)
        } else {
            gameStage.showEndGame(TEXT_GAME_OVER_WHITE_WIN)
        }
    }
}