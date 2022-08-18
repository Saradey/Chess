package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.mappers.dragged
import com.goncharov.evgeny.chess.components.mappers.game
import com.goncharov.evgeny.chess.components.mappers.layers
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.interactors.WorldWrapInteractor

class WorldWrapAndDraggedOnSystem(
    private val worldViewport: Viewport,
    private val worldWrapInteractor: WorldWrapInteractor
) : EntitySystem() {

    private val gameEntity by lazy {
        engine.getEntitiesFor(gameFamily).first()
    }

    override fun update(deltaTime: Float) {
        if (Gdx.input.isTouched && !game[gameEntity].isGameOver) {
            val entities = engine.getEntitiesFor(piecesFamily)
            val positionWorld = worldViewport.unproject(
                Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            )
            if (worldWrapInteractor.worldWrapPosition(positionWorld)) {
                entities.forEach { entity ->
                    if (!dragged[gameEntity].isDragged &&
                        worldWrapInteractor.checkedClickPosition(entity, positionWorld)
                    ) {
                        dragged[gameEntity].isDragged = true
                        entity.add(dragged[gameEntity])
                        layers[entity].layer = DRAGGED_LAYER_4
                        return@forEach
                    }
                }
            }
        }
    }
}