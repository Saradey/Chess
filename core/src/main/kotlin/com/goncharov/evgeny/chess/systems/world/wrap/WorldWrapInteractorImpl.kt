package com.goncharov.evgeny.chess.systems.world.wrap

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*

class WorldWrapInteractorImpl : WorldWrapInteractor {

    override fun worldWrapPosition(position: Vector2): Boolean {
        return position.x > WIDTH_OFFSET &&
                position.x < SPRITE_SIZE * 8 + WIDTH_OFFSET &&
                position.y > SPRITE_SIZE / 2 &&
                position.y < WORLD_HEIGHT - SPRITE_SIZE / 2
    }

    override fun checkedClickPosition(
        entity: Entity,
        position: Vector2
    ): Boolean {
        return sprites[entity].sprite.boundingRectangle.contains(position)
    }
}