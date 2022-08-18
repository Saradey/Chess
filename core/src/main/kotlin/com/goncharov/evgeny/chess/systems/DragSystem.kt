package com.goncharov.evgeny.chess.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.goncharov.evgeny.chess.components.mappers.dragged
import com.goncharov.evgeny.chess.components.mappers.sprites
import com.goncharov.evgeny.chess.consts.draggedFamily
import com.goncharov.evgeny.chess.consts.gameFamily

class DragSystem : IteratingSystem(draggedFamily) {

    private val gameEntity by lazy {
        engine.getEntitiesFor(gameFamily).first()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (dragged[gameEntity].isDragged) {
            sprites[entity].sprite.setCenter(
                dragged[gameEntity].positionDragged.x,
                dragged[gameEntity].positionDragged.y
            )
        }
    }
}