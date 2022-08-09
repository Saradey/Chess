package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.mappers.pieces
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.SPRITE_HEIGHT_WIDTH
import com.goncharov.evgeny.chess.consts.WORLD_ORIGIN_WIDTH
import com.goncharov.evgeny.chess.consts.WORlD_ORIGIN_HEIGHT

class DragAndDropSystem(
    private val worldViewport: Viewport
) : EntitySystem() {

    private val piecesFamily: Family = Family.all(
        PiecesComponent::class.java,
    ).get()

    override fun update(deltaTime: Float) {
        val entities = engine.getEntitiesFor(piecesFamily)
        if (Gdx.input.isTouched) {
            val positionWorld =
                worldViewport.unproject(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat()))
            if (positionWorld.x > WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT &&
                positionWorld.x < SPRITE_HEIGHT_WIDTH * 8 + WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
            ) {
                if (entities.all { entity ->
                        !pieces[entity].isDragged
                    }
                ) {
                    entities.forEach { entity ->
                        val sprite = sprites[entity].sprite
                        if (sprite.boundingRectangle.contains(positionWorld)) {
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
            entities.forEach { entity ->
                pieces[entity].isDragged = false
            }
        }
    }
}