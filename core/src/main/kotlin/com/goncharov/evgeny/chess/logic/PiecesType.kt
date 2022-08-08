package com.goncharov.evgeny.chess.logic

sealed class PiecesType {

    /** Пешка */
    object Pawn : PiecesType()

    /** Башня */
    object Tower : PiecesType()

    /** Конь */
    object Horse : PiecesType()

    /** Слон */
    object Elephant : PiecesType()

    /** Королева */
    object Queen : PiecesType()

    /** Король */
    object King : PiecesType()
}
