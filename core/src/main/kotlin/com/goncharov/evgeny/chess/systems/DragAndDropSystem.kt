package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.mappers.cells
import com.goncharov.evgeny.chess.components.mappers.game
import com.goncharov.evgeny.chess.components.mappers.pieces
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.controllers.ChangeOfMovingController
import com.goncharov.evgeny.chess.controllers.GameInteractor
import com.goncharov.evgeny.chess.controllers.GameOverController

class DragAndDropSystem(
    private val worldViewport: Viewport,
    private val changeOfMovingController: ChangeOfMovingController,
    private val interactorController: GameInteractor,
    private val gameOverController: GameOverController
) : EntitySystem() {

    private val gameComponent by lazy {
        game[engine.getEntitiesFor(gameFamily).first()]
    }

    override fun update(deltaTime: Float) {
        val entities = engine.getEntitiesFor(piecesFamily)
        if (Gdx.input.isTouched && !gameComponent.isGameOver) {
            val positionWorld =
                worldViewport.unproject(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat()))
            if (positionWorld.x > WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT &&
                positionWorld.x < SPRITE_HEIGHT_WIDTH * 8 + WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT &&
                positionWorld.y > SPRITE_HEIGHT_WIDTH / 2 &&
                positionWorld.y < WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH / 2
            ) {
                if (entities.all { entity ->
                        !pieces[entity].isDragged
                    }
                ) {
                    entities.forEach { entity ->
                        val sprite = sprites[entity].sprite
                        if (
                            sprite.boundingRectangle.contains(positionWorld) &&
                            interactorController.checkingThePlayer(pieces[entity].piecesColor)
                        ) {
                            pieces[entity].isDragged = true
                        }
                    }
                }
                entities.firstOrNull { entity ->
                    pieces[entity].isDragged
                }?.let { entity ->
                    sprites[entity].sprite.setCenter(
                        positionWorld.x,
                        positionWorld.y
                    )
                }
            }
        } else {
            if (entities.any { entity -> pieces[entity].isDragged }) {
                entities.firstOrNull { entity ->
                    pieces[entity].isDragged
                }?.let { entity ->
                    val positionWorld = worldViewport.unproject(
                        Vector2(
                            Gdx.input.x.toFloat(),
                            Gdx.input.y.toFloat()
                        )
                    )
                    pieces[entity].isDragged = false
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
                        pieces[entity].positionBoard = resultPositionBoard
                        sprites[entity].sprite.setCenter(resultPosition.x, resultPosition.y)
                        interactorController.turnChanged()
                        changeOfMovingController.showMessageMoved()
                    } else {
                        if (thisIsThePlayersFigure(entities, resultPositionBoard)) {
                            //back pieces
                            val cellEntity = cellsList.first { cellEntity ->
                                cells[cellEntity].positionBoard == pieces[entity].positionBoard
                            }
                            sprites[entity].sprite.setCenter(
                                cells[cellEntity].centrePosition.x,
                                cells[cellEntity].centrePosition.y
                            )
                        } else {
                            //remove pieces
                            val entityRemoving = getRemovingPieces(entities, resultPositionBoard)
                            engine.removeEntity(entityRemoving)
                            pieces[entity].positionBoard = resultPositionBoard
                            sprites[entity].sprite.setCenter(resultPosition.x, resultPosition.y)
                            if (!pieces[entityRemoving].isKingPieces) {
                                interactorController.turnChanged()
                                changeOfMovingController.showMessageMoved()
                            } else {
                                gameComponent.isGameOver = true
                                gameOverController.gameOver(pieces[entityRemoving].piecesColor)
                            }
                        }
                    }
                }
            }
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