package com.goncharov.evgeny.chess.utils

import com.badlogic.ashley.core.Entity
import com.goncharov.evgeny.chess.components.mappers.layers

class LayerComparator : Comparator<Entity> {
    override fun compare(entityFirst: Entity, entitySecond: Entity): Int {
        return if (layers[entityFirst].layer < layers[entitySecond].layer) {
            -1
        } else {
            1
        }
    }
}