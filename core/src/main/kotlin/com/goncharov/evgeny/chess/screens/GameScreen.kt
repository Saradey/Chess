package com.goncharov.evgeny.chess.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.controllers.*
import com.goncharov.evgeny.chess.extensions.addListenerKtx
import com.goncharov.evgeny.chess.factory.ChessBoardFactory
import com.goncharov.evgeny.chess.factory.GameFactory
import com.goncharov.evgeny.chess.factory.PiecesFactory
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.navigation.NavigationKey
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.systems.DragAndDropSystem
import com.goncharov.evgeny.chess.systems.RenderSystem
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class GameScreen(
    private val batch: SpriteBatch,
    assetManager: AssetManager,
    savedSettingsManager: SavedSettingsManager,
    private val navigator: Navigator
) : BaseScreen() {

    private val viewport = FillViewport(WORLD_WIDTH, WORLD_HEIGHT)
    private val hudViewport = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val engine = Engine()
    private val stage = Stage(hudViewport, batch)
    private val uiSkin = assetManager[UI_ASSET_DESCRIPTOR]
    private val chessBoardFactory =
        ChessBoardFactory(engine, savedSettingsManager, uiSkin, assetManager)
    private val piecesFactory = PiecesFactory(engine, assetManager)
    private val soundClickButton = assetManager[CLICK_BUTTON_SOUND_DESCRIPTOR]
    private val gameFactory = GameFactory(savedSettingsManager, engine)
    private val shapeRenderer = ShapeRenderer()
    private val changeOfMovingController: ChangeOfMovingController =
        ChangeOfMovingControllerImpl(savedSettingsManager, engine)
    private val gameController: GameInteractor = GameInteractorImpl(engine)
    private val gameOverController: GameOverController = GameOverControllerImpl(engine)

    override fun show() {
        shapeRenderer.color = Color.RED
        debug(TAG, "show()")
        gameFactory.initialGame()
        initUi()
        chessBoardFactory.buildChessBoard()
        chessBoardFactory.addBackground()
        piecesFactory.buildWhitePiecesPlayer()
        piecesFactory.buildBlackPiecesPlayer()
        engine.addSystem(RenderSystem(viewport, batch, shapeRenderer))
        engine.addSystem(
            DragAndDropSystem(
                viewport,
                changeOfMovingController,
                gameController,
                gameOverController
            )
        )
    }

    override fun render(delta: Float) {
        clearScreen()
        engine.update(delta)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        debug(TAG, "resize()")
        viewport.update(width, height, true)
        hudViewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        debug(TAG, "dispose()")
        stage.dispose()
        Gdx.input.inputProcessor = null
        shapeRenderer.dispose()
    }

    private fun initUi() {
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.setFillParent(true)
        val button = Button(uiSkin)
        button.addListenerKtx(::clickButtonBack)
        table.add(button)
            .width(button.width * 1.5f)
            .height(button.height * 1.5f)
            .top().padTop(12f).padLeft(12f)
            .prefHeight
        val label = Label("", uiSkin)
        table.add(label).expand().padLeft(-54f)
        stage.addActor(table)
        changeOfMovingController.initLabelMessage(label)
        changeOfMovingController.initMessageMoving()
        gameOverController.initLabelMessage(label)
    }

    private fun clickButtonBack() {
        soundClickButton.play()
        navigator.navigation(NavigationKey.MainMenuScreenKey)
    }

    companion object {
        private const val TAG = "GameScreen"
    }
}