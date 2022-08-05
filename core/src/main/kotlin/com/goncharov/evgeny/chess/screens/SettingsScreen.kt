package com.goncharov.evgeny.chess.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.CLICK_BUTTON_SOUND_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_HEIGHT
import com.goncharov.evgeny.chess.consts.UI_WIDTH
import com.goncharov.evgeny.chess.extensions.addListenerKtx
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class SettingsScreen(
    private val navigator: Navigator,
    bach: SpriteBatch,
    assetManager: AssetManager
) : BaseScreen() {

    private val viewport = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val stage = Stage(viewport, bach)
    private val uiSkin = assetManager[UI_ASSET_DESCRIPTOR]
    private val soundClickButton = assetManager[CLICK_BUTTON_SOUND_DESCRIPTOR]
    private val colorBoardThemeButtonGroup = ButtonGroup<CheckBox>()
    private val movingPlayerButtonGroup = ButtonGroup<CheckBox>()

    override fun show() {
        debug(TAG, "show()")
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.background = uiSkin.getDrawable("paper background")
        table.setFillParent(true)
        var table1 = Table()
        var table2 = Table()
        var image = Image(uiSkin, "line_1")
        table2.add(image)
        image = Image(uiSkin, "line_8")
        table2.add(image).padTop(-9.0f)
        var label: Label? = Label("Board color", uiSkin)
        table2.add(label).padTop(-10.0f).spaceLeft(12.0f).spaceRight(12.0f)
        image = Image(uiSkin, "line_7")
        table2.add(image).padTop(-6.0f)
        image = Image(uiSkin, "line_2")
        table2.add(image).padTop(-6.0f).growX()
        image = Image(uiSkin, "line_3")
        table2.add(image).padTop(1.0f)
        table1.add(table2).growX()
        table1.row()
        table2 = Table()
        image = Image(uiSkin, "line_4")
        table2.add(image).padLeft(-1.0f).padTop(-8.0f).fill(true)
        var table3 = Table()
        val checkBoxColorBlack = CheckBox(" Board color black", uiSkin)
        checkBoxColorBlack.name = "blackBoard"
        table3.add(checkBoxColorBlack).spaceBottom(10.0f).expandX().align(Align.left)
        checkBoxColorBlack.addListenerKtx(::changeBoardTheme)
        table3.row()
        val checkBoxWhiteColor = CheckBox(" Board color white", uiSkin)
        checkBoxWhiteColor.addListenerKtx(::changeBoardTheme)
        checkBoxWhiteColor.name = "whiteBoard"
        colorBoardThemeButtonGroup.add(checkBoxColorBlack)
        colorBoardThemeButtonGroup.add(checkBoxWhiteColor)
        colorBoardThemeButtonGroup.setChecked("blackBoard")
        table3.add(checkBoxWhiteColor).expandX().align(Align.left)
        table2.add(table3).growX()
        image = Image(uiSkin, "line_4")
        table2.add(image).fill(true)
        table1.add(table2).fillX()
        table1.row()
        table2 = Table()
        image = Image(uiSkin, "line_5")
        table2.add(image).padLeft(-1.0f)
        image = Image(uiSkin, "line_2")
        table2.add(image).padTop(7.0f).growX()
        image = Image(uiSkin, "line_6")
        table2.add(image)
        table1.add(table2).growX()
        table.add(table1).fillX()
        table.row()
        table1 = Table()
        table2 = Table()
        image = Image(uiSkin, "line_1")
        table2.add(image).padTop(6.0f)
        image = Image(uiSkin, "line_8")
        table2.add(image).padTop(-4.0f)
        label = Label("The first move", uiSkin)
        table2.add(label).spaceLeft(12.0f).spaceRight(12.0f)
        image = Image(uiSkin, "line_7")
        table2.add(image)
        image = Image(uiSkin, "line_2")
        table2.add(image)
        image = Image(uiSkin, "line_3")
        table2.add(image).padTop(8.0f)
        table1.add(table2)
        table1.row()
        table2 = Table()
        image = Image(uiSkin, "line_4")
        table2.add(image).padLeft(-1.0f).padTop(-6.0f).fill(true)
        table3 = Table()
        val firstWhiteCheckBox = CheckBox(" First move white player", uiSkin)
        table3.add(firstWhiteCheckBox).spaceBottom(10.0f).expandX().align(Align.left)
        firstWhiteCheckBox.addListenerKtx(::changeMovePlayer)
        firstWhiteCheckBox.name = "firstMovingWhite"
        table3.row()
        val firstBlackCheckBox = CheckBox(" First move black player", uiSkin)
        table3.add(firstBlackCheckBox).align(Align.left)
        firstBlackCheckBox.addListenerKtx(::changeMovePlayer)
        firstBlackCheckBox.name = "firstMovingBlack"
        table2.add(table3).growX().align(Align.left)
        movingPlayerButtonGroup.add(firstWhiteCheckBox)
        movingPlayerButtonGroup.add(firstBlackCheckBox)
        movingPlayerButtonGroup.setChecked("firstMovingWhite")
        image = Image(uiSkin, "line_4")
        table2.add(image).fill(true)
        table1.add(table2).fillX()
        table1.row()
        table2 = Table()
        image = Image(uiSkin, "line_5")
        table2.add(image).padLeft(-1.0f)
        image = Image(uiSkin, "line_2")
        table2.add(image).padTop(8.0f).growX()
        image = Image(uiSkin, "line_6")
        table2.add(image)
        table1.add(table2).growX()
        table.add(table1).spaceTop(12.0f).fillX()
        table.row()
        val imageTextButton = ImageTextButton("Back", uiSkin)
        table.add(imageTextButton).spaceTop(12.0f)
        imageTextButton.addListenerKtx(::clickBack)
        stage.addActor(table)
    }

    private fun clickBack() {
        soundClickButton.play()
        navigator.navigation(NavigationKey.MainMenuScreenKey)
    }

    private fun changeBoardTheme() {
        soundClickButton.play()
    }

    private fun changeMovePlayer() {
        soundClickButton.play()
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
        private const val TAG = "SettingsScreen"
    }
}