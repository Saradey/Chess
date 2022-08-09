package com.goncharov.evgeny.chess.components.mappers

import com.badlogic.ashley.core.ComponentMapper
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.SpriteComponent

val sprites: ComponentMapper<SpriteComponent> = ComponentMapper.getFor(
    SpriteComponent::class.java
)

val pieces: ComponentMapper<PiecesComponent> = ComponentMapper.getFor(
    PiecesComponent::class.java
)