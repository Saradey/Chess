package com.goncharov.evgeny.chess.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.goncharov.evgeny.chess.components.DraggedComponent
import com.goncharov.evgeny.chess.components.GameComponent
import com.goncharov.evgeny.chess.logic.PlayerColor
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager.Companion.FIRST_MOVING_WHITE_OPTION

class GameFactory(
    private val savedSettingsManager: SavedSettingsManager,
    private val engine: Engine
) {

    fun initialGame() {
        val entityGame = Entity()
        val gameComponent = GameComponent(getFirstStep())
        entityGame.add(gameComponent)
        val draggedComponent = DraggedComponent()
        entityGame.add(draggedComponent)
        engine.addEntity(entityGame)
    }

    private fun getFirstStep() =
        if (savedSettingsManager.getFirstMoving() == FIRST_MOVING_WHITE_OPTION) {
            PlayerColor.White
        } else {
            PlayerColor.Black
        }
}