package com.goncharov.evgeny.chess.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.goncharov.evgeny.chess.components.*
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager.Companion.GRAY_BOARD_OPTION

class ChessBoardFactory(
    private val engine: Engine,
    private val savedSettingsManager: SavedSettingsManager,
    private val uiSkin: Skin,
    resourceManager: ResourceManager
) {
    private val gameAtlas = resourceManager[GAME_ASSET_DESCRIPTOR]

    fun buildChessBoard() {
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
                spriteBoard.setSize(SPRITE_SIZE, SPRITE_SIZE)
                val xPosition = x * SPRITE_SIZE + WIDTH_OFFSET
                val yPosition = y * SPRITE_SIZE + heightOffset
                spriteBoard.setPosition(
                    xPosition,
                    yPosition
                )
                val cellEntity = Entity()
                val cellComponent = CellComponent(
                    Vector2(
                        spriteBoard.x + spriteBoard.width / 2,
                        spriteBoard.y + spriteBoard.height / 2
                    ),
                    Pair(
                        ((xPosition - WIDTH_OFFSET) / SPRITE_SIZE).toInt(),
                        ((yPosition - SIZE_SHADOW) / SPRITE_SIZE).toInt()
                    )
                )
                cellEntity.add(cellComponent)
                val spriteComponent = SpriteComponent(spriteBoard)
                val layerComponent = LayerComponent(SPRITE_LAYER_2)
                cellEntity.add(spriteComponent)
                cellEntity.add(layerComponent)
                engine.addEntity(cellEntity)
            }
        }
        addShadow(
            0f,
            SPRITE_SIZE * 8 + WIDTH_OFFSET,
            SIZE_SHADOW,
        )
        addShadow(
            180f,
            WIDTH_OFFSET - SIZE_SHADOW,
            -SIZE_SHADOW,
        )
        addShadow(
            270f,
            WIDTH_OFFSET - 5f + WORlD_ORIGIN_HEIGHT,
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
        val layerComponent = LayerComponent(SPRITE_LAYER_1)
        backgroundEntity.add(spriteComponent)
        backgroundEntity.add(backgroundComponent)
        backgroundEntity.add(layerComponent)
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
        val layerComponent = LayerComponent(SPRITE_LAYER_2)
        shadowEntity.add(layerComponent)
        engine.addEntity(shadowEntity)
    }

    private fun getLightColorBoard() = if (
        savedSettingsManager.getBoardTheme() == GRAY_BOARD_OPTION
    ) gameAtlas.findRegion(SQUARE_GRAY_LIGHT_ID) else gameAtlas.findRegion(SQUARE_BROWN_LIGHT_ID)

    private fun getDarkColorBoard() = if (
        savedSettingsManager.getBoardTheme() == GRAY_BOARD_OPTION
    ) gameAtlas.findRegion(SQUARE_GRAY_DARK_ID) else gameAtlas.findRegion(SQUARE_BROWN_DARK_ID)
}