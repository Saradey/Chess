package com.goncharov.evgeny.chess.consts

import com.badlogic.ashley.core.Family
import com.goncharov.evgeny.chess.components.CellComponent
import com.goncharov.evgeny.chess.components.GameComponent
import com.goncharov.evgeny.chess.components.PiecesComponent

val gameFamily: Family = Family.all(GameComponent::class.java).get()

val piecesFamily: Family = Family.all(PiecesComponent::class.java).get()

val cellsFamily: Family = Family.all(CellComponent::class.java).get()