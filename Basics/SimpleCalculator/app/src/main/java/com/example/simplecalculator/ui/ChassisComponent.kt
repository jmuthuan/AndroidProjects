package com.example.simplecalculator.ui

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Shader
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.ChassisBezelHighlight
import com.example.simplecalculator.ui.theme.ChassisBezelShadow
import com.example.simplecalculator.ui.theme.ChassisBodyColorDark
import com.example.simplecalculator.ui.theme.ChassisBodyColorLight
import com.example.simplecalculator.ui.theme.ChassisPanelColorDark
import com.example.simplecalculator.ui.theme.ChassisPanelColorLight
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.ui.theme.handjetFontFamily
import kotlin.random.Random

/**
 * Builds a small, deterministic grain/pebble noise bitmap once (seeded Random so it is
 * stable across recompositions/previews). This is a plain (non-composable) function so
 * it can be hoisted behind a single `remember` call and never regenerated on every
 * keypress-driven recomposition of the calculator screen.
 */
private fun buildNoiseBitmap(sizePx: Int = 64, seed: Long = 42L): Bitmap {
    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val random = Random(seed)
    for (y in 0 until sizePx) {
        for (x in 0 until sizePx) {
            val gray = random.nextInt(256)
            val alpha = random.nextInt(50)
            bitmap.setPixel(x, y, AndroidColor.argb(alpha, gray, gray, gray))
        }
    }
    return bitmap
}

/**
 * A tiled procedural grain brush for the "molded plastic" chassis texture.
 * Computed once via `remember` (no keys) so it survives recomposition without being
 * regenerated on every keypress — see performance constraint in the handoff.
 */
@Composable
private fun rememberNoiseBrush(): Brush {
    return remember {
        val bitmap = buildNoiseBitmap()
        ShaderBrush(BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT))
    }
}

/**
 * Applies the outer calculator body look: a vertical plastic-sheen gradient with a very
 * low-alpha procedural grain overlay drawn on top via `drawWithContent`, which is
 * guaranteed compatible with this project's Compose BOM (no reliance on a
 * `background(brush, alpha)` overload).
 */
@Composable
fun Modifier.skeuomorphicChassisBody(): Modifier {
    val noiseBrush = rememberNoiseBrush()
    return this
        .background(brush = Brush.verticalGradient(listOf(ChassisBodyColorLight, ChassisBodyColorDark)))
        .drawWithContent {
            drawContent()
            drawRect(brush = noiseBrush, alpha = 0.06f)
        }
}

/**
 * The inset "control panel" bezel that frames the display + button grid as a single
 * unit, distinguishable from the outer chassis body. Reuses the project's own
 * [Modifier.shadow] emboss primitive (light top-left + dark bottom-right) exactly as
 * ButtonComponent/InputDisplayComponent already do.
 */
@Composable
fun ChassisPanel(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(
                color = ChassisBezelShadow,
                offsetX = 6.dp,
                offsetY = 6.dp,
                blurRadius = 16.dp
            )
            .shadow(
                color = ChassisBezelHighlight,
                offsetX = (-6).dp,
                offsetY = (-6).dp,
                blurRadius = 16.dp
            )
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Brush.verticalGradient(listOf(ChassisPanelColorLight, ChassisPanelColorDark)))
            .padding(14.dp),
        content = content
    )
}

/**
 * Optional small brand/model plate, embossed like an engraved nameplate. Kept
 * single-line and wrap-content tall so it cannot meaningfully compete with
 * button/display space on small screens. Its text is sourced from strings.xml and is
 * deliberately not a digit/operator/"( )"/"AC" to avoid colliding with
 * SimpleCalculatorUiTest.kt's onNodeWithText lookups.
 */
@Composable
fun ChassisBrandPlate(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(
                color = ChassisBezelHighlight,
                offsetX = (-2).dp,
                offsetY = (-2).dp,
                blurRadius = 4.dp
            )
            .shadow(
                color = ChassisBezelShadow,
                offsetX = 2.dp,
                offsetY = 2.dp,
                blurRadius = 4.dp
            )
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = stringResource(id = R.string.chassis_brand_plate),
            fontFamily = handjetFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 1,
            color = ChassisPanelColorDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChassisComponentPreview() {
    SimpleCalculatorTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .skeuomorphicChassisBody()
                .padding(16.dp)
        ) {
            ChassisPanel(modifier = Modifier.fillMaxWidth()) {
                ChassisBrandPlate()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "panel content placeholder")
            }
        }
    }
}
