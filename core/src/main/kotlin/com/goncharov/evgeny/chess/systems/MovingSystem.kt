package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.goncharov.evgeny.chess.components.DraggedComponent
import com.goncharov.evgeny.chess.components.mappers.*
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.controllers.ChangeOfMovingController
import com.goncharov.evgeny.chess.interactors.DropInteractor
import com.goncharov.evgeny.chess.interactors.GameInteractor

class MovingSystem(
    private val dropInteractor: DropInteractor,
    private val changeOfMovingController: ChangeOfMovingController,
    private val gameInteractor: GameInteractor
) : IteratingSystem(draggedFamily) {

    private val gameEntity by lazy {
        engine.getEntitiesFor(gameFamily).first()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (!Gdx.input.isTouched && !game[gameEntity].isGameOver &&
            dragged[gameEntity].isDragged
        ) {
            val entities = engine.getEntitiesFor(piecesFamily)
            if (dropInteractor.isEmptyBoardPosition(entities)) {
                //moving pieces
                pieces[entity].positionBoard = dropInteractor.getResultPositionBoard()
                val resultPosition = dropInteractor.getResultPosition()
                sprites[entity].sprite.setCenter(resultPosition.x, resultPosition.y)
                gameInteractor.turnChanged()
                changeOfMovingController.showMessageMoved()
                moveWasMade(entity)
            } else if (dropInteractor.thisIsThePlayersFigure(entities, game[gameEntity])) {
                //back pieces
                val cellsList = engine.getEntitiesFor(cellsFamily)
                val cellEntity = cellsList.first { cellEntity ->
                    cells[cellEntity].positionBoard == pieces[entity].positionBoard
                }
                sprites[entity].sprite.setCenter(
                    cells[cellEntity].centrePosition.x,
                    cells[cellEntity].centrePosition.y
                )
                moveWasMade(entity)
            }
        }
    }

    private fun moveWasMade(entity: Entity) {
        dragged[gameEntity].isDragged = false
        layers[entity].layer = SPRITE_LAYER_3
        entity.remove(DraggedComponent::class.java)
    }
}