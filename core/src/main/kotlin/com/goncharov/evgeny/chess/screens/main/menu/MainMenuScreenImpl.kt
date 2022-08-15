package com.goncharov.evgeny.chess.screens.main.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.managers.MusicManager
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.ui.MainMenuStageImpl
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class MainMenuScreenImpl(
    private val navigator: Navigator,
    batch: SpriteBatch,
    resourceManager: ResourceManager,
    musicManager: MusicManager
) : BaseScreen(), MainMenuScreen {

    private val viewport = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val stage = MainMenuStageImpl(
        viewport,
        batch,
        resourceManager,
        this
    )

    init {
        musicManager.startMainMusic()
    }

    override fun show() {
        debug(TAG, "show()")
        Gdx.input.inputProcessor = stage
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

    override fun goToTheGameScreen() {
        navigator.navigation(NavigationKey.GameScreenKey)
    }

    override fun goToTheSettingsScreen() {
        navigator.navigation(NavigationKey.SettingScreenKey)
    }

    companion object {
        private const val TAG = "MainMenuScreen"
    }
}