package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component

data class LayerComponent(
    val layer: Int
) : Component