package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.utils.LayerComparator

class RenderSystem(
    private val viewport: Viewport,
    private val batch: SpriteBatch
) : SortedIteratingSystem(renderFamily, LayerComparator()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        forceSort()
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        sprites[entity].sprite.draw(batch)
        batch.end()
    }
}