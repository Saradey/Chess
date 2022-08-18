package com.goncharov.evgeny.chess.systems.world.wrap

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2

interface WorldWrapInteractor {

    fun worldWrapPosition(position: Vector2): Boolean

    fun checkedClickPosition(entity: Entity, position: Vector2): Boolean
}