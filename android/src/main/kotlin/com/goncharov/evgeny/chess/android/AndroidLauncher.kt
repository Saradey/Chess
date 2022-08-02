package com.goncharov.evgeny.chess.android

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.goncharov.evgeny.chess.App

/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(App(), AndroidApplicationConfiguration().apply {
            // Configure your application here.
        })
    }
}
