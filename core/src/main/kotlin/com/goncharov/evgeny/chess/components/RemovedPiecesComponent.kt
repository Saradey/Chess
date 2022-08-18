package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component
import com.goncharov.evgeny.chess.logic.PlayerColor

data class RemovedPiecesComponent(
    val piecesColor: PlayerColor,
    var putInPlace: Boolean = false
) : Component