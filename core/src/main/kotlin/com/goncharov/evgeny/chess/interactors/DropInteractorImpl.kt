package com.goncharov.evgeny.chess.interactors

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import com.goncharov.evgeny.chess.components.GameComponent
import com.goncharov.evgeny.chess.components.mappers.cells
import com.goncharov.evgeny.chess.components.mappers.pieces
import java.lang.NullPointerException

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

    override fun isEmptyBoardPosition(
        entities: ImmutableArray<Entity>
    ): Boolean {
        return entities.any { entity ->
            pieces[entity].positionBoard == resultPositionBoard
        }.not()
    }

    override fun getResultPositionBoard(): Pair<Int, Int> {
        return resultPositionBoard ?: throw NullPointerException(MUST_NOT_NULL_POSITION_BOARD)
    }

    override fun getResultPosition(): Vector2 {
        return resultPosition ?: throw NullPointerException(MUST_NOT_NULL_POSITION)
    }

    override fun thisIsThePlayersFigure(
        entities: ImmutableArray<Entity>,
        gameComponent: GameComponent
    ): Boolean {
        return entities.find { entity ->
            pieces[entity].positionBoard == resultPositionBoard
        }?.let { entity ->
            gameComponent.step == pieces[entity].piecesColor
        } ?: false
    }

    companion object {
        private const val MUST_NOT_NULL_POSITION_BOARD = "resultPositionBoard must not bu null"
        private const val MUST_NOT_NULL_POSITION = "resultPosition must not bu null"
    }
}