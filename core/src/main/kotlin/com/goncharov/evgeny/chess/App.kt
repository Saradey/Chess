package com.goncharov.evgeny.chess

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.goncharov.evgeny.chess.consts.MAIN_MUSIC_DESCRIPTOR
import com.goncharov.evgeny.chess.managers.MusicManager
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.screens.game.GameScreenImpl
import com.goncharov.evgeny.chess.screens.main.menu.MainMenuScreenImpl
import com.goncharov.evgeny.chess.screens.settings.SettingsScreenImpl
import com.goncharov.evgeny.chess.screens.splash.SplashScreenImpl
import com.goncharov.evgeny.chess.utils.debug

class App : Game(), Navigator {

    private val batch by lazy {
        SpriteBatch()
    }
    private val savedSettingsManager = SavedSettingsManager()
    private val resourceManager = ResourceManager(AssetManager())
    private val musicManager by lazy {
        MusicManager(resourceManager[MAIN_MUSIC_DESCRIPTOR])
    }

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        debug(TAG, "start application")
        resourceManager.loadAllResources()
        navigation(NavigationKey.SplashScreenKey)
    }

    override fun navigation(key: NavigationKey) {
        debug(TAG, "navigation ${key::class.java.simpleName}")
        when (key) {
            NavigationKey.SplashScreenKey -> setScreen(
                SplashScreenImpl(this, batch, resourceManager)
            )
            NavigationKey.MainMenuScreenKey -> setScreen(
                MainMenuScreenImpl(this, batch, resourceManager, musicManager)
            )
            NavigationKey.GameScreenKey -> setScreen(
                GameScreenImpl(
                    batch,
                    resourceManager,
                    savedSettingsManager,
                    this
                )
            )
            NavigationKey.SettingScreenKey -> setScreen(
                SettingsScreenImpl(this, batch, resourceManager, savedSettingsManager)
            )
        }
    }

    override fun dispose() {
        debug(TAG, "dispose")
        batch.dispose()
        resourceManager.disposeAllResources()
    }

    companion object {
        private const val TAG = "App"
    }
}