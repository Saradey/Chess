package com.goncharov.evgeny.chess.interactors

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import com.goncharov.evgeny.chess.components.mappers.cells

class DropInteractorImpl : DropInteractor {

    private var resultPositionBoard: Pair<Int, Int>? = null
    private var resultPosition: Vector2? = null

    override fun calculationToTheNearestCell(
        cellsList: ImmutableArray<Entity>,
        positionWorld: Vector2
    ) {
        val firstCell = cellsList.first()
        var tempDst = cells[firstCell].centrePosition.dst(positionWorld)
        resultPositionBoard = cells[firstCell].positionBoard
        resultPosition = Vector2(cells[firstCell].centrePosition)
        cellsList.forEach { cellEntity ->
            val cellDst = cells[cellEntity].centrePosition.dst(positionWorld)
            if (cellDst < tempDst) {
                tempDst = cellDst
                resultPositionBoard = cells[cellEntity].positionBoard
                resultPosition?.set(cells[cellEntity].centrePosition)
            }
        }
    }
}