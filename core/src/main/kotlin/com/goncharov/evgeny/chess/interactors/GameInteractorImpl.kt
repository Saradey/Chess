package com.goncharov.evgeny.chess.interactors

import com.badlogic.ashley.core.Engine
import com.goncharov.evgeny.chess.components.mappers.game
import com.goncharov.evgeny.chess.consts.gameFamily
import com.goncharov.evgeny.chess.logic.PlayerColor

class GameInteractorImpl(
    engine: Engine
) : GameInteractor {

    private val gameComponent by lazy {
        game[engine.getEntitiesFor(gameFamily).first()]
    }

    override fun turnChanged() {
        gameComponent.step = if (gameComponent.step == PlayerColor.White) {
            PlayerColor.Black
        } else {
            PlayerColor.White
        }
    }

    override fun checkingThePlayer(type: PlayerColor): Boolean {
        return gameComponent.step == type
    }
}