package com.marsanix.ledgo.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.marsanix.pelajarankuapp.SideBarNavigationItem

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.coloredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
) = composed {
    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparent = color.copy(alpha = 0f).toArgb()
    this.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparent
            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
}

@Composable
fun getSelectedItem(id: String): SideBarNavigationItem? {
    return SideBarNavigationItem.entries.find { it.id.toString() == id }
}

@Composable
fun getNextItem(id: String): SideBarNavigationItem {
    val index = SideBarNavigationItem.entries.indexOfFirst { it.id.toString() == id }
    val nextIndex = index + 1
    val countMateriIndex = SideBarNavigationItem.entries.size - 2
    if (index != -1) {
        return if (index < countMateriIndex) {
            SideBarNavigationItem.entries[nextIndex]
        } else {
            SideBarNavigationItem.entries[index]
        }
    }
    return SideBarNavigationItem.entries[0]
}

