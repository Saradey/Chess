package com.goncharov.evgeny.chess.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.extensions.addListenerKtx
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class MainMenuScreen(
    private val navigator: Navigator,
    bach: SpriteBatch,
    assetManager: AssetManager
) : BaseScreen() {

    private val viewport = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val stage = Stage(viewport, bach)
    private val uiSkin = assetManager[UI_ASSET_DESCRIPTOR]

    override fun show() {
        debug(TAG, "show()")
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.background = uiSkin.getDrawable(BACKGROUND_PAPER_ID)
        table.setFillParent(true)
        val textButtonStartGame = ImageTextButton(TEXT_PLAYER_GAME, uiSkin)
        textButtonStartGame.addListenerKtx(::startPlayerVsPlayerGame)
        table.add(textButtonStartGame)
        table.row()
        val textButtonStartGameWithComputer = ImageTextButton(TEXT_PLAYER_WITH_COMPUTER, uiSkin)
        textButtonStartGameWithComputer.isDisabled = true
        table.add(textButtonStartGameWithComputer)
        table.row()
        val textButtonSettings = ImageTextButton(TEXT_SETTINGS, uiSkin)
        textButtonSettings.addListenerKtx(::startSettings)
        table.add(textButtonSettings)
        table.row()
        val textButtonExit = ImageTextButton(TEXT_EXIT, uiSkin)
        textButtonExit.addListenerKtx(::exitApplication)
        table.add(textButtonExit)
        stage.addActor(table)
    }

    private fun startPlayerVsPlayerGame() {
        navigator.navigation(NavigationKey.GameScreenKey)
    }

    private fun startSettings() {
        navigator.navigation(NavigationKey.SettingScreenKey)
    }

    private fun exitApplication() {
        Gdx.app.exit()
    }

    override fun render(delta: Float) {
        clearScreen()
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        debug(TAG, "resize()")
        viewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        debug(TAG, "dispose()")
        Gdx.input.inputProcessor = null
        stage.dispose()
    }

    companion object {
        private const val TAG = "MainMenuScreen"
    }
}