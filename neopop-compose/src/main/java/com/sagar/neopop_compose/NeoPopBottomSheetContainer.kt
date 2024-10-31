package com.sagar.neopop_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * Creates a neo-pop 3D bottom sheet container with customizable colors, shapes, and shadows.
 *
 * @param modifier The modifier for this sheet composable.
 * @param containerTopShadowHeight The height of the top shadow (3d effect) on the container.
 * @param containerColors The brush used to color the container.
 * @param containerStrokeColors The brush used to color the container's stroke.
 * @param containerTopColors The brush used to color the top part of the container.
 * @param containerStrokeWidth The width of the container's stroke.
 * @param isDragHandleVisible Whether the drag handle is visible.
 * @param dragHandleSize The size of the drag handle.
 * @param dragHandleTopPadding The top padding of the drag handle.
 * @param dragHandleColors The brush used to color the drag handle.
 * @param dragHandleShadowColors The brush used to color the drag handle's shadow.
 * @param dragHandleStrokeColors The brush used to color the drag handle's stroke.
 * @param dragHandleTopShadowHeight The height of the top shadow (3d effect) on the drag handle.
 * @param dragHandleStrokeWidth The width of the drag handle's stroke.
 * @param content The content of the bottom sheet container.
 */
@Composable
fun NeoPopBottomSheetContainer(
    containerColors: Brush,
    containerStrokeColors: Brush,
    containerTopColors: Brush,
    dragHandleColors: Brush,
    dragHandleShadowColors: Brush,
    dragHandleStrokeColors: Brush,
    modifier: Modifier = Modifier,
    containerTopShadowHeight: Dp = 5.dp,
    containerStrokeWidth: Dp = 0.dp,
    isDragHandleVisible: Boolean = true,
    dragHandleSize: DpSize = DpSize(60.dp, 10.dp),
    dragHandleTopPadding: Dp = 20.dp,
    dragHandleTopShadowHeight: Dp = 5.dp,
    dragHandleStrokeWidth: Dp = 0.2.dp,
    content: @Composable BoxScope.() -> Unit
) {
    var containerHeightAsPx by remember {
        mutableFloatStateOf(0f)
    }
    var containerWidthAsPx by remember {
        mutableFloatStateOf(0f)
    }

    Box(
        modifier = modifier
            .padding(top = containerTopShadowHeight)
            .onGloballyPositioned { coordinates ->
                // Set column height using the LayoutCoordinates
                containerHeightAsPx = coordinates.size.height.toFloat()
                containerWidthAsPx = coordinates.size.width.toFloat()
            }
            .drawWithCache {
                onDrawBehind {
                    val sideHeightAsPx = containerTopShadowHeight.toPx()

                    // Container TopView
                    drawRect(
                        brush = containerColors,
                        topLeft = Offset(x = 0f, y = 0f),
                        size = Size(width = containerWidthAsPx, height = containerHeightAsPx),
                    )

                    // TopView of the container (3D effect)
                    Path().apply {
                        moveTo(x = 0f, y = 0f)
                        lineTo(
                            x = sideHeightAsPx,
                            y = -sideHeightAsPx
                        )
                        lineTo(
                            x = containerWidthAsPx - sideHeightAsPx,
                            y = -sideHeightAsPx
                        )
                        lineTo(
                            x = containerWidthAsPx,
                            y = 0f
                        )
                        close()
                        drawPath(
                            path = this,
                            brush = containerTopColors
                        )
                    }

                    // Stroke in container TopView
                    drawLine(
                        brush = containerStrokeColors,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = containerWidthAsPx, y = 0f),
                        strokeWidth = containerStrokeWidth.toPx()
                    )
                    drawLine(
                        brush = containerStrokeColors,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = containerHeightAsPx),
                        strokeWidth = containerStrokeWidth.toPx()
                    )
                    drawLine(
                        brush = containerStrokeColors,
                        start = Offset(x = containerWidthAsPx, y = 0f),
                        end = Offset(x = containerWidthAsPx, y = containerHeightAsPx),
                        strokeWidth = containerStrokeWidth.toPx()
                    )

                }
            }
    ) {
        if (isDragHandleVisible) {
            Column {
                //Drag Handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = dragHandleTopPadding)
                        .size(dragHandleSize)
                        .drawWithCache {
                            onDrawBehind {
                                val sideHeightAsPx = dragHandleTopShadowHeight.toPx()
                                // Drag Handle TopView
                                drawRect(
                                    brush = dragHandleColors,
                                    topLeft = Offset(x = 0f, y = 0f),
                                    size = Size(
                                        width = dragHandleSize.width.toPx(),
                                        height = dragHandleSize.height.toPx()
                                    ),
                                )

                                // TopView of the Drag Handle (3D effect)
                                Path().apply {
                                    moveTo(x = 0f, y = 0f)
                                    lineTo(
                                        x = sideHeightAsPx,
                                        y = -sideHeightAsPx
                                    )
                                    lineTo(
                                        x = dragHandleSize.width.toPx() - sideHeightAsPx,
                                        y = -sideHeightAsPx
                                    )
                                    lineTo(
                                        x = dragHandleSize.width.toPx(),
                                        y = 0f
                                    )
                                    close()
                                    drawPath(
                                        path = this,
                                        brush = dragHandleShadowColors
                                    )
                                }

                                // Drag Handle Stroke
                                drawRect(
                                    brush = dragHandleStrokeColors,
                                    topLeft = Offset(x = 0f, y = 0f),
                                    size = Size(
                                        width = dragHandleSize.width.toPx(),
                                        height = dragHandleSize.height.toPx()
                                    ),
                                    style = Stroke(width = dragHandleStrokeWidth.toPx())
                                )
                            }
                        }
                )
                Box {
                    content()
                }
            }
        } else {
            content()
        }
    }
}

