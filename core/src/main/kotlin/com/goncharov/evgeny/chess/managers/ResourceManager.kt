package com.goncharov.evgeny.chess.managers

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.goncharov.evgeny.chess.consts.CLICK_BUTTON_SOUND_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.GAME_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.MAIN_MUSIC_DESCRIPTOR
import com.goncharov.evgeny.chess.consts.UI_ASSET_DESCRIPTOR
import com.goncharov.evgeny.chess.utils.debug

class ResourceManager(
    private val assetManager: AssetManager
) {

    fun loadAllResources() {
        debug(TAG, "loadAllResources")
        assetManager.load(GAME_ASSET_DESCRIPTOR)
        assetManager.load(UI_ASSET_DESCRIPTOR)
        assetManager.load(CLICK_BUTTON_SOUND_DESCRIPTOR)
        assetManager.load(MAIN_MUSIC_DESCRIPTOR)
        assetManager.finishLoading()
    }

    fun disposeAllResources() {
        assetManager.dispose()
    }

    operator fun <T> get(assetDescriptor: AssetDescriptor<T>): T {
        debug(TAG, "getResources $assetDescriptor")
        return assetManager[assetDescriptor]
    }

    companion object {
        private const val TAG = "ResourceManager"
    }
}