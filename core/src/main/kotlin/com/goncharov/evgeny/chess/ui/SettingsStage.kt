package com.goncharov.evgeny.chess.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.extensions.addListenerKtx
import com.goncharov.evgeny.chess.interactors.SettingsInteractor
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator

class SettingsStage(
    viewport: Viewport,
    bach: SpriteBatch,
    resourceManager: ResourceManager,
    private val navigator: Navigator,
    private val uiSkin: Skin,
    private val settingsInteractor: SettingsInteractor
) : Stage(viewport, bach) {

    private val soundClickButton = resourceManager[CLICK_BUTTON_SOUND_DESCRIPTOR]
    private val colorBoardThemeButtonGroup = ButtonGroup<CheckBox>()
    private val movingPlayerButtonGroup = ButtonGroup<CheckBox>()
    private var rootTable: Table? = null

    init {
        initUi()
    }

    private fun initUi() {
        rootTable = Table()
        rootTable?.background = uiSkin.getDrawable(BACKGROUND_PAPER_ID)
        rootTable?.setFillParent(true)
        val colorBoardersTable = Table()
        initTheTopBoardColorBoarders(colorBoardersTable)
        initCheckboxesBoardColor(colorBoardersTable)
        initTheBottomBoardColorBoarders(colorBoardersTable)
        val firstMoveContentTable = Table()
        initTheTopFirstMovingBoarders(firstMoveContentTable)
        initFirstMovingCheckBoxes(firstMoveContentTable)
        initTheBottomFirstMovingBoarders(firstMoveContentTable)
        initBackButton()
        addActor(rootTable)
    }

    private fun initBackButton() {
        val backButton = ImageTextButton(TEXT_BACK, uiSkin)
        rootTable?.add(backButton)?.spaceTop(12.0f)
        backButton.addListenerKtx(::clickBack)
    }

    private fun initTheTopBoardColorBoarders(contentTable: Table) {
        val temporaryTable = Table()
        var image = Image(uiSkin, LINE_1_ID)
        temporaryTable.add(image)
        image = Image(uiSkin, LINE_8_ID)
        temporaryTable.add(image).padTop(-9.0f)
        val label = Label(TEXT_BOARD_COLOR, uiSkin)
        temporaryTable.add(label).padTop(-10.0f).spaceLeft(12.0f).spaceRight(12.0f)
        image = Image(uiSkin, LINE_7_ID)
        temporaryTable.add(image).padTop(-6.0f)
        image = Image(uiSkin, LINE_2_ID)
        temporaryTable.add(image).padTop(-6.0f).growX()
        image = Image(uiSkin, LINE_3_ID)
        temporaryTable.add(image).padTop(1.0f)
        contentTable.add(temporaryTable).growX()
        contentTable.row()
    }

    private fun initTheBottomBoardColorBoarders(contentTable: Table) {
        val temporaryTable = Table()
        var image = Image(uiSkin, LINE_5_ID)
        temporaryTable.add(image).padLeft(-1.0f)
        image = Image(uiSkin, LINE_2_ID)
        temporaryTable.add(image).padTop(7.0f).growX()
        image = Image(uiSkin, LINE_6_ID)
        temporaryTable.add(image)
        contentTable.add(temporaryTable).growX()
        rootTable?.add(contentTable)?.fillX()
        rootTable?.row()
    }

    private fun initCheckboxesBoardColor(
        contentTable: Table
    ) {
        val temporaryTable = Table()
        var image = Image(uiSkin, LINE_4_ID)
        temporaryTable.add(image).padLeft(-1.0f).padTop(-8.0f).fill(true)
        val tableCheckBoxColorBlack = Table()
        val checkBoxColorBlack = CheckBox(TEXT_BOARD_COLOR_BROWN, uiSkin)
        checkBoxColorBlack.name = SavedSettingsManager.BROWN_BOARD_OPTION
        tableCheckBoxColorBlack.add(checkBoxColorBlack).spaceBottom(10.0f).expandX()
            .align(Align.left)
        checkBoxColorBlack.addListenerKtx(::changeBoardTheme)
        tableCheckBoxColorBlack.row()
        val checkBoxWhiteColor = CheckBox(TEXT_BOARD_COLOR_GRAY, uiSkin)
        checkBoxWhiteColor.addListenerKtx(::changeBoardTheme)
        checkBoxWhiteColor.name = SavedSettingsManager.GRAY_BOARD_OPTION
        colorBoardThemeButtonGroup.add(checkBoxWhiteColor)
        colorBoardThemeButtonGroup.add(checkBoxColorBlack)
        colorBoardThemeButtonGroup.setChecked(settingsInteractor.getTextBoardChecked())
        tableCheckBoxColorBlack.add(checkBoxWhiteColor).expandX().align(Align.left)
        temporaryTable.add(tableCheckBoxColorBlack).growX()
        image = Image(uiSkin, LINE_4_ID)
        temporaryTable.add(image).fill(true)
        contentTable.add(temporaryTable).fillX()
        contentTable.row()
    }

    private fun initTheTopFirstMovingBoarders(contentTable: Table) {
        val temporaryTable = Table()
        var image = Image(uiSkin, LINE_1_ID)
        temporaryTable.add(image).padTop(6.0f)
        image = Image(uiSkin, LINE_8_ID)
        temporaryTable.add(image).padTop(-4.0f)
        val label = Label(TEXT_FIRST_MOVE, uiSkin)
        temporaryTable.add(label).spaceLeft(12.0f).spaceRight(12.0f)
        image = Image(uiSkin, LINE_7_ID)
        temporaryTable.add(image)
        image = Image(uiSkin, LINE_2_ID)
        temporaryTable.add(image)
        image = Image(uiSkin, LINE_3_ID)
        temporaryTable.add(image).padTop(8.0f)
        contentTable.add(temporaryTable)
        contentTable.row()
    }

    private fun initFirstMovingCheckBoxes(contentTable: Table) {
        val temporaryTable = Table()
        var image = Image(uiSkin, LINE_4_ID)
        temporaryTable.add(image).padLeft(-1.0f).padTop(-6.0f).fill(true)
        val tableCheckBoxes = Table()
        val firstWhiteCheckBox = CheckBox(TEXT_FIRST_MOVE_WHITE, uiSkin)
        tableCheckBoxes.add(firstWhiteCheckBox).spaceBottom(10.0f).expandX().align(Align.left)
        firstWhiteCheckBox.addListenerKtx(::changeMovePlayer)
        firstWhiteCheckBox.name = SavedSettingsManager.FIRST_MOVING_WHITE_OPTION
        tableCheckBoxes.row()
        val firstBlackCheckBox = CheckBox(TEXT_FIRST_MOVE_BLACK, uiSkin)
        tableCheckBoxes.add(firstBlackCheckBox).align(Align.left)
        firstBlackCheckBox.addListenerKtx(::changeMovePlayer)
        firstBlackCheckBox.name = SavedSettingsManager.FIRST_MOVING_BLACK_OPTION
        temporaryTable.add(tableCheckBoxes).growX().align(Align.left)
        movingPlayerButtonGroup.add(firstWhiteCheckBox)
        movingPlayerButtonGroup.add(firstBlackCheckBox)
        movingPlayerButtonGroup.setChecked(settingsInteractor.getTextFirstMovingChecked())
        image = Image(uiSkin, LINE_4_ID)
        temporaryTable.add(image).fill(true)
        contentTable.add(temporaryTable).fillX()
        contentTable.row()
    }

    private fun initTheBottomFirstMovingBoarders(contentTable: Table) {
        val temporaryTable = Table()
        var image = Image(uiSkin, LINE_5_ID)
        temporaryTable.add(image).padLeft(-1.0f)
        image = Image(uiSkin, LINE_2_ID)
        temporaryTable.add(image).padTop(8.0f).growX()
        image = Image(uiSkin, LINE_6_ID)
        temporaryTable.add(image)
        contentTable.add(temporaryTable).growX()
        rootTable?.add(contentTable)?.spaceTop(12.0f)?.fillX()
        rootTable?.row()
    }

    private fun clickBack() {
        soundClickButton.play()
        navigator.navigation(NavigationKey.MainMenuScreenKey)
    }

    private fun changeBoardTheme() {
        soundClickButton.play()
        colorBoardThemeButtonGroup.checked ?: return
        settingsInteractor.savedBoardTheme(
            colorBoardThemeButtonGroup.checked?.name ?: SavedSettingsManager.GRAY_BOARD_OPTION
        )
    }

    private fun changeMovePlayer() {
        soundClickButton.play()
        movingPlayerButtonGroup.checked ?: return
        settingsInteractor.savedFirstMoving(
            movingPlayerButtonGroup.checked.name
        )
    }
}