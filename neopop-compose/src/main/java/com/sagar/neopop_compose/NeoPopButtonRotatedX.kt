package com.sagar.neopop_compose

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Creates a neo-pop button that is rotated from the x axis with customizable colors, shapes, and animations.

 * @param modifier The modifier for this composable.
 * @param buttonDropHeight The height of the button's drop 3D effect.
 * @param buttonColor The background color of the button.
 * @param buttonStrokeColor The color of the button's stroke.
 * @param buttonDropColor The color of the bottom part of the button.
 * @param buttonStrokeWidth The width of the button's stroke.
 * @param buttonRotationValue The rotation angle of the button(should be less than 30).
 * @param enabled Whether the button is enabled or disabled.
 * @param animationSpec The animation specification for the button's click effect.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param content The content of the button.
 */
@Composable
fun NeoPopButtonRotatedX(
    buttonColor: Color,
    buttonStrokeColor: Color,
    buttonDropColor: Color,
    modifier: Modifier = Modifier,
    buttonDropHeight: Dp = 5.dp,
    buttonStrokeWidth: Dp = 0.3.dp,
    buttonRotationValue: Dp = 10.dp,
    enabled: Boolean = true,
    animationSpec: AnimationSpec<Dp> = spring(visibilityThreshold = Dp.VisibilityThreshold),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val density = LocalDensity.current

    val buttonAnimatedOffset by animateDpAsState(
        targetValue = if (isButtonPressed) buttonDropHeight else 0.dp,
        label = "",
        animationSpec = animationSpec
    )

    var buttonHeightAsPx by remember {
        mutableFloatStateOf(0f)
    }
    var buttonWidthAsPx by remember {
        mutableFloatStateOf(0f)
    }

    val bottomWidthSubtractionEnd by animateDpAsState(
        targetValue = if (isButtonPressed) 0.dp else buttonDropHeight,
        label = "",
        animationSpec = animationSpec
    )

    val bottomWidthAdditionStart by animateDpAsState(
        targetValue = if (isButtonPressed) 0.dp else buttonDropHeight,
        label = "",
        animationSpec = animationSpec
    )

    val buttonAnimatedDropHeight = remember(buttonAnimatedOffset) {
        with(density) {
            (buttonDropHeight - buttonAnimatedOffset).toPx()
        }
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) {
                onClick()
            }
            .onGloballyPositioned { coordinates ->
                // Set column height using the LayoutCoordinates
                buttonHeightAsPx = coordinates.size.height.toFloat()
                buttonWidthAsPx = coordinates.size.width.toFloat()
            }
            .drawWithCache {
                onDrawBehind {
                    val offsetAsPx = buttonAnimatedOffset.toPx()

                    // Button TopView
                    Path().apply {
                        moveTo(0f, offsetAsPx)
                        lineTo(0 - buttonRotationValue.toPx(), offsetAsPx + buttonHeightAsPx)
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx(),
                            offsetAsPx + buttonHeightAsPx
                        )
                        lineTo(buttonWidthAsPx, offsetAsPx)
                        lineTo(0f, offsetAsPx)
                        drawPath(
                            path = this,
                            color = buttonColor
                        )
                    }

                    // Button TopView Border
                    Path().apply {
                        moveTo(0f, offsetAsPx)
                        lineTo(0 - buttonRotationValue.toPx(), offsetAsPx + buttonHeightAsPx)
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx(),
                            offsetAsPx + buttonHeightAsPx
                        )
                        lineTo(buttonWidthAsPx, offsetAsPx)
                        lineTo(0f, offsetAsPx)
                        drawPath(
                            path = this,
                            color = buttonStrokeColor,
                            style = Stroke(buttonStrokeWidth.toPx())
                        )
                    }

                    // BottomView of the Button (3D effect)
                    Path().apply {
                        moveTo(0 - buttonRotationValue.toPx(), offsetAsPx + buttonHeightAsPx)
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx(),
                            offsetAsPx + buttonHeightAsPx
                        )
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx() - bottomWidthSubtractionEnd.toPx(),
                            offsetAsPx + buttonHeightAsPx + buttonAnimatedDropHeight
                        )
                        lineTo(
                            -buttonRotationValue.toPx() + bottomWidthAdditionStart.toPx(),
                            offsetAsPx + buttonHeightAsPx + buttonAnimatedDropHeight
                        )
                        drawPath(
                            path = this,
                            color = buttonDropColor
                        )
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    translationY = buttonAnimatedOffset.toPx()
                    rotationX = buttonRotationValue.toPx()
                }
        ) {
            content()
        }
    }
}

/**
 * Creates a neo-pop button that is rotated from the x axis with customizable colors, shapes, and animations.

 * @param modifier The modifier for this composable.
 * @param buttonDropHeight The height of the button's drop 3D effect.
 * @param buttonColors The background colors of the button.
 * @param buttonStrokeColors The colors of the button's stroke.
 * @param buttonDropColors The colors of the bottom part of the button.
 * @param buttonStrokeWidth The width of the button's stroke.
 * @param buttonRotationValue The rotation angle of the button(should be less than 30).
 * @param enabled Whether the button is enabled or disabled.
 * @param animationSpec The animation specification for the button's click effect.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param content The content of the button.
 */
@Composable
fun NeoPopButtonRotatedX(
    buttonColors: Brush,
    buttonStrokeColors: Brush,
    buttonDropColors: Brush,
    modifier: Modifier = Modifier,
    buttonDropHeight: Dp = 5.dp,
    buttonStrokeWidth: Dp = 0.3.dp,
    buttonRotationValue: Dp = 10.dp,
    enabled: Boolean = true,
    animationSpec: AnimationSpec<Dp> = spring(visibilityThreshold = Dp.VisibilityThreshold),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val density = LocalDensity.current

    val buttonAnimatedOffset by animateDpAsState(
        targetValue = if (isButtonPressed) buttonDropHeight else 0.dp,
        label = "",
        animationSpec = animationSpec
    )

    var buttonHeightAsPx by remember {
        mutableFloatStateOf(0f)
    }
    var buttonWidthAsPx by remember {
        mutableFloatStateOf(0f)
    }

    val bottomWidthSubtractionEnd by animateDpAsState(
        targetValue = if (isButtonPressed) 0.dp else buttonDropHeight,
        label = "",
        animationSpec = animationSpec
    )

    val bottomWidthAdditionStart by animateDpAsState(
        targetValue = if (isButtonPressed) 0.dp else buttonDropHeight,
        label = "",
        animationSpec = animationSpec
    )

    val buttonAnimatedDropHeight = remember(buttonAnimatedOffset) {
        with(density) {
            (buttonDropHeight - buttonAnimatedOffset).toPx()
        }
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) {
                onClick()
            }
            .onGloballyPositioned { coordinates ->
                // Set column height using the LayoutCoordinates
                buttonHeightAsPx = coordinates.size.height.toFloat()
                buttonWidthAsPx = coordinates.size.width.toFloat()
            }
            .drawWithCache {
                onDrawBehind {
                    val offsetAsPx = buttonAnimatedOffset.toPx()

                    // Button TopView
                    Path().apply {
                        moveTo(0f, offsetAsPx)
                        lineTo(0 - buttonRotationValue.toPx(), offsetAsPx + buttonHeightAsPx)
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx(),
                            offsetAsPx + buttonHeightAsPx
                        )
                        lineTo(buttonWidthAsPx, offsetAsPx)
                        lineTo(0f, offsetAsPx)
                        drawPath(
                            path = this,
                            brush = buttonColors
                        )
                    }

                    // Button TopView Border
                    Path().apply {
                        moveTo(0f, offsetAsPx)
                        lineTo(0 - buttonRotationValue.toPx(), offsetAsPx + buttonHeightAsPx)
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx(),
                            offsetAsPx + buttonHeightAsPx
                        )
                        lineTo(buttonWidthAsPx, offsetAsPx)
                        lineTo(0f, offsetAsPx)
                        drawPath(
                            path = this,
                            brush = buttonStrokeColors,
                            style = Stroke(buttonStrokeWidth.toPx())
                        )
                    }

                    // BottomView of the Button (3D effect)
                    Path().apply {
                        moveTo(0 - buttonRotationValue.toPx(), offsetAsPx + buttonHeightAsPx)
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx(),
                            offsetAsPx + buttonHeightAsPx
                        )
                        lineTo(
                            buttonWidthAsPx + buttonRotationValue.toPx() - bottomWidthSubtractionEnd.toPx(),
                            offsetAsPx + buttonHeightAsPx + buttonAnimatedDropHeight
                        )
                        lineTo(
                            -buttonRotationValue.toPx() + bottomWidthAdditionStart.toPx(),
                            offsetAsPx + buttonHeightAsPx + buttonAnimatedDropHeight
                        )
                        drawPath(
                            path = this,
                            brush = buttonDropColors
                        )
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    translationY = buttonAnimatedOffset.toPx()
                    rotationX = buttonRotationValue.toPx()
                }
        ) {
            content()
        }
    }
}