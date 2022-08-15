package com.goncharov.evgeny.chess.ui.game

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.Viewport
import com.goncharov.evgeny.chess.consts.CLICK_BUTTON_SOUND_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_HEIGHT
import com.goncharov.evgeny.chess.consts.UI_WIDTH
import com.goncharov.evgeny.chess.extensions.addListenerKtx
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.screens.game.GameScreen

class GameStageImpl(
    batch: SpriteBatch,
    viewport: Viewport,
    resourceManager: ResourceManager,
    private val gameScreen: GameScreen,
) : Stage(viewport, batch), GameStage {

    private val uiSkin: Skin = resourceManager[UI_ASSET_DESCRIPTOR]
    private val soundClick: Sound = resourceManager[CLICK_BUTTON_SOUND_DESCRIPTOR]
    private var labelMessage: Label? = null

    init {
        initUi()
    }

    override fun showMessage(message: String) {
        labelMessage?.setText(message)
        labelMessage?.addAction(
            Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(1f),
                Actions.fadeOut(1f)
            )
        )
        labelMessage?.addAction(
            Actions.sequence(
                Actions.moveTo(
                    X_MOVE_START_POSITION,
                    Y_MOVE_START_POSITION
                ),
                Actions.moveTo(
                    X_MOVE_START_POSITION,
                    Y_MOVE_POSITION,
                    2f
                )
            )
        )
    }

    override fun showEndGame(messageEndGame: String) {
        labelMessage?.setText(messageEndGame)
        labelMessage?.addAction(
            Actions.sequence(
                Actions.alpha(0f),
                Actions.fadeIn(1f)
            )
        )
    }

    private fun initUi() {
        val table = Table()
        table.setFillParent(true)
        val button = Button(uiSkin)
        button.addListenerKtx(::clickButtonBack)
        table.add(button)
            .width(button.width * 1.5f)
            .height(button.height * 1.5f)
            .top().padTop(12f).padLeft(12f)
            .prefHeight
        labelMessage = Label("", uiSkin)
        table.add(labelMessage).expand().padLeft(-54f)
        addActor(table)
    }

    private fun clickButtonBack() {
        soundClick.play()
        gameScreen.goToTheMainMenu()
    }

    companion object {
        private const val X_MOVE_START_POSITION = UI_WIDTH / 2f - 109f
        private const val Y_MOVE_START_POSITION = UI_HEIGHT / 2f - 10f
        private const val Y_MOVE_POSITION = UI_HEIGHT / 2f + 10f
    }
}