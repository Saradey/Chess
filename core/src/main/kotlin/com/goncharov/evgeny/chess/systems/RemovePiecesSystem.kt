package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.goncharov.evgeny.chess.components.DraggedComponent
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.RemovedPiecesComponent
import com.goncharov.evgeny.chess.components.mappers.*
import com.goncharov.evgeny.chess.consts.SPRITE_LAYER_3
import com.goncharov.evgeny.chess.consts.draggedFamily
import com.goncharov.evgeny.chess.consts.gameFamily
import com.goncharov.evgeny.chess.consts.piecesFamily
import com.goncharov.evgeny.chess.controllers.ChangeOfMovingController
import com.goncharov.evgeny.chess.controllers.GameOverController
import com.goncharov.evgeny.chess.interactors.DropInteractor
import com.goncharov.evgeny.chess.interactors.GameInteractor

class RemovePiecesSystem(
    private val dropInteractor: DropInteractor,
    private val changeOfMovingController: ChangeOfMovingController,
    private val gameInteractor: GameInteractor,
    private val gameOverController: GameOverController
) : IteratingSystem(draggedFamily) {

    private val gameEntity by lazy {
        engine.getEntitiesFor(gameFamily).first()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (!Gdx.input.isTouched && !game[gameEntity].isGameOver &&
            dragged[gameEntity].isDragged
        ) {
            val entities = engine.getEntitiesFor(piecesFamily)
            val entityRemoving = dropInteractor.getRemovingPieces(entities)
            pieces[entity].positionBoard = dropInteractor.getResultPositionBoard()
            val resultPosition = dropInteractor.getResultPosition()
            sprites[entity].sprite.setCenter(resultPosition.x, resultPosition.y)
            if (!pieces[entityRemoving].isKingPieces) {
                gameInteractor.turnChanged()
                changeOfMovingController.showMessageMoved()
            } else {
                game[gameEntity].isGameOver = true
                gameOverController.gameOver(pieces[entityRemoving].piecesColor)
            }
            val removedPiecesComponent = RemovedPiecesComponent(
                pieces[entityRemoving].piecesColor
            )
            entityRemoving.add(removedPiecesComponent)
            entityRemoving.remove(PiecesComponent::class.java)
            moveWasMade(entity)
        }
    }

    private fun moveWasMade(entity: Entity) {
        dragged[gameEntity].isDragged = false
        layers[entity].layer = SPRITE_LAYER_3
        entity.remove(DraggedComponent::class.java)
        dropInteractor.moveWasMade()
    }
}