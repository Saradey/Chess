package com.goncharov.evgeny.chess

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator

class App : Game(), Navigator {

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
    }

    override fun navigation(key: NavigationKey) {
        when (key) {
            NavigationKey.MainMenuScreenKey -> {

            }
            NavigationKey.GameScreenKey -> {

            }
            NavigationKey.SettingScreenKey -> {

            }
        }
    }
}