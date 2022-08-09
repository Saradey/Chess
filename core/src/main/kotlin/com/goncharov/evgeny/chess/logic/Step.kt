package com.goncharov.evgeny.chess.logic

sealed class Step {

    object WhiteStep : Step()

    object BlackStep : Step()
}