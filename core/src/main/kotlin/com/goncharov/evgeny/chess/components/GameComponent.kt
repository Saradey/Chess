package com.goncharov.evgeny.chess.components

import com.badlogic.ashley.core.Component
import com.goncharov.evgeny.chess.logic.Step

data class GameComponent(
    var step: Step
) : Component