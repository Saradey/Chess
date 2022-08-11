package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component
import com.goncharov.evgeny.chess.logic.PlayerColor

data class PiecesComponent(
    var isDragged: Boolean = false,
    val piecesColor: PlayerColor,
    var positionBoard: Pair<Int, Int>
) : Component