package com.goncharov.evgeny.chess.screens

import com.goncharov.evgeny.chess.base.BaseScreen
import com.goncharov.evgeny.chess.utils.debug

class MainMenuScreen : BaseScreen() {

    override fun show() {
        debug(TAG, "show()")
    }

    override fun render(delta: Float) {

    }

    override fun resize(width: Int, height: Int) {
        debug(TAG, "resize()")
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        debug(TAG, "dispose()")
    }

    companion object {
        private const val TAG = "MainMenuScreen"
    }
}