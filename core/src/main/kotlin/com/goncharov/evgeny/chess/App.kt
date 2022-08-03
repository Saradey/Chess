package com.goncharov.evgeny.chess

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.screens.GameScreen
import com.goncharov.evgeny.chess.screens.MainMenuScreen
import com.goncharov.evgeny.chess.screens.SettingsScreen

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
        navigation(NavigationKey.MainMenuScreenKey)
    }

    override fun navigation(key: NavigationKey) {
        when (key) {
            NavigationKey.MainMenuScreenKey -> setScreen(MainMenuScreen())
            NavigationKey.GameScreenKey -> setScreen(GameScreen())
            NavigationKey.SettingScreenKey -> setScreen(SettingsScreen())
        }
    }
}