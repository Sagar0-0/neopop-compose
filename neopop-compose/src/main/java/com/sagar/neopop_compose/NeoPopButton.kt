package com.sagar.neopop_compose

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

/**
 * Creates a NeoPop style button with a subtle drop shadow effect.
 *
 * @param modifier A modifier to apply to the button.
 * @param buttonDropHeight The height of the drop shadow effect in Dp.
 * @param buttonColor The primary color of the button.
 * @param buttonStrokeColor The color of the button's stroke (outline).
 * @param buttonRightColor The color used for the right shadow of the button.
 * @param buttonBottomColor The color used for the left shadow of the button.
 * @param buttonStrokeWidth The width of the button's stroke in Dp.
 * @param enabled Whether the button is enabled or disabled.
 * @param animationSpec The animation specification for the button's press effect.
 * @param onClick A callback function that is invoked when the button is clicked.
 * @param content The content to be displayed within the button.
 */
@Composable
fun NeoPopButton(
    modifier: Modifier = Modifier,
    buttonDropHeight: Dp = 3.dp,
    buttonColor: Color = Color.Black,
    buttonStrokeColor: Color = Color.Green,
    buttonRightColor: Color = Color(0xFF6EB11C),
    buttonBottomColor: Color = Color(0xFF3D6011),
    buttonStrokeWidth: Dp = 0.3.dp,
    enabled: Boolean = true,
    animationSpec: AnimationSpec<Dp> = spring(visibilityThreshold = Dp.VisibilityThreshold),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()

    val buttonAnimatedOffset by animateDpAsState(
        targetValue = if (isButtonPressed) buttonDropHeight else 0.dp,
        label = "",
        animationSpec = animationSpec
    )

    val buttonAnimatedDropHeight = remember(buttonAnimatedOffset) {
        buttonDropHeight - buttonAnimatedOffset
    }

    var buttonHeightAsPx by remember {
        mutableFloatStateOf(0f)
    }
    var buttonWidthAsPx by remember {
        mutableFloatStateOf(0f)
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
                    drawRect(
                        color = buttonColor,
                        topLeft = Offset(x = offsetAsPx, y = offsetAsPx),
                        size = Size(width = buttonWidthAsPx, height = buttonHeightAsPx),
                    )
                    // Stroke in Button TopView
                    drawRect(
                        color = buttonStrokeColor,
                        topLeft = Offset(x = offsetAsPx, y = offsetAsPx),
                        size = Size(width = buttonWidthAsPx, height = buttonHeightAsPx),
                        style = Stroke(buttonStrokeWidth.toPx())
                    )

                    // RightView of the Button (3D effect)
                    val rightPath = Path()
                    rightPath.moveTo(x = offsetAsPx + buttonWidthAsPx, y = offsetAsPx)
                    rightPath.lineTo(
                        x = offsetAsPx + buttonWidthAsPx + buttonAnimatedDropHeight.toPx(),
                        y = offsetAsPx + buttonAnimatedDropHeight.toPx()
                    )
                    rightPath.lineTo(
                        x = buttonWidthAsPx + offsetAsPx + buttonAnimatedDropHeight.toPx(),
                        y = buttonHeightAsPx + offsetAsPx + buttonAnimatedDropHeight.toPx()
                    )
                    rightPath.lineTo(
                        x = buttonWidthAsPx + offsetAsPx,
                        y = buttonHeightAsPx + offsetAsPx
                    )
                    rightPath.close()
                    drawPath(
                        path = rightPath, color = buttonRightColor
                    )

                    // BottomView of the Button (3D effect)
                    val bottomPath = Path()
                    bottomPath.moveTo(x = offsetAsPx, y = offsetAsPx + buttonHeightAsPx)
                    bottomPath.lineTo(
                        x = offsetAsPx + buttonAnimatedDropHeight.toPx(),
                        y = offsetAsPx + buttonHeightAsPx + buttonAnimatedDropHeight.toPx()
                    )
                    bottomPath.lineTo(
                        x = buttonWidthAsPx + offsetAsPx + buttonAnimatedDropHeight.toPx(),
                        y = buttonHeightAsPx + offsetAsPx + buttonAnimatedDropHeight.toPx()
                    )
                    bottomPath.lineTo(
                        x = buttonWidthAsPx + offsetAsPx,
                        y = buttonHeightAsPx + offsetAsPx
                    )
                    bottomPath.close()
                    drawPath(
                        path = bottomPath,
                        color = buttonBottomColor
                    )
                }
            }
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = buttonAnimatedOffset
                            .toPx()
                            .toInt(),
                        y = buttonAnimatedOffset
                            .toPx()
                            .toInt()
                    )
                }
        ) {
            content()
        }
    }
}