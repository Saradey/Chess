package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.components.BackgroundComponent
import com.goncharov.evgeny.chess.components.CellComponent
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.SpriteComponent
import com.goncharov.evgeny.chess.components.mappers.sprites

class RenderSystem(
    private val viewport: Viewport,
    private val batch: SpriteBatch
) : EntitySystem() {

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
        engine.getEntitiesFor(familyPiecesSprite).forEach { entity ->
            sprites[entity].sprite.draw(batch)
        }
        batch.end()
    }
}