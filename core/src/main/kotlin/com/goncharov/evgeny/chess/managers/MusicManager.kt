package com.goncharov.evgeny.chess.managers

import com.badlogic.gdx.assets.AssetManager
import com.goncharov.evgeny.chess.consts.MAIN_MUSIC_DESCRIPTOR

class MusicManager(
    assetManager: AssetManager
) {
    private val mainMusic by lazy {
        assetManager[MAIN_MUSIC_DESCRIPTOR]
    }

    fun startMainMusic() {
        if (!mainMusic.isPlaying) {
            mainMusic.play()
            mainMusic.isLooping = true
        }
    }

    fun stopMainMusic() {
        if (mainMusic.isPlaying) {
            mainMusic.stop()
        }
    }
}