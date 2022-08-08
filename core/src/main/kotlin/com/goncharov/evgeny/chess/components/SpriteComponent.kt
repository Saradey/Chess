package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite

data class SpriteComponent(
    val sprite: Sprite
) : Component