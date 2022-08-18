package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.mappers.dragged
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.draggedFamily
import com.goncharov.evgeny.chess.consts.gameFamily

class DragSystem(
    private val worldViewport: Viewport
) : IteratingSystem(draggedFamily) {

    private val gameEntity by lazy {
        engine.getEntitiesFor(gameFamily).first()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (dragged[gameEntity].isDragged) {
            val positionWorld = worldViewport.unproject(
                Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            )
            sprites[entity].sprite.setCenter(positionWorld.x, positionWorld.y)
        }
    }
}