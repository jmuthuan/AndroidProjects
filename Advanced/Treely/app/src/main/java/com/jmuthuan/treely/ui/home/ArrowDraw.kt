package com.jmuthuan.treely.ui.home

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.jmuthuan.treely.utils.ArrowShow

fun getPath(size: DrawScope, arrowShow: ArrowShow): Path {
    val canvasWidth = size.size.width
    val canvasHeight = size.size.height

    val context = size.drawContext

    val pathLine = Path()

    when(arrowShow) {
        ArrowShow.UP -> {
            pathLine.moveTo(x = canvasWidth/2, y = canvasHeight)
            pathLine.lineTo(x = canvasWidth/2 + 8.dpToPx(context), y = canvasHeight + 8.dpToPx(context))
            pathLine.lineTo(x = canvasWidth/2 - 8.dpToPx(context), y = canvasHeight + 8.dpToPx(context))
            pathLine.close()
        }
        ArrowShow.DOWN -> {
            pathLine.moveTo(x = canvasWidth/2, y = canvasHeight + 32.dpToPx(context))
            pathLine.lineTo(x = canvasWidth/2 + 8.dpToPx(context), y = canvasHeight + 24.dpToPx(context))
            pathLine.lineTo(x = canvasWidth/2 - 8.dpToPx(context), y = canvasHeight + 24.dpToPx(context))
            pathLine.close()
        }
        ArrowShow.RIGHT_TOP -> {
            pathLine.moveTo(x = 0.0f, y = canvasHeight/2)
            pathLine.lineTo(x = (-10).dpToPx(context), y = canvasHeight/2 + 3.dpToPx(context))
            pathLine.lineTo(x = (2).dpToPx(context), y = canvasHeight/2 + 13.dpToPx(context))
            pathLine.close()
        }
        ArrowShow.RIGHT_DOWN -> {
            pathLine.moveTo(x = 0.0f, y = canvasHeight/2)
            pathLine.lineTo(x = 2.dpToPx(context), y = canvasHeight/2 - 13.dpToPx(context))
            pathLine.lineTo(x = (-10).dpToPx(context), y = canvasHeight/2 + (-2).dpToPx(context))
            pathLine.close()
        }
        else -> {
            pathLine.moveTo(x = 0.0f, y = canvasHeight/2)
            pathLine.lineTo(x = (-16).dpToPx(context), y = canvasHeight/2)
            pathLine.close()
        }

    }
    return pathLine
}

fun getLinePoints(size: DrawScope, arrowShow: ArrowShow): Array<Offset> {
    val canvasWidth = size.size.width
    val canvasHeight = size.size.height

    val context = size.drawContext

    val linePoints = Array(2){ Offset(x = 0.0f, y = 0.0f) }

    when(arrowShow) {
        ArrowShow.UP -> {
            linePoints[0] = Offset(x = canvasWidth/2, y = canvasHeight )
            linePoints[1] = Offset(x = canvasWidth/2, y = canvasHeight + 32.dpToPx(context))
        }
        ArrowShow.DOWN -> {
            linePoints[0] = Offset(x = canvasWidth/2, y = canvasHeight )
            linePoints[1] = Offset(x = canvasWidth/2, y = canvasHeight + 32.dpToPx(context))
        }
        ArrowShow.RIGHT_TOP -> {
            linePoints[0] = Offset(x = 0.0f, y = canvasHeight/2 )
            linePoints[1] = Offset(x = (-32).dpToPx(context), y = canvasHeight + 16.dpToPx(context))
        }
        ArrowShow.RIGHT_DOWN -> {
            linePoints[0] = Offset(x = 0.0f, y = canvasHeight/2 )
            linePoints[1] = Offset(x = (-32).dpToPx(context), y = (-16).dpToPx(context))
        }

        else -> {
            linePoints[0] = Offset(x = canvasWidth/2, y = canvasHeight )
            linePoints[1] = Offset(x = canvasWidth/2, y = canvasHeight + 32.dpToPx(context))
        }
    }
    return linePoints
}

fun getOffsetCircle(size: DrawScope, arrowShow: ArrowShow): Offset {
    val canvasWidth = size.size.width
    val canvasHeight = size.size.height

    val context = size.drawContext

    val offset = when(arrowShow) {
        ArrowShow.UP -> Offset(x = canvasWidth/2, y = canvasHeight + 32.dpToPx(context))
        ArrowShow.DOWN -> Offset(x = canvasWidth/2, y = canvasHeight)
        ArrowShow.RIGHT_TOP-> Offset(x = (-32).dpToPx(context), y = canvasHeight + 16.dpToPx(context))
        ArrowShow.RIGHT_DOWN -> Offset(x = (-32).dpToPx(context), y = (-16).dpToPx(context))

        else -> Offset(x = canvasWidth/2, y = canvasHeight)
    }
    return offset
}

fun Int.dpToPx(drawContext: DrawContext): Float = (this * drawContext.density.density)
