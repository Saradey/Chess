package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.SpriteComponent
import com.goncharov.evgeny.chess.components.mappers.pieces
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*

class RenderSystem(
    private val viewport: Viewport,
    private val batch: SpriteBatch
) : EntitySystem() {

    private val backgroundSpriteComponent: SpriteComponent by lazy {
        sprites[engine.getEntitiesFor(backgroundFamily).first()]
    }
    private val cellsSpriteComponents: List<SpriteComponent> by lazy {
        engine.getEntitiesFor(cellsFamily).map { cellEntity ->
            sprites[cellEntity]
        }
    }
    private val shadowSpriteComponents: List<SpriteComponent> by lazy {
        engine.getEntitiesFor(shadowFamily).map { shadowEntity ->
            sprites[shadowEntity]
        }
    }
    private val piecesEntities: ImmutableArray<Entity> by lazy {
        engine.getEntitiesFor(piecesFamily)
    }
    private val removedPiecesEntities: ImmutableArray<Entity> by lazy {
        engine.getEntitiesFor(removedPiecesFamily)
    }

    override fun update(deltaTime: Float) {
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        backgroundSpriteComponent.sprite.draw(batch)
        cellsSpriteComponents.forEach { cellsComponent ->
            cellsComponent.sprite.draw(batch)
        }
        shadowSpriteComponents.forEach { shadowComponent ->
            shadowComponent.sprite.draw(batch)
        }
        piecesEntities.sortedBy { piecesEntity ->
            pieces[piecesEntity].isDragged
        }.forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        removedPiecesEntities.forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        batch.end()
    }
}