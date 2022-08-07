package com.goncharov.evgeny.chess.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.UI_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_WIDTH
import com.goncharov.evgeny.chess.consts.WORLD_HEIGHT
import com.goncharov.evgeny.chess.consts.WORLD_WIDTH
import com.goncharov.evgeny.chess.factory.ChessBoardFactory
import com.goncharov.evgeny.chess.managers.MusicManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.systems.RenderSystem
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class GameScreen(
    private val batch: SpriteBatch,
    assetManager: AssetManager,
    private val debugRender: ShapeRenderer,
    private val musicManager: MusicManager,
    savedSettingsManager: SavedSettingsManager,
    private val navigator: Navigator
) : BaseScreen() {

    private val viewport = FillViewport(WORLD_WIDTH, WORLD_HEIGHT)
    private val hudViewport = FillViewport(UI_WIDTH, WORLD_HEIGHT)
    private val engine = Engine()
    private val stage = Stage(hudViewport, batch)
    private val uiSkin = assetManager[UI_ASSET_DESCRIPTOR]
    private val chessBoardFactory =
        ChessBoardFactory(engine, savedSettingsManager, uiSkin, assetManager)

    override fun show() {
        debug(TAG, "show()")
        Gdx.input.inputProcessor = stage
        chessBoardFactory.buildChessBoard()
        chessBoardFactory.addBackground()
        engine.addSystem(RenderSystem(viewport, batch))
    }

    override fun render(delta: Float) {
        clearScreen()
        engine.update(delta)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        debug(TAG, "resize()")
        viewport.update(width, height, true)
        hudViewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
        musicManager.startMainMusic()
    }

    override fun dispose() {
        debug(TAG, "dispose()")
        stage.dispose()
        Gdx.input.inputProcessor = null
    }

    companion object {
        private const val TAG = "GameScreen"
    }
}