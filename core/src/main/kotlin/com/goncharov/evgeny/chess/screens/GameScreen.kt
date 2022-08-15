package com.goncharov.evgeny.chess.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FillViewport
import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.consts.*
import com.goncharov.evgeny.chess.controllers.*
import com.goncharov.evgeny.chess.factory.ChessBoardFactory
import com.goncharov.evgeny.chess.factory.GameFactory
import com.goncharov.evgeny.chess.factory.PiecesFactory
import com.goncharov.evgeny.chess.managers.ResourceManager
import com.goncharov.evgeny.chess.managers.SavedSettingsManager
import com.goncharov.evgeny.chess.navigation.Navigator
import com.goncharov.evgeny.chess.systems.DragAndDropSystem
import com.goncharov.evgeny.chess.systems.RenderSystem
import com.goncharov.evgeny.chess.ui.game.GameStageImpl
import com.goncharov.evgeny.chess.utils.clearScreen
import com.goncharov.evgeny.chess.utils.debug

class GameScreen(
    private val batch: SpriteBatch,
    resourceManager: ResourceManager,
    savedSettingsManager: SavedSettingsManager,
    navigator: Navigator
) : BaseScreen() {

    private val viewport = FillViewport(WORLD_WIDTH, WORLD_HEIGHT)
    private val hudViewport = FillViewport(UI_WIDTH, UI_HEIGHT)
    private val gameStage = GameStageImpl(
        batch,
        hudViewport,
        resourceManager,
        navigator
    )
    private val engine = Engine()
    private val chessBoardFactory =
        ChessBoardFactory(
            engine,
            savedSettingsManager,
            resourceManager[UI_ASSET_DESCRIPTOR],
            resourceManager
        )
    private val piecesFactory = PiecesFactory(engine, resourceManager)
    private val gameFactory = GameFactory(savedSettingsManager, engine)
    private val changeOfMovingController: ChangeOfMovingController =
        ChangeOfMovingControllerImpl(gameStage, savedSettingsManager, engine)
    private val gameController: GameInteractor = GameInteractorImpl(engine)
    private val gameOverController: GameOverController = GameOverControllerImpl(
        engine,
        gameStage
    )

    override fun show() {
        debug(TAG, "show()")
        gameFactory.initialGame()
        Gdx.input.inputProcessor = gameStage
        chessBoardFactory.buildChessBoard()
        chessBoardFactory.addBackground()
        piecesFactory.buildWhitePiecesPlayer()
        piecesFactory.buildBlackPiecesPlayer()
        engine.addSystem(
            DragAndDropSystem(
                viewport,
                changeOfMovingController,
                gameController,
                gameOverController
            )
        )
        engine.addSystem(RenderSystem(viewport, batch))
        changeOfMovingController.initMessageMoving()
    }

    override fun render(delta: Float) {
        clearScreen()
        engine.update(delta)
        gameStage.act(delta)
        gameStage.draw()
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
        gameStage.dispose()
        Gdx.input.inputProcessor = null
    }

    companion object {
        private const val TAG = "GameScreen"
    }
}