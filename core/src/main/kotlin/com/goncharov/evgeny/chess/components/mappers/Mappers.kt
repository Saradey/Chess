package com.goncharov.evgeny.chess.components.mappers

import com.badlogic.ashley.core.ComponentMapper
import com.goncharov.evgeny.chess.components.SpriteComponent

val sprites: ComponentMapper<SpriteComponent> = ComponentMapper.getFor(
    SpriteComponent::class.java
)