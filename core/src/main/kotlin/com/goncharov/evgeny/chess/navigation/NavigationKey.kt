package com.goncharov.evgeny.chess.navigation

sealed class NavigationKey {

    object MainMenuScreenKey : NavigationKey()

    object SettingScreenKey : NavigationKey()

    object GameScreenKey : NavigationKey()

    object SplashScreenKey : NavigationKey()
}