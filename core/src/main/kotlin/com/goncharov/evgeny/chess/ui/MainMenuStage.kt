package com.goncharov.evgeny.chess.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.extensions.addListenerKtx
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator

class MainMenuStage(
    viewport: Viewport,
    batch: SpriteBatch,
    resourceManager: ResourceManager,
    private val navigator: Navigator
) : Stage(viewport, batch) {

    private val uiSkin: Skin = resourceManager[UI_ASSET_DESCRIPTOR]
    private val soundClickButton: Sound = resourceManager[CLICK_BUTTON_SOUND_DESCRIPTOR]
    private val rootTable = Table()

    init {
        initUi()
    }

    private fun initUi() {
        rootTable.background = uiSkin.getDrawable(BACKGROUND_PAPER_ID)
        rootTable.setFillParent(true)
        initBoardersTop()
        initButtons()
        initBottomBoarders()
        addActor(rootTable)
    }

    private fun initBoardersTop() {
        val image1 = Image(uiSkin, LINE_1_ID)
        rootTable.add(image1).padLeft(8.0f).padTop(33.0f)
        val table2 = Table()
        val image2 = Image(uiSkin, LINE_8_ID)
        table2.add(image2)
        val label = Label(TEXT_MENU, uiSkin)
        table2.add(label).spaceLeft(12.0f).spaceRight(12.0f)
        val image3 = Image(uiSkin, LINE_7_ID)
        table2.add(image3)
        val image4 = Image(uiSkin, LINE_2_ID)
        table2.add(image4).growX()
        rootTable.add(table2).padTop(23.0f).fillX()
        val image5 = Image(uiSkin, LINE_3_ID)
        rootTable.add(image5).padLeft(-8.0f).padBottom(-31.0f)
        rootTable.row()
        val image6 = Image(uiSkin, LINE_4_ID)
        rootTable.add(image6).fillY()
    }

    private fun initButtons() {
        val tableButtons = Table()
        val textButtonStartGame = ImageTextButton(TEXT_PLAYER_GAME, uiSkin)
        textButtonStartGame.addListenerKtx(::startPlayerVsPlayerGame)
        tableButtons.add(textButtonStartGame)
        tableButtons.row()
        val textButtonStartGameWithComputer = ImageTextButton(TEXT_PLAYER_WITH_COMPUTER, uiSkin)
        textButtonStartGameWithComputer.isDisabled = true
        tableButtons.add(textButtonStartGameWithComputer)
        tableButtons.row()
        val textButtonSettings = ImageTextButton(TEXT_SETTINGS, uiSkin)
        textButtonSettings.addListenerKtx(::startSettings)
        tableButtons.add(textButtonSettings)
        tableButtons.row()
        val textButtonExit = ImageTextButton(TEXT_EXIT, uiSkin)
        textButtonExit.addListenerKtx(::exitApplication)
        tableButtons.add(textButtonExit)
        rootTable.add(tableButtons)
    }

    private fun initBottomBoarders() {
        val image7 = Image(uiSkin, LINE_4_ID)
        rootTable.add(image7).fillY()
        rootTable.row()
        val image8 = Image(uiSkin, LINE_5_ID)
        rootTable.add(image8).padRight(-7.0f)
        val image9 = Image(uiSkin, LINE_2_ID)
        rootTable.add(image9).padLeft(-13.0f).padRight(-13.0f).padTop(8.0f).fillX()
        val image10 = Image(uiSkin, LINE_6_ID)
        rootTable.add(image10).padRight(8.0f)
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
}