package com.goncharov.evgeny.chess.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.TEXT_LOGO
import com.goncharov.evgeny.chess.consts.UI_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_HEIGHT
import com.goncharov.evgeny.chess.consts.UI_WIDTH
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class SplashScreen(
    private val navigator: Navigator,
    bach: SpriteBatch,
    assetManager: AssetManager
) : BaseScreen() {

    private val viewPort = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val stage = Stage(viewPort, bach)
    private val uiSkin = assetManager[UI_ASSET_DESCRIPTOR]

    override fun show() {
        debug(TAG, "show()")
        val table = Table()
        table.setFillParent(true)
        val label = Label(TEXT_LOGO, uiSkin)
        label.setAlignment(Align.center)
        table.add(label)
        stage.addActor(table)
        label.addAction(
            Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(1f),
                Actions.fadeOut(1f),
            )
        )
    }

    override fun render(delta: Float) {
        clearScreen()
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        debug(TAG, "resize()")
        viewPort.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        debug(TAG, "dispose()")
        stage.dispose()
    }

    companion object {
        private const val TAG = "SplashScreen"
    }
}