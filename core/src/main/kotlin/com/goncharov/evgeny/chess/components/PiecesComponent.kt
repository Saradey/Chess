package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component

data class PiecesComponent(
    var isDragged: Boolean = false
) : Component