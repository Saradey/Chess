package com.goncharov.evgeny.chess.controllers

import com.badlogic.gdx.scenes.scene2d.ui.Label

interface ChangeOfMovingController {

    fun initLabelMessage(label: Label)

    fun initMessageMoving()

    fun showMessageMoved()
}