package com.goncharov.evgeny.chess.interactors

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2

interface DropInteractor {

    fun calculationToTheNearestCell(
        cellsList: ImmutableArray<Entity>,
        positionWorld: Vector2
    )
}