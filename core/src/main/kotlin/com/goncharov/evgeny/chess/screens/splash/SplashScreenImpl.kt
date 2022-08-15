package com.goncharov.evgeny.chess.screens.splash

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.UI_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_HEIGHT
import com.goncharov.evgeny.chess.consts.UI_WIDTH
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.ui.SplashStageImpl
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class SplashScreenImpl(
    private val navigator: Navigator,
    bach: SpriteBatch,
    resourceManager: ResourceManager
) : BaseScreen(), SplashScreen {

    private val viewPort = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val stage = SplashStageImpl(
        resourceManager[UI_ASSET_DESCRIPTOR],
        bach,
        viewPort,
        this
    )

    override fun show() {
        debug(TAG, "show()")
    }

    override fun render(delta: Float) {
        clearScreen()
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        debug(TAG, "resize()")
        viewPort.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        debug(TAG, "dispose()")
        stage.dispose()
    }

    override fun goToTheMainMenu() {
        navigator.navigation(NavigationKey.MainMenuScreenKey)
    }

    companion object {
        private const val TAG = "SplashScreen"
    }
}