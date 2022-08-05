package com.goncharov.evgeny.chess

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.goncharov.evgeny.chess.consts.CLICK_BUTTON_SOUND_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.GAME_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.screens.GameScreen
import com.goncharov.evgeny.chess.screens.MainMenuScreen
import com.goncharov.evgeny.chess.screens.SettingsScreen
import com.goncharov.evgeny.chess.screens.SplashScreen
import com.goncharov.evgeny.chess.utils.debug

class App : Game(), Navigator {

    private val batch by lazy {
        SpriteBatch()
    }
    private val assetManager = AssetManager()
    private val debugRender by lazy {
        ShapeRenderer()
    }

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        debug(TAG, "start application")
        assetManager.load(GAME_ASSET_DESCRIPTOR)
        assetManager.load(UI_ASSET_DESCRIPTOR)
        assetManager.load(CLICK_BUTTON_SOUND_DESCRIPTOR)
        assetManager.finishLoading()
        navigation(NavigationKey.SplashScreenKey)
    }

    override fun navigation(key: NavigationKey) {
        debug(TAG, "navigation ${key::class.java.simpleName}")
        when (key) {
            NavigationKey.SplashScreenKey -> setScreen(
                SplashScreen(this, batch, assetManager)
            )
            NavigationKey.MainMenuScreenKey -> setScreen(
                MainMenuScreen(this, batch, assetManager)
            )
            NavigationKey.GameScreenKey -> setScreen(
                GameScreen()
            )
            NavigationKey.SettingScreenKey -> setScreen(
                SettingsScreen()
            )
        }
    }

    override fun dispose() {
        debug(TAG, "dispose")
        batch.dispose()
        assetManager.dispose()
        debugRender.dispose()
    }

    companion object {
        private const val TAG = "App"
    }
}