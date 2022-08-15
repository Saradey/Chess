package com.goncharov.evgeny.chess.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.consts.TEXT_LOGO
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.screens.splash.SplashScreen

class SplashStageImpl(
    private val uiSkin: Skin,
    bach: SpriteBatch,
    viewport: Viewport,
    private val splashScreen: SplashScreen
) : Stage(viewport, bach) {

    private var labelLogo: Label? = null

    init {
        initUi()
        initAction()
    }

    private fun initUi() {
        val table = Table()
        table.setFillParent(true)
        labelLogo = Label(TEXT_LOGO, uiSkin)
        labelLogo?.setAlignment(Align.center)
        table.add(labelLogo)
        addActor(table)
    }

    private fun initAction() {
        labelLogo?.addAction(
            Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(1f),
                Actions.fadeOut(1f),
                Actions.run {
                    splashScreen.goToTheMainMenu()
                }
            )
        )
    }
}