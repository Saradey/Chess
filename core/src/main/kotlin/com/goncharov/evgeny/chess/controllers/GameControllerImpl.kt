package com.goncharov.evgeny.chess.controllers

import com.badlogic.ashley.core.Engine
import com.goncharov.evgeny.chess.components.mappers.game
import com.goncharov.evgeny.chess.consts.gameFamily
import com.goncharov.evgeny.chess.logic.Step

class GameControllerImpl(
    engine: Engine
) : GameController {

    private val gameComponent by lazy {
        game[engine.getEntitiesFor(gameFamily).first()]
    }

    override fun turnChanged() {
        gameComponent.step = if (gameComponent.step == Step.WhiteStep) {
            Step.BlackStep
        } else {
            Step.WhiteStep
        }
    }
}