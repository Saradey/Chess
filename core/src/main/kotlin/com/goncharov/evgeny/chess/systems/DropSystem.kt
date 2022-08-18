package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.mappers.cells
import com.goncharov.evgeny.chess.components.mappers.dragged
import com.goncharov.evgeny.chess.components.mappers.game
import com.goncharov.evgeny.chess.consts.cellsFamily
import com.goncharov.evgeny.chess.consts.draggedFamily
import com.goncharov.evgeny.chess.consts.gameFamily
import com.goncharov.evgeny.chess.interactors.DropInteractor

class DropSystem(
    private val worldViewport: Viewport,
    private val dropInteractor: DropInteractor
) : IteratingSystem(draggedFamily) {

    private val gameEntity by lazy {
        engine.getEntitiesFor(gameFamily).first()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (!Gdx.input.isTouched && !game[gameEntity].isGameOver &&
            dragged[gameEntity].isDragged
        ) {
            val positionWorld =
                worldViewport.unproject(
                    Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
                )
            val cellsList = engine.getEntitiesFor(cellsFamily)
            dropInteractor.calculationToTheNearestCell(cellsList, positionWorld)
        }
    }
}