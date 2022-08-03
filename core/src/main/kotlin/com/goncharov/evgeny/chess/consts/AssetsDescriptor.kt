package com.goncharov.evgeny.chess.consts

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin

val GAME_ASSET_DESCRIPTOR = AssetDescriptor(GAME_ASSET_PATH, TextureAtlas::class.java)
val UI_ASSET_DESCRIPTOR = AssetDescriptor(UI_ASSET_PATH, Skin::class.java)