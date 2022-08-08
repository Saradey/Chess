package com.goncharov.evgeny.chess.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Sprite
import com.goncharov.evgeny.chess.components.PiecesComponent
import com.goncharov.evgeny.chess.components.SpriteComponent
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.managers.SavedSettingsManager

class PiecesFactory(
    private val engine: Engine,
    private val savedSettingsManager: SavedSettingsManager,
    assetManager: AssetManager
) {
    private val gameAtlas = assetManager[GAME_ASSET_DESCRIPTOR]

    fun buildWhitePiecesPlayer() {
        val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
        //add pawn
        for (index in 0..7) {
            addEntityPieces(
                index * SPRITE_HEIGHT_WIDTH + widthOffset,
                SPRITE_HEIGHT_WIDTH,
                Sprite(gameAtlas.findRegion(WHITE_PAWN_ID)),
            )
        }
        //add tower
        addEntityPieces(widthOffset, 0f, Sprite(gameAtlas.findRegion(WHITE_ROOK_ID)))
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 7,
            0f,
            Sprite(gameAtlas.findRegion(WHITE_ROOK_ID))
        )
        //add horse
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH,
            0f,
            Sprite(gameAtlas.findRegion(WHITE_KNIGHT_ID))
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 6,
            0f,
            Sprite(gameAtlas.findRegion(WHITE_KNIGHT_ID))
        )
        //add elephant
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 2,
            0f,
            Sprite(gameAtlas.findRegion(WHITE_BISHOP_ID))
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 5,
            0f,
            Sprite(gameAtlas.findRegion(WHITE_BISHOP_ID))
        )
        //add queen
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 3,
            0f,
            Sprite(gameAtlas.findRegion(WHITE_QUEEN_ID))
        )
        //add king
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 4,
            0f,
            Sprite(gameAtlas.findRegion(WHITE_KING_ID))
        )
    }

    fun buildBlackPiecesPlayer() {
        val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
        //add pawn
        for (index in 0..7) {
            addEntityPieces(
                index * SPRITE_HEIGHT_WIDTH + widthOffset,
                WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH * 2,
                Sprite(gameAtlas.findRegion(BLACK_PAWN_ID)),
            )
        }
        //add tower
        addEntityPieces(
            widthOffset,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_ROOK_ID))
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 7,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_ROOK_ID))
        )
        //add horse
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_KNIGHT_ID))
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 6,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_KNIGHT_ID))
        )
        //add elephant
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 2,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_BISHOP_ID))
        )
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 5,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_BISHOP_ID))
        )
        //add queen
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 3,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_QUEEN_ID))
        )
        //add king
        addEntityPieces(
            widthOffset + SPRITE_HEIGHT_WIDTH * 4,
            WORLD_HEIGHT - SPRITE_HEIGHT_WIDTH,
            Sprite(gameAtlas.findRegion(BLACK_KING_ID))
        )
    }

    private fun addEntityPieces(
        x: Float,
        y: Float,
        piecesSprite: Sprite
    ) {
        val piecesEntity = Entity()
        piecesSprite.setSize(SPRITE_HEIGHT_WIDTH, SPRITE_HEIGHT_WIDTH)
        piecesSprite.setPosition(
            x,
            y
        )
        val spriteComponent = SpriteComponent(piecesSprite)
        val piecesComponent = PiecesComponent()
        piecesEntity.add(spriteComponent)
        piecesEntity.add(piecesComponent)
        engine.addEntity(piecesEntity)
    }
}