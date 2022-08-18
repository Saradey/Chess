package com.goncharov.evgeny.chess.interactors

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.SPRITE_SIZE
import com.goncharov.evgeny.chess.consts.WORLD_HEIGHT
import com.goncharov.evgeny.chess.consts.WORLD_ORIGIN_WIDTH
import com.goncharov.evgeny.chess.consts.WORlD_ORIGIN_HEIGHT

class WorldWrapInteractorImpl : WorldWrapInteractor {

    override fun worldWrapPosition(position: Vector2): Boolean {
        return position.x > WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT &&
                position.x < SPRITE_SIZE * 8 + WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT &&
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