package com.goncharov.evgeny.chess.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.goncharov.evgeny.chess.components.BackgroundComponent
import com.goncharov.evgeny.chess.components.CellComponent
import com.goncharov.evgeny.chess.components.ShadowComponent
import com.goncharov.evgeny.chess.components.SpriteComponent
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager.Companion.GRAY_BOARD_OPTION

class ChessBoardFactory(
    private val engine: Engine,
    private val savedSettingsManager: SavedSettingsManager,
    private val uiSkin: Skin,
    assetManager: AssetManager
) {
    private val gameAtlas = assetManager[GAME_ASSET_DESCRIPTOR]

    fun buildChessBoard() {
        val spriteBoardHeight = SPRITE_HEIGHT_WIDTH
        val widthOffset = WORLD_ORIGIN_WIDTH - WORlD_ORIGIN_HEIGHT
        val heightOffset = SIZE_SHADOW
        for (y in 0..7) {
            for (x in 0..7) {
                val spriteBoard = when {
                    y % 2 == 0 && x % 2 == 0 -> Sprite(getDarkColorBoard())
                    y % 2 == 0 && x % 2 != 0 -> Sprite(getLightColorBoard())
                    y % 2 != 0 && x % 2 == 0 -> Sprite(getLightColorBoard())
                    y % 2 != 0 && x % 2 != 0 -> Sprite(getDarkColorBoard())
                    else -> Sprite(getLightColorBoard())
                }
                spriteBoard.setSize(spriteBoardHeight, spriteBoardHeight)
                spriteBoard.setPosition(
                    x * spriteBoardHeight + widthOffset,
                    y * spriteBoardHeight + heightOffset
                )
                val cell = Entity()
                val cellComponent = CellComponent()
                cell.add(cellComponent)
                val spriteComponent = SpriteComponent(spriteBoard)
                cell.add(spriteComponent)
                engine.addEntity(cell)
            }
        }
        addShadow(
            0f,
            spriteBoardHeight * 8 + widthOffset,
            SIZE_SHADOW,
        )
        addShadow(
            180f,
            widthOffset - SIZE_SHADOW,
            -SIZE_SHADOW,
        )
        addShadow(
            270f,
            widthOffset - 5f + WORlD_ORIGIN_HEIGHT,
            -WORlD_ORIGIN_HEIGHT + 5f,
        )
        addShadow(
            90f,
            WORLD_ORIGIN_WIDTH - 25f,
            WORlD_ORIGIN_HEIGHT - 5f,
        )
    }

    fun addBackground() {
        val backgroundEntity = Entity()
        val spriteComponent = SpriteComponent(
            Sprite(
                uiSkin.atlas.findRegion(BACKGROUND_PAPER_ID)
            ).apply {
                setSize(width * 2.5f, height * 2.5f)
                x = (WORLD_WIDTH - width) / 2
                y = (WORLD_HEIGHT - height) / 2
            }
        )
        val backgroundComponent = BackgroundComponent()
        backgroundEntity.add(spriteComponent)
        backgroundEntity.add(backgroundComponent)
        engine.addEntity(backgroundEntity)
    }

    private fun addShadow(
        rotate: Float,
        x: Float,
        y: Float,
    ) {
        val shadowEntity = Entity()
        val shadowComponent = ShadowComponent()
        shadowEntity.add(shadowComponent)
        val spriteShadow = Sprite(gameAtlas.findRegion(RIGHT_SHADOW_BOARD))
        spriteShadow.setSize(
            SIZE_SHADOW,
            WORLD_HEIGHT - SIZE_SHADOW * 2
        )
        spriteShadow.setOrigin(SIZE_SHADOW / 2f, WORLD_HEIGHT / 2f)
        spriteShadow.setPosition(
            x,
            y
        )
        spriteShadow.rotation = rotate
        val spriteComponent = SpriteComponent(spriteShadow)
        shadowEntity.add(spriteComponent)
        engine.addEntity(shadowEntity)
    }

    private fun getLightColorBoard() = if (
        savedSettingsManager.getBoardTheme() == GRAY_BOARD_OPTION
    ) gameAtlas.findRegion(SQUARE_GRAY_LIGHT_ID) else gameAtlas.findRegion(SQUARE_BROWN_LIGHT_ID)

    private fun getDarkColorBoard() = if (
        savedSettingsManager.getBoardTheme() == GRAY_BOARD_OPTION
    ) gameAtlas.findRegion(SQUARE_GRAY_DARK_ID) else gameAtlas.findRegion(SQUARE_BROWN_DARK_ID)
}