/**
 * Creates a neo-pop 3D bottom sheet container with customizable colors, shapes, and shadows.
 *
 * @param modifier The modifier for this sheet composable.
 * @param containerTopShadowHeight The height of the top shadow (3d effect) on the container.
 * @param containerColor The color used to color the container.
 * @param containerStrokeColor The color used to color the container's stroke.
 * @param containerTopColor The color used to color the top part of the container.
 * @param containerStrokeWidth The width of the container's stroke.
 * @param isDragHandleVisible Whether the drag handle is visible.
 * @param dragHandleSize The size of the drag handle.
 * @param dragHandleTopPadding The top padding of the drag handle.
 * @param dragHandleColor The color used to color the drag handle.
 * @param dragHandleShadowColor The color used to color the drag handle's shadow.
 * @param dragHandleStrokeColor The color used to color the drag handle's stroke.
 * @param dragHandleTopShadowHeight The height of the top shadow (3d effect) on the drag handle.
 * @param dragHandleStrokeWidth The width of the drag handle's stroke.
 * @param content The content of the bottom sheet container.
 */
@Composable
fun NeoPopBottomSheetContainer(
    containerColor: Color,
    containerStrokeColor: Color,
    containerTopColor: Color,
    dragHandleColor: Color,
    dragHandleShadowColor: Color,
    dragHandleStrokeColor: Color,
    modifier: Modifier = Modifier,
    containerTopShadowHeight: Dp = 5.dp,
    containerStrokeWidth: Dp = 0.dp,
    isDragHandleVisible: Boolean = true,
    dragHandleSize: DpSize = DpSize(60.dp, 10.dp),
    dragHandleTopPadding: Dp = 20.dp,
    dragHandleTopShadowHeight: Dp = 5.dp,
    dragHandleStrokeWidth: Dp = 0.2.dp,
    content: @Composable BoxScope.() -> Unit
) {
    var containerHeightAsPx by remember {
        mutableFloatStateOf(0f)
    }
    var containerWidthAsPx by remember {
        mutableFloatStateOf(0f)
    }

    Box(
        modifier = modifier
            .padding(top = containerTopShadowHeight)
            .onGloballyPositioned { coordinates ->
                // Set column height using the LayoutCoordinates
                containerHeightAsPx = coordinates.size.height.toFloat()
                containerWidthAsPx = coordinates.size.width.toFloat()
            }
            .drawWithCache {
                onDrawBehind {
                    val sideHeightAsPx = containerTopShadowHeight.toPx()

                    // Container TopView
                    drawRect(
                        color = containerColor,
                        topLeft = Offset(x = 0f, y = 0f),
                        size = Size(width = containerWidthAsPx, height = containerHeightAsPx),
                    )

                    // TopView of the container (3D effect)
                    Path().apply {
                        moveTo(x = 0f, y = 0f)
                        lineTo(
                            x = sideHeightAsPx,
                            y = -sideHeightAsPx
                        )
                        lineTo(
                            x = containerWidthAsPx - sideHeightAsPx,
                            y = -sideHeightAsPx
                        )
                        lineTo(
                            x = containerWidthAsPx,
                            y = 0f
                        )
                        close()
                        drawPath(
                            path = this,
                            color = containerTopColor
                        )
                    }

                    // Stroke in container TopView
                    drawLine(
                        color = containerStrokeColor,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = containerWidthAsPx, y = 0f),
                        strokeWidth = containerStrokeWidth.toPx()
                    )
                    drawLine(
                        color = containerStrokeColor,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = containerHeightAsPx),
                        strokeWidth = containerStrokeWidth.toPx()
                    )
                    drawLine(
                        color = containerStrokeColor,
                        start = Offset(x = containerWidthAsPx, y = 0f),
                        end = Offset(x = containerWidthAsPx, y = containerHeightAsPx),
                        strokeWidth = containerStrokeWidth.toPx()
                    )

                }
            }
    ) {
        if (isDragHandleVisible) {
            Column {
                //Drag Handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = dragHandleTopPadding)
                        .size(dragHandleSize)
                        .drawWithCache {
                            onDrawBehind {
                                val sideHeightAsPx = dragHandleTopShadowHeight.toPx()
                                // Drag Handle TopView
                                drawRect(
                                    color = dragHandleColor,
                                    topLeft = Offset(x = 0f, y = 0f),
                                    size = Size(
                                        width = dragHandleSize.width.toPx(),
                                        height = dragHandleSize.height.toPx()
                                    ),
                                )

                                // TopView of the Drag Handle (3D effect)
                                Path().apply {
                                    moveTo(x = 0f, y = 0f)
                                    lineTo(
                                        x = sideHeightAsPx,
                                        y = -sideHeightAsPx
                                    )
                                    lineTo(
                                        x = dragHandleSize.width.toPx() - sideHeightAsPx,
                                        y = -sideHeightAsPx
                                    )
                                    lineTo(
                                        x = dragHandleSize.width.toPx(),
                                        y = 0f
                                    )
                                    close()
                                    drawPath(
                                        path = this,
                                        color = dragHandleShadowColor
                                    )
                                }

                                // Drag Handle Stroke
                                drawRect(
                                    color = dragHandleStrokeColor,
                                    topLeft = Offset(x = 0f, y = 0f),
                                    size = Size(
                                        width = dragHandleSize.width.toPx(),
                                        height = dragHandleSize.height.toPx()
                                    ),
                                    style = Stroke(width = dragHandleStrokeWidth.toPx())
                                )
                            }
                        }
                )
                Box {
                    content()
                }
            }
        } else {
            content()
        }
    }
}