package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component

data class DraggedComponent(
    var isDragged: Boolean = false
) : Component