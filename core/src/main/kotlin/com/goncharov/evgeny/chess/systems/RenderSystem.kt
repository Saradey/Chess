package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.mappers.pieces
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*

class RenderSystem(
    private val viewport: Viewport,
    private val batch: SpriteBatch
) : EntitySystem() {

    override fun update(deltaTime: Float) {
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        engine.getEntitiesFor(backgroundFamily).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        engine.getEntitiesFor(cellsFamily).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        engine.getEntitiesFor(shadowShadowFamily).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        engine.getEntitiesFor(piecesFamily).sortedBy { entity ->
            pieces[entity].isDragged
        }.forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        engine.getEntitiesFor(removedPiecesFamily).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        batch.end()
    }
}