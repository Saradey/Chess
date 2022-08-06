package com.goncharov.evgeny.chess.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.extensions.addListenerKtx
import com.goncharov.evgeny.chess.managers.MusicManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class MainMenuScreen(
    private val navigator: Navigator,
    batch: SpriteBatch,
    assetManager: AssetManager,
    private val musicManager: MusicManager
) : BaseScreen() {

    private val viewport = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val stage = Stage(viewport, batch)
    private val uiSkin = assetManager[UI_ASSET_DESCRIPTOR]
    private val soundClickButton = assetManager[CLICK_BUTTON_SOUND_DESCRIPTOR]

    override fun show() {
        debug(TAG, "show()")
        musicManager.startMainMusic()
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.background = uiSkin.getDrawable(BACKGROUND_PAPER_ID)
        table.setFillParent(true)
        val image1 = Image(uiSkin, LINE_1_ID)
        table.add(image1).padLeft(8.0f).padTop(33.0f)
        val table2 = Table()
        val image2 = Image(uiSkin, LINE_8_ID)
        table2.add(image2)
        val label = Label(TEXT_MENU, uiSkin)
        table2.add(label).spaceLeft(12.0f).spaceRight(12.0f)
        val image3 = Image(uiSkin, LINE_7_ID)
        table2.add(image3)
        val image4 = Image(uiSkin, LINE_2_ID)
        table2.add(image4).growX()
        table.add(table2).padTop(23.0f).fillX()
        val image5 = Image(uiSkin, LINE_3_ID)
        table.add(image5).padLeft(-8.0f).padBottom(-31.0f)
        table.row()
        val image6 = Image(uiSkin, LINE_4_ID)
        table.add(image6).fillY()
        val table3 = Table()
        val textButtonStartGame = ImageTextButton(TEXT_PLAYER_GAME, uiSkin)
        textButtonStartGame.addListenerKtx(::startPlayerVsPlayerGame)
        table3.add(textButtonStartGame)
        table3.row()
        val textButtonStartGameWithComputer = ImageTextButton(TEXT_PLAYER_WITH_COMPUTER, uiSkin)
        textButtonStartGameWithComputer.isDisabled = true
        table3.add(textButtonStartGameWithComputer)
        table3.row()
        val textButtonSettings = ImageTextButton(TEXT_SETTINGS, uiSkin)
        textButtonSettings.addListenerKtx(::startSettings)
        table3.add(textButtonSettings)
        table3.row()
        val textButtonExit = ImageTextButton(TEXT_EXIT, uiSkin)
        textButtonExit.addListenerKtx(::exitApplication)
        table3.add(textButtonExit)
        table.add(table3)
        val image7 = Image(uiSkin, LINE_4_ID)
        table.add(image7).fillY()
        table.row()
        val image8 = Image(uiSkin, LINE_5_ID)
        table.add(image8).padRight(-7.0f)
        val image9 = Image(uiSkin, LINE_2_ID)
        table.add(image9).padLeft(-13.0f).padRight(-13.0f).padTop(8.0f).fillX()
        val image10 = Image(uiSkin, LINE_6_ID)
        table.add(image10).padRight(8.0f)
        stage.addActor(table)
    }

    private fun startPlayerVsPlayerGame() {
        soundClickButton.play()
        navigator.navigation(NavigationKey.GameScreenKey)
    }

    private fun startSettings() {
        soundClickButton.play()
        navigator.navigation(NavigationKey.SettingScreenKey)
    }

    private fun exitApplication() {
        soundClickButton.play()
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