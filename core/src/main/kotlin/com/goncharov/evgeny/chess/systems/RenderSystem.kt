package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.*
import com.goncharov.evgeny.chess.components.mappers.sprites

class RenderSystem(
    private val viewport: Viewport,
    private val batch: SpriteBatch
) : EntitySystem() {

    init {
//        (viewport.camera as OrthographicCamera).zoom += 2
    }

    private val familyBackgroundSprite = Family.all(
        SpriteComponent::class.java,
        BackgroundComponent::class.java
    ).get()
    private val familyBoardSprite = Family.all(
        SpriteComponent::class.java,
        CellComponent::class.java
    ).get()
    private val familyPiecesSprite = Family.all(
        SpriteComponent::class.java,
        PiecesComponent::class.java
    ).get()
    private val familyShadowSprite = Family.all(
        SpriteComponent::class.java,
        ShadowComponent::class.java
    ).get()

    override fun update(deltaTime: Float) {
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        engine.getEntitiesFor(familyBackgroundSprite).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        engine.getEntitiesFor(familyBoardSprite).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        engine.getEntitiesFor(familyShadowSprite).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        engine.getEntitiesFor(familyPiecesSprite).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        batch.end()
    }
}