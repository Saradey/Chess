package com.goncharov.evgeny.chess.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.SpriteComponent
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.logic.PlayerColor
import com.goncharov.evgeny.chess.managers.ResourceManager

class PiecesFactory(
    private val engine: Engine,
    resourceManager: ResourceManager
) {
    private val gameAtlas = resourceManager[GAME_ASSET_DESCRIPTOR]

    fun buildWhitePiecesPlayer() {
        val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
        //add pawn
        for (index in 0..7) {
            addEntityPieces(
                index * SPRITE_HEIGHT_WIDTH + widthOffset,
                SPRITE_HEIGHT_WIDTH + SIZE_SHADOW,
                Sprite(gameAtlas.findRegion(WHITE_PAWN_ID)),
                PlayerColor.White
            )
        }
        //add tower
        addEntityPieces(
            widthOffset,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_ROOK_ID)),
            PlayerColor.White
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 7,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_ROOK_ID)),
            PlayerColor.White
        )
        //add horse
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_KNIGHT_ID)),
            PlayerColor.White
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 6,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_KNIGHT_ID)),
            PlayerColor.White
        )
        //add elephant
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 2,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_BISHOP_ID)),
            PlayerColor.White
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 5,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_BISHOP_ID)),
            PlayerColor.White
        )
        //add queen
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 3,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_QUEEN_ID)),
            PlayerColor.White
        )
        //add king
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 4,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_KING_ID)),
            PlayerColor.White,
            true
        )
    }

    fun buildBlackPiecesPlayer() {
        val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
        //add pawn
        for (index in 0..7) {
            addEntityPieces(
                index * SPRITE_HEIGHT_WIDTH + widthOffset,
                WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH * 2 - SIZE_SHADOW,
                Sprite(gameAtlas.findRegion(BLACK_PAWN_ID)),
                PlayerColor.Black
            )
        }
        //add tower
        addEntityPieces(
            widthOffset,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_ROOK_ID)),
            PlayerColor.Black
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 7,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_ROOK_ID)),
            PlayerColor.Black
        )
        //add horse
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_KNIGHT_ID)),
            PlayerColor.Black
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 6,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_KNIGHT_ID)),
            PlayerColor.Black
        )
        //add elephant
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 2,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_BISHOP_ID)),
            PlayerColor.Black
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 5,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_BISHOP_ID)),
            PlayerColor.Black
        )
        //add queen
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 3,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_QUEEN_ID)),
            PlayerColor.Black
        )
        //add king
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 4,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_KING_ID)),
            PlayerColor.Black,
            true
        )
    }

    private fun addEntityPieces(
        x: Float,
        y: Float,
        piecesSprite: Sprite,
        piecesColor: PlayerColor,
        isKing: Boolean = false
    ) {
        val piecesEntity = Entity()
        piecesSprite.setSize(SPRITE_HEIGHT_WIDTH, SPRITE_HEIGHT_WIDTH)
        piecesSprite.setPosition(
            x,
            y
        )
        val spriteComponent = SpriteComponent(piecesSprite)
        val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
        val piecesComponent = PiecesComponent(
            piecesColor = piecesColor,
            positionBoard = Pair(
                ((x - widthOffset) / SPRITE_HEIGHT_WIDTH).toInt(),
                ((y - SIZE_SHADOW) / SPRITE_HEIGHT_WIDTH).toInt()
            ),
            isKingPieces = isKing
        )
        piecesEntity.add(spriteComponent)
        piecesEntity.add(piecesComponent)
        engine.addEntity(piecesEntity)
    }
}