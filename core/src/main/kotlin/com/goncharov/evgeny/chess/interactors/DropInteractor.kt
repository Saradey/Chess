package com.goncharov.evgeny.chess.interactors

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import com.goncharov.evgeny.chess.components.GameComponent

interface DropInteractor {

    fun calculationToTheNearestCell(
        cellsList: ImmutableArray<Entity>,
        positionWorld: Vector2
    )

    fun isEmptyBoardPosition(
        entities: ImmutableArray<Entity>,
    ): Boolean

    fun getResultPositionBoard(): Pair<Int, Int>

    fun getResultPosition(): Vector2

    fun thisIsThePlayersFigure(
        entities: ImmutableArray<Entity>,
        gameComponent: GameComponent
    ): Boolean

    fun getRemovingPieces(entities: ImmutableArray<Entity>): Entity

    fun moveWasMade()
}