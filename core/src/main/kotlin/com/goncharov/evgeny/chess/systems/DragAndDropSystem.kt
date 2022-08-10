package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.CellComponent
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.mappers.cells
import com.goncharov.evgeny.chess.components.mappers.pieces
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.controllers.ChangeOfMovingController
import com.goncharov.evgeny.chess.controllers.GameInteractor

class DragAndDropSystem(
    private val worldViewport: Viewport,
    private val changeOfMovingController: ChangeOfMovingController,
    private val interactorController: GameInteractor
) : EntitySystem() {

    private val piecesFamily: Family = Family.all(
        PiecesComponent::class.java,
    ).get()
    private val cellsFamily: Family = Family.all(
        CellComponent::class.java,
    ).get()

    override fun update(deltaTime: Float) {
        val entities = engine.getEntitiesFor(piecesFamily)
        if (Gdx.input.isTouched) {
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
                    val resultPosition = Vector2(cells[firstCell].centrePosition)
                    cellsList.forEach { cellEntity ->
                        val cellDst = cells[cellEntity].centrePosition.dst(positionWorld)
                        if (cellDst < tempDst) {
                            tempDst = cellDst
                            resultPosition.set(cells[cellEntity].centrePosition)
                        }
                    }
                    sprites[entity].sprite.setCenter(resultPosition.x, resultPosition.y)
                    interactorController.turnChanged()
                    changeOfMovingController.showMessageMoved()
                }
            }
        }
    }
}