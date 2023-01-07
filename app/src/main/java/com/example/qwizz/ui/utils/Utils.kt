package com.example.qwizz.ui.utils

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@Composable
internal fun StatusBarInsetHandler(inset: Dp = 30.dp, content: @Composable () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = inset)
    ) {
        content()
    }
}


internal fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

val CustomDrawerShapeDiscussed = GenericShape { size: Size, _: LayoutDirection ->
    val curveWidth: Float = size.width / 5 // 1/5 of width
    val curveHeight: Float = size.height / 4 // 1/4 of height

    val answerX = (size.width - curveWidth) * sqrt(2.0).toFloat()

    moveTo(0f, 0f) // move to top middle
    lineTo(size.width - curveWidth, 0f) // top line
//    relativeQuadraticBezierTo(
//        dx1 = size.width - curveWidth,
//        dy1 = 0f,
//        dx2 = size.width,
//        dy2 = curveWidth
//    ) // top right curve
    quadraticBezierTo(
        x1 = size.width,
        y1 = 0f,
        x2 = size.width,
        y2 = curveWidth
    ) // top right curve
    lineTo(size.width, size.height - curveHeight) // right line
    quadraticBezierTo(
        x1 = size.width,
        y1 = size.height - curveHeight,
        x2 = answerX,
        y2 = size.height - curveHeight
    ) // bottom major curve
    lineTo(0f, size.height)
    lineTo(0f, 0f) // left line

    close()
}


class DrawerShape(private val sides: Int, private val rotation: Float = 0f, val cornerRadius: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                val radius = if (size.width > size.height) size.width / 2f else size.height / 2f
                val angle = 2.0 * Math.PI / sides
                val cx = size.width / 2f
                val cy = size.height / 2f
                val r = rotation * (Math.PI / 180)
                moveTo(
                    x = cx + (radius * cos(0.0 + r).toFloat()), // center X
                    y = cy + (radius * sin(0.0 + r).toFloat()) // center Y
                )
                for (i in 1 until sides) {
                    lineTo(
                        x = cx + (radius * cos(angle * i + r).toFloat()),
                        y = cy + (radius * sin(angle * i + r).toFloat())
                    )
                }
                close()
            }
        )
    }
}