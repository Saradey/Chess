package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.DraggedComponent
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.RemovedPiecesComponent
import com.goncharov.evgeny.chess.components.mappers.*
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.controllers.ChangeOfMovingController
import com.goncharov.evgeny.chess.interactors.GameInteractor
import com.goncharov.evgeny.chess.controllers.GameOverController
import com.goncharov.evgeny.chess.logic.PlayerColor

class DragAndDropSystem(
    private val worldViewport: Viewport,
    private val changeOfMovingController: ChangeOfMovingController,
    private val interactorController: GameInteractor,
    private val gameOverController: GameOverController
) : EntitySystem() {

    private val gameComponent by lazy {
        game[engine.getEntitiesFor(gameFamily).first()]
    }
    private val draggedComponent = DraggedComponent()
    private var draggedEntity: Entity? = null

    override fun update(deltaTime: Float) {
        val entities = engine.getEntitiesFor(piecesFamily)
        if (Gdx.input.isTouched && !gameComponent.isGameOver) {
            val positionWorld =
                worldViewport.unproject(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat()))
            if (positionWorld.x > WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT &&
                positionWorld.x < SPRITE_SIZE * 8 + WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT &&
                positionWorld.y > SPRITE_SIZE / 2 &&
                positionWorld.y < WORLD_HEIGHT - SPRITE_SIZE / 2
            ) {
                if (!draggedComponent.isDragged) {
                    entities.forEach { entity ->
                        val sprite = sprites[entity].sprite
                        if (sprite.boundingRectangle.contains(positionWorld) &&
                            interactorController.checkingThePlayer(pieces[entity].piecesColor)
                        ) {
                            draggedComponent.isDragged = true
                            entity.add(draggedComponent)
                            draggedEntity = entity
                            return@forEach
                        }
                    }
                }
                draggedEntity?.let { entity ->
                    sprites[entity].sprite.setCenter(
                        positionWorld.x,
                        positionWorld.y
                    )
                }
            }
        } else {
            if (draggedComponent.isDragged && draggedEntity != null) {
                val positionWorld = worldViewport.unproject(
                    Vector2(
                        Gdx.input.x.toFloat(),
                        Gdx.input.y.toFloat()
                    )
                )
                draggedEntity?.remove(DraggedComponent::class.java)
                val cellsList = engine.getEntitiesFor(cellsFamily)
                val firstCell = cellsList.first()
                var tempDst = cells[firstCell].centrePosition.dst(positionWorld)
                var resultPositionBoard = cells[firstCell].positionBoard
                val resultPosition = Vector2(cells[firstCell].centrePosition)
                cellsList.forEach { cellEntity ->
                    val cellDst = cells[cellEntity].centrePosition.dst(positionWorld)
                    if (cellDst < tempDst) {
                        tempDst = cellDst
                        resultPositionBoard = cells[cellEntity].positionBoard
                        resultPosition.set(cells[cellEntity].centrePosition)
                    }
                }
                if (isEmptyBoardPosition(entities, resultPositionBoard)) {
                    //moving pieces
                    pieces[draggedEntity].positionBoard = resultPositionBoard
                    sprites[draggedEntity].sprite.setCenter(resultPosition.x, resultPosition.y)
                    interactorController.turnChanged()
                    changeOfMovingController.showMessageMoved()
                } else {
                    if (thisIsThePlayersFigure(entities, resultPositionBoard)) {
                        //back pieces
                        val cellEntity = cellsList.first { cellEntity ->
                            cells[cellEntity].positionBoard == pieces[draggedEntity].positionBoard
                        }
                        sprites[draggedEntity].sprite.setCenter(
                            cells[cellEntity].centrePosition.x,
                            cells[cellEntity].centrePosition.y
                        )
                    } else {
                        //remove pieces
                        val entityRemoving =
                            getRemovingPieces(entities, resultPositionBoard)
                        pieces[draggedEntity].positionBoard = resultPositionBoard
                        sprites[draggedEntity].sprite.setCenter(resultPosition.x, resultPosition.y)
                        if (!pieces[entityRemoving].isKingPieces) {
                            interactorController.turnChanged()
                            changeOfMovingController.showMessageMoved()
                        } else {
                            gameComponent.isGameOver = true
                            gameOverController.gameOver(pieces[entityRemoving].piecesColor)
                        }
                        if (pieces[entityRemoving].piecesColor == PlayerColor.White) {
                            val count = engine.getEntitiesFor(removedPiecesFamily)
                                .count { entityRemoved ->
                                    piecesRemoved[entityRemoved].piecesColor == PlayerColor.White
                                } + 1
                            val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
                            sprites[entityRemoving].sprite.setPosition(
                                if (count < 9) widthOffset + SPRITE_SIZE * 8
                                else widthOffset + SPRITE_SIZE * 9,
                                if (count < 9) WORLD_HEIGHT - count * SPRITE_SIZE - SIZE_SHADOW
                                else WORLD_HEIGHT - (count - 8) * SPRITE_SIZE - SIZE_SHADOW
                            )
                        } else {
                            val count = engine.getEntitiesFor(removedPiecesFamily)
                                .count { entityRemoved ->
                                    piecesRemoved[entityRemoved].piecesColor == PlayerColor.Black
                                }
                            val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
                            sprites[entityRemoving].sprite.setPosition(
                                if (count < 8) widthOffset - SPRITE_SIZE
                                else widthOffset - SPRITE_SIZE * 2,
                                if (count < 8) count * SPRITE_SIZE + SIZE_SHADOW
                                else (count - 8) * SPRITE_SIZE + SIZE_SHADOW
                            )
                        }
                        val removedPiecesComponent = RemovedPiecesComponent(
                            pieces[entityRemoving].piecesColor
                        )
                        entityRemoving.add(removedPiecesComponent)
                        entityRemoving.remove(PiecesComponent::class.java)
                    }
                }
            }
            draggedEntity = null
            draggedComponent.isDragged = false
        }
    }

    private fun getRemovingPieces(
        entities: ImmutableArray<Entity>,
        positionBoard: Pair<Int, Int>
    ): Entity {
        return entities.first { entity ->
            pieces[entity].positionBoard == positionBoard
        }
    }

    private fun isEmptyBoardPosition(
        entities: ImmutableArray<Entity>,
        positionBoard: Pair<Int, Int>
    ): Boolean {
        return entities.any { entity ->
            pieces[entity].positionBoard == positionBoard
        }.not()
    }

    private fun thisIsThePlayersFigure(
        entities: ImmutableArray<Entity>,
        positionBoard: Pair<Int, Int>
    ): Boolean {
        return entities.find { entity ->
            pieces[entity].positionBoard == positionBoard
        }?.let { entity ->
            interactorController.checkingThePlayer(pieces[entity].piecesColor)
        } ?: false
    }
}