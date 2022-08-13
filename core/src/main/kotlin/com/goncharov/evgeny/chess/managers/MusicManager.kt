package com.goncharov.evgeny.chess.managers

import com.badlogic.gdx.audio.Music

class MusicManager(
    private val gameMusic: Music
) {

    fun startMainMusic() {
        if (!gameMusic.isPlaying) {
            gameMusic.play()
            gameMusic.isLooping = true
        }
    }
}