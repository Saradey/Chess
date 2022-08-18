package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.goncharov.evgeny.chess.components.mappers.pieces
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
                    if (countRemovedWhite < 9) WIDTH_OFFSET + SPRITE_SIZE * 8
                    else WIDTH_OFFSET + SPRITE_SIZE * 9,
                    if (countRemovedWhite < 9) WORLD_HEIGHT - countRemovedWhite * SPRITE_SIZE - SIZE_SHADOW
                    else WORLD_HEIGHT - (countRemovedWhite - 8) * SPRITE_SIZE - SIZE_SHADOW
                )
                countRemovedWhite++
            } else {
                sprites[entity].sprite.setPosition(
                    if (countRemovedBlack < 8) WIDTH_OFFSET - SPRITE_SIZE
                    else WIDTH_OFFSET - SPRITE_SIZE * 2,
                    if (countRemovedBlack < 8) countRemovedBlack * SPRITE_SIZE + SIZE_SHADOW
                    else (countRemovedBlack - 8) * SPRITE_SIZE + SIZE_SHADOW
                )
                countRemovedBlack++
            }
            piecesRemoved[entity].putInPlace = true
        }
    }
}