package com.sagar.neopop_compose

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize

/**
 * Creates a 3D pressable button with a customizable animation effect. When pressed, the button animates with a rotation and scaling effect.
 *
 * @param modifier A modifier to apply to the button.
 * @param rotationFactor The degree of rotation to apply when the button is pressed, more the factor more the rotation.
 * @param shrinkFactor The shrink factor to apply when the button is pressed,shrink is downwards, so lesser the value more shrink it'll be. (Range: 0f to 1f).
 * @param animationSpec The animation specification for the press effect.
 * @param onClick A callback function that is invoked when the button is clicked successfully(Note: If Tap is cancelled then this callback won't be invoked).
 * @param content The content to be displayed within the button.
 */
@Composable
fun NeoPop3DPressableContent(
    modifier: Modifier = Modifier,
    rotationFactor: Int = 10,
    shrinkFactor: Float = 0.94f,
    animationSpec: AnimationSpec<Float> = spring(
        Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    ),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    var isPressed by remember {
        mutableStateOf(false)
    }

    val scaleAnimation by animateFloatAsState(
        targetValue = if (isPressed) shrinkFactor else 1f,
        animationSpec = animationSpec,
        label = "Scale Animation"
    )

    var rotationY by remember { mutableFloatStateOf(0f) }
    var rotationX by remember { mutableFloatStateOf(0f) }

    val rotationYAnimation by animateFloatAsState(
        targetValue = rotationY,
        animationSpec = animationSpec,
        label = "RotationY Animation"
    )

    val rotationXAnimation by animateFloatAsState(
        targetValue = rotationX,
        animationSpec = animationSpec,
        label = "RotationX Animation"
    )

    var boxSize by remember { mutableStateOf(Size.Zero) }

    Box(
        modifier = Modifier
            .onSizeChanged { boxSize = it.toSize() }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offSet ->
                        // Hit and Trial Calculation for finding the rotation quantity of both x and y (Can be improved in future)
                        val xPos = ((offSet.x / boxSize.width) * 2) - 1f
                        val yPos = ((offSet.y / boxSize.height) * -2) + 1f

                        //Trigger the animation
                        rotationY = xPos * rotationFactor
                        rotationX = yPos * rotationFactor * 2
                        isPressed = true

                        val isReleased = tryAwaitRelease()
                        if (isReleased) { // If the tap is released don't consider it a valid click event
                            onClick()
                        }

                        // Reset the animated value
                        isPressed = false
                        rotationY = 0f
                        rotationX = 0f
                    }
                )
            }
            .graphicsLayer {
                // Apply the animating values
                this.rotationX = rotationXAnimation
                this.rotationY = rotationYAnimation
                this.scaleX = scaleAnimation
                this.scaleY = scaleAnimation
            }
            .then(modifier)
    ) {
        content()
    }
}