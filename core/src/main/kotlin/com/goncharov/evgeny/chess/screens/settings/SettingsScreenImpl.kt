package com.goncharov.evgeny.chess.screens.settings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.ui.settings.SettingsInteractorImpl
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.ui.settings.SettingsStageImpl
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class SettingsScreenImpl(
    private val navigator: Navigator,
    bach: SpriteBatch,
    resourceManager: ResourceManager,
    savedSettingsManager: SavedSettingsManager
) : ScreenAdapter(), SettingsScreen {

    private val viewport = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val stage = SettingsStageImpl(
        viewport,
        bach,
        resourceManager,
        this,
        resourceManager[UI_ASSET_DESCRIPTOR],
        SettingsInteractorImpl(savedSettingsManager)
    )

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

    override fun goToTheMainMenu() {
        navigator.navigation(NavigationKey.MainMenuScreenKey)
    }

    companion object {
        private const val TAG = "SettingsScreen"
    }
}