package com.blueberry.kmp_apod.progressindicator

import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
* Source: https://github.com/EhsanMsz/MszProgressIndicator/
* */
@Composable
fun SemiCircleSpinProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    circleDiameter: Dp = DefaultCircleDiameter,
    sweepAngle: Float = DefaultSweepAngle
) {
    val transition = rememberInfiniteTransition()

    //Fractional start angle
    val startAngle by transition.fraction(animationDuration)

    ProgressIndicator(modifier, circleDiameter) {
        drawIndeterminateSemiCircleIndicator(
            diameter = circleDiameter.toPx(),
            startAngle = startAngle * 360f,
            sweepAngle = sweepAngle,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateSemiCircleIndicator(
    diameter: Float,
    startAngle: Float,
    sweepAngle: Float,
    color: Color
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        useCenter = false,
        sweepAngle = sweepAngle,
        size = Size(width = diameter, height = diameter),
    )
}

@Composable
internal fun InfiniteTransition.fraction(
    durationMillis: Int,
    delayMillis: Int = 0,
    easing: Easing = LinearEasing
): State<Float> {
    val duration = durationMillis + delayMillis

    return animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = duration
                0f at delayMillis / 2 with easing
                1f at durationMillis + (delayMillis / 2)
                1f at duration
            }
        )
    )
}

private const val DefaultAnimationDuration = 700

private val DefaultCircleDiameter = 40.dp
private const val DefaultSweepAngle = 130f