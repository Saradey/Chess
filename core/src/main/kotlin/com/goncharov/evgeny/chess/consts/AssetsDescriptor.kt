package com.goncharov.evgeny.chess.consts

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin

val GAME_ASSET_DESCRIPTOR = AssetDescriptor(GAME_ASSET_PATH, TextureAtlas::class.java)
val UI_ASSET_DESCRIPTOR = AssetDescriptor(UI_ASSET_PATH, Skin::class.java)
val CLICK_BUTTON_SOUND_DESCRIPTOR = AssetDescriptor(CLICK_BUTTON_SOUND_PATH, Sound::class.java)
val MAIN_MUSIC_DESCRIPTOR = AssetDescriptor(MAIN_MUSIC_PATH, Music::class.java)