package com.example.qwizz.ui.utils

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.sqrt

sealed class QShapes {

    object DrawerShapes: QShapes() {
        val shape = GenericShape { size: Size, _: LayoutDirection ->
            val curveRadius: Float = size.width / 5 // 1/5 of width
            val heightOffset: Float = size.height / 4 // 1/4 of height

            val hypotenuseLength = calculateDistanceBetweenPoints(0f, size.height, size.width, size.height - heightOffset + curveRadius) // bottom curve line length

            val answerX = (size.width - curveRadius) * sqrt(2.0).toFloat()

            moveTo(0f, 0f) // move to top middle
            lineTo(size.width - curveRadius, 0f) // top line
            quadraticBezierTo(
                x1 = size.width,
                y1 = 0f,
                x2 = size.width,
                y2 = curveRadius
            ) // top right curve
            lineTo(size.width, size.height - heightOffset) // right line
            quadraticBezierTo(
                x1 = size.width,
                y1 = size.height - heightOffset + curveRadius,
                x2 = size.width - curveRadius,
                y2 = size.height - heightOffset + curveRadius + 68f // static value
            ) // bottom major curve
            lineTo(0f, size.height) // line bottom
            lineTo(0f, 0f) // left line

            close()
        }
    }
}

internal fun calculateDistanceBetweenPoints(
    x1: Float,
    y1: Float,
    x2: Float,
    y2: Float
): Float {
    return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))
}