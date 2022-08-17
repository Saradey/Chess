package com.goncharov.evgeny.chess.components.mappers

import com.badlogic.ashley.core.ComponentMapper
import com.goncharov.evgeny.chess.components.*

val sprites: ComponentMapper<SpriteComponent> = ComponentMapper.getFor(
    SpriteComponent::class.java
)

val pieces: ComponentMapper<PiecesComponent> = ComponentMapper.getFor(
    PiecesComponent::class.java
)

val piecesRemoved: ComponentMapper<RemovedPiecesComponent> = ComponentMapper.getFor(
    RemovedPiecesComponent::class.java
)

val cells: ComponentMapper<CellComponent> = ComponentMapper.getFor(
    CellComponent::class.java
)

val game: ComponentMapper<GameComponent> = ComponentMapper.getFor(
    GameComponent::class.java
)

val layers: ComponentMapper<LayerComponent> = ComponentMapper.getFor(
    LayerComponent::class.java
)