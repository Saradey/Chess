package com.goncharov.evgeny.chess.consts

import com.badlogic.ashley.core.Family
import com.goncharov.evgeny.chess.components.*

val gameFamily: Family = Family.all(GameComponent::class.java).get()

val piecesFamily: Family = Family.all(PiecesComponent::class.java).get()

val cellsFamily: Family = Family.all(CellComponent::class.java).get()

val removedPiecesFamily: Family = Family.all(RemovedPiecesComponent::class.java).get()

val allPieces: Family = Family.all(SpriteComponent::class.java).one(
    PiecesComponent::class.java,
    CellComponent::class.java,
    RemovedPiecesComponent::class.java
).get()