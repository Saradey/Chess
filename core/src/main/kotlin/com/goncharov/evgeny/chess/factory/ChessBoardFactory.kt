package com.goncharov.evgeny.chess.factory

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.goncharov.evgeny.chess.components.BackgroundComponent
import com.goncharov.evgeny.chess.components.SpriteComponent
import com.goncharov.evgeny.chess.consts.BACKGROUND_PAPER_ID
import com.goncharov.evgeny.chess.consts.WORLD_HEIGHT
import com.goncharov.evgeny.chess.consts.WORLD_WIDTH
import com.goncharov.evgeny.chess.managers.SavedSettingsManager

class ChessBoardFactory(
    private val engine: Engine,
    private val savedSettingsManager: SavedSettingsManager,
    private val uiSkin: Skin
) {

    fun buildChessBoard() {

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
}