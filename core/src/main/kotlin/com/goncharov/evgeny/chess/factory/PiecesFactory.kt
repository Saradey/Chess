package com.goncharov.evgeny.chess.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.goncharov.evgeny.chess.components.LayerComponent
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
        val widthOffset = WIDTH_OFFSET
        //add pawn
        for (index in 0..7) {
            addEntityPieces(
                index * SPRITE_SIZE + widthOffset,
                SPRITE_SIZE + SIZE_SHADOW,
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
            widthOffset + SPRITE_SIZE * 7,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_ROOK_ID)),
            PlayerColor.White
        )
        //add horse
        addEntityPieces(
            widthOffset + SPRITE_SIZE,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_KNIGHT_ID)),
            PlayerColor.White
        )
        addEntityPieces(
            widthOffset + SPRITE_SIZE * 6,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_KNIGHT_ID)),
            PlayerColor.White
        )
        //add elephant
        addEntityPieces(
            widthOffset + SPRITE_SIZE * 2,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_BISHOP_ID)),
            PlayerColor.White
        )
        addEntityPieces(
            widthOffset + SPRITE_SIZE * 5,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_BISHOP_ID)),
            PlayerColor.White
        )
        //add queen
        addEntityPieces(
            widthOffset + SPRITE_SIZE * 3,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_QUEEN_ID)),
            PlayerColor.White
        )
        //add king
        addEntityPieces(
            widthOffset + SPRITE_SIZE * 4,
            SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(WHITE_KING_ID)),
            PlayerColor.White,
            true
        )
    }

    fun buildBlackPiecesPlayer() {
        //add pawn
        for (index in 0..7) {
            addEntityPieces(
                index * SPRITE_SIZE + WIDTH_OFFSET,
                WORLD_HEIGHT - SPRITE_SIZE * 2 - SIZE_SHADOW,
                Sprite(gameAtlas.findRegion(BLACK_PAWN_ID)),
                PlayerColor.Black
            )
        }
        //add tower
        addEntityPieces(
            WIDTH_OFFSET,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_ROOK_ID)),
            PlayerColor.Black
        )
        addEntityPieces(
            WIDTH_OFFSET + SPRITE_SIZE * 7,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_ROOK_ID)),
            PlayerColor.Black
        )
        //add horse
        addEntityPieces(
            WIDTH_OFFSET + SPRITE_SIZE,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_KNIGHT_ID)),
            PlayerColor.Black
        )
        addEntityPieces(
            WIDTH_OFFSET + SPRITE_SIZE * 6,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_KNIGHT_ID)),
            PlayerColor.Black
        )
        //add elephant
        addEntityPieces(
            WIDTH_OFFSET + SPRITE_SIZE * 2,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_BISHOP_ID)),
            PlayerColor.Black
        )
        addEntityPieces(
            WIDTH_OFFSET + SPRITE_SIZE * 5,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_BISHOP_ID)),
            PlayerColor.Black
        )
        //add queen
        addEntityPieces(
            WIDTH_OFFSET + SPRITE_SIZE * 3,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
            Sprite(gameAtlas.findRegion(BLACK_QUEEN_ID)),
            PlayerColor.Black
        )
        //add king
        addEntityPieces(
            WIDTH_OFFSET + SPRITE_SIZE * 4,
            WORLD_HEIGHT - SPRITE_SIZE - SIZE_SHADOW,
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
        piecesSprite.setSize(SPRITE_SIZE, SPRITE_SIZE)
        piecesSprite.setPosition(
            x,
            y
        )
        val spriteComponent = SpriteComponent(piecesSprite)
        val piecesComponent = PiecesComponent(
            piecesColor = piecesColor,
            positionBoard = Pair(
                ((x - WIDTH_OFFSET) / SPRITE_SIZE).toInt(),
                ((y - SIZE_SHADOW) / SPRITE_SIZE).toInt()
            ),
            isKingPieces = isKing
        )
        piecesEntity.add(spriteComponent)
        piecesEntity.add(piecesComponent)
        val layerComponent = LayerComponent(SPRITE_LAYER_3)
        piecesEntity.add(layerComponent)
        engine.addEntity(piecesEntity)
    }
}