package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.goncharov.evgeny.chess.components.mappers.piecesRemoved
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.logic.PlayerColor

class PutInPlaceSystem : IteratingSystem(removedPiecesFamily) {

    private var countRemovedBlack = 0
    private var countRemovedWhite = 1

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (!piecesRemoved[entity].putInPlace) {
            if (piecesRemoved[entity].piecesColor == PlayerColor.White) {
                sprites[entity].sprite.setPosition(
                    if (countRemovedWhite < 9) X_WHITE_POSITION_FIRST_COLUMN
                    else X_WHITE_POSITION_SECOND_COLUMN,
                    if (countRemovedWhite < 9) WORLD_HEIGHT - countRemovedWhite * SPRITE_SIZE - SIZE_SHADOW
                    else WORLD_HEIGHT - (countRemovedWhite - 8) * SPRITE_SIZE - SIZE_SHADOW
                )
                countRemovedWhite++
            } else {
                sprites[entity].sprite.setPosition(
                    if (countRemovedBlack < 8) X_BLACK_POSITION_FIRST_COLUMN
                    else X_BLACK_POSITION_SECOND_COLUMN,
                    if (countRemovedBlack < 8) countRemovedBlack * SPRITE_SIZE + SIZE_SHADOW
                    else (countRemovedBlack - 8) * SPRITE_SIZE + SIZE_SHADOW
                )
                countRemovedBlack++
            }
            piecesRemoved[entity].putInPlace = true
        }
    }

    companion object {
        private const val X_WHITE_POSITION_FIRST_COLUMN = WIDTH_OFFSET + SPRITE_SIZE * 8
        private const val X_WHITE_POSITION_SECOND_COLUMN = WIDTH_OFFSET + SPRITE_SIZE * 9
        private const val X_BLACK_POSITION_FIRST_COLUMN = WIDTH_OFFSET - SPRITE_SIZE
        private const val X_BLACK_POSITION_SECOND_COLUMN = WIDTH_OFFSET - SPRITE_SIZE * 2
    }
}