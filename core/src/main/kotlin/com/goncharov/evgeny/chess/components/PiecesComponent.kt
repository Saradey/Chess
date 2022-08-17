package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component
import com.goncharov.evgeny.chess.logic.PlayerColor

data class PiecesComponent(
    val piecesColor: PlayerColor,
    var positionBoard: Pair<Int, Int>,
    var isKingPieces: Boolean = false
) : Component