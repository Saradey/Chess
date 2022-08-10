package com.goncharov.evgeny.chess.consts

import com.badlogic.ashley.core.Family
import com.goncharov.evgeny.chess.components.GameComponent

val gameFamily: Family = Family.all(GameComponent::class.java).get()