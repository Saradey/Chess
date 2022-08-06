package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.SpriteComponent
import com.goncharov.evgeny.chess.components.mappers.sprites

class RenderSystem(
    private val viewport: Viewport,
    private val batch: SpriteBatch
) : EntitySystem() {

    private val familySprite = Family.one(
        SpriteComponent::class.java
    ).get()

    override fun update(deltaTime: Float) {
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        engine.getEntitiesFor(familySprite).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        batch.end()
    }
}