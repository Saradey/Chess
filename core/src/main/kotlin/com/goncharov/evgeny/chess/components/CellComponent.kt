package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

data class CellComponent(
    val centrePosition: Vector2
) : Component