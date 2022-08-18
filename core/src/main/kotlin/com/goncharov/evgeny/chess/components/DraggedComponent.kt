package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

data class DraggedComponent(
    var isDragged: Boolean = false,
    var positionDragged: Vector2 = Vector2()
) : Component