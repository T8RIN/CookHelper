package ru.tech.cookhelper.presentation.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.lang.Math.toRadians
import kotlin.math.*


private const val TWO_PI = 2 * PI

class RoundedStarShape(
    private val sides: Int,
    private val curve: Float = 0.09f,
    rotation: Float = 0f,
    iterations: Int = 360,
) : Shape {

    private val steps = (TWO_PI) / iterations.coerceAtMost(360)
    private val rotationDegree = (PI / 180) * rotation

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(
        Path().apply {
            val r = size.height.coerceAtMost(size.width) * 0.4 * (1f - curve)

            val xCenter = size.width * .5f
            val yCenter = size.height * .5f

            moveTo(xCenter, yCenter)

            var t = 0.0

            while (t <= TWO_PI) {
                val x = r * (cos(t - rotationDegree) * (1 + curve * cos(sides * t)))
                val y = r * (sin(t - rotationDegree) * (1 + curve * cos(sides * t)))
                lineTo((x + xCenter).toFloat(), (y + yCenter).toFloat())

                t += steps
            }

            val x = r * (cos(t - rotationDegree) * (1 + curve * cos(sides * t)))
            val y = r * (sin(t - rotationDegree) * (1 + curve * cos(sides * t)))
            lineTo((x + xCenter).toFloat(), (y + yCenter).toFloat())

        }
    )

}

class PolygonShape(sides: Int, rotation: Float = 0f) : Shape {
    private val STEPCOUNT = ((TWO_PI) / sides)

    private val rotationDegree = (PI / 180) * rotation

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(Path().apply {

        val r = min(size.height, size.width) * .5f

        val xCenter = size.width * .5f
        val yCenter = size.height * .5f

        moveTo(xCenter, yCenter)

        var t = -rotationDegree

        while (t <= TWO_PI) {
            val x = r * cos(t)
            val y = r * sin(t)
            lineTo((x + xCenter).toFloat(), (y + yCenter).toFloat())

            t += STEPCOUNT
        }

        val x = r * cos(t)
        val y = r * sin(t)
        lineTo((x + xCenter).toFloat(), (y + yCenter).toFloat())
    })
}

val TetraStarShape = RoundedStarShape(4, 0.19f, 45f)
val HexagonShape = RoundedStarShape(6, 0.025f)
val ErtsgammaShape = RoundedStarShape(12, 0.06f)

data class SmoothRoundedCornerShape(
    private val topLeft: Dp = 0.dp,
    private val topLeftSmoothness: Int = 60,
    private val topRight: Dp = 0.dp,
    private val topRightSmoothness: Int = 60,
    private val bottomRight: Dp = 0.dp,
    private val bottomRightSmoothness: Int = 60,
    private val bottomLeft: Dp = 0.dp,
    private val bottomLeftSmoothness: Int = 60
) : CornerBasedShape(
    topStart = CornerSize(topLeft),
    topEnd = CornerSize(topRight),
    bottomEnd = CornerSize(bottomRight),
    bottomStart = CornerSize(bottomLeft)
) {
    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection
    ) = when {
        topStart + topEnd + bottomEnd + bottomStart == 0.0f -> {
            Outline.Rectangle(size.toRect())
        }
        topLeftSmoothness + topRightSmoothness +
                bottomRightSmoothness + bottomLeftSmoothness == 0 -> {
            Outline.Rounded(
                RoundRect(
                    rect = size.toRect(),
                    topLeft = CornerRadius(topStart),
                    topRight = CornerRadius(topEnd),
                    bottomRight = CornerRadius(bottomEnd),
                    bottomLeft = CornerRadius(bottomStart)
                )
            )
        }
        else -> {
            Outline.Generic(
                Path().apply {
                    val halfOfShortestSide = min(size.height, size.width) / 2
                    val smoothCornersMap = mutableMapOf<String, SmoothCorner>()

                    var selectedSmoothCorner =
                        smoothCornersMap["$topStart - $topLeftSmoothness"] ?: SmoothCorner(
                            topStart,
                            topLeftSmoothness,
                            halfOfShortestSide
                        )

                    // Top Left Corner
                    moveTo(
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide
                    )

                    cubicTo(
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToFurthestSide
                    )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = 0f,
                            right = selectedSmoothCorner.arcSection.radius * 2,
                            bottom = selectedSmoothCorner.arcSection.radius * 2
                        ),
                        startAngleRadians =
                        (toRadians(180.0) + selectedSmoothCorner.arcSection.arcStartAngle)
                            .toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    selectedSmoothCorner =
                        smoothCornersMap["$topEnd - $topRightSmoothness"] ?: SmoothCorner(
                            topEnd,
                            topRightSmoothness,
                            halfOfShortestSide
                        )

                    lineTo(
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    // Top Right Corner
                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.width - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.width - selectedSmoothCorner.anchorPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                    )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = size.width - selectedSmoothCorner.arcSection.radius * 2,
                            right = size.width,
                            bottom = selectedSmoothCorner.arcSection.radius * 2
                        ),
                        startAngleRadians =
                        (toRadians(270.0) + selectedSmoothCorner.arcSection.arcStartAngle)
                            .toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                    )

                    selectedSmoothCorner =
                        smoothCornersMap["$bottomEnd - $bottomRightSmoothness"] ?: SmoothCorner(
                            bottomEnd,
                            bottomRightSmoothness,
                            halfOfShortestSide
                        )

                    lineTo(
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide
                    )

                    // Bottom Right Corner
                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                        size.height - selectedSmoothCorner.anchorPoint2.distanceToFurthestSide
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - selectedSmoothCorner.arcSection.radius * 2,
                            left = size.width - selectedSmoothCorner.arcSection.radius * 2,
                            right = size.width,
                            bottom = size.height
                        ),
                        startAngleRadians =
                        (toRadians(0.0) + selectedSmoothCorner.arcSection.arcStartAngle)
                            .toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.width - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    selectedSmoothCorner =
                        smoothCornersMap["$bottomStart - $bottomLeftSmoothness"] ?: SmoothCorner(
                            bottomStart,
                            bottomLeftSmoothness,
                            halfOfShortestSide
                        )

                    lineTo(
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToClosestSide
                    )

                    // Bottom Left Corner
                    cubicTo(
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - selectedSmoothCorner.arcSection.radius * 2,
                            left = 0f,
                            right = selectedSmoothCorner.arcSection.radius * 2,
                            bottom = size.height
                        ),
                        startAngleRadians =
                        (toRadians(90.0) + selectedSmoothCorner.arcSection.arcStartAngle)
                            .toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false
                    )

                    cubicTo(
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide
                    )

                    close()

                }
            )
        }
    }

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize
    ) = SmoothRoundedCornerShape(
        topLeft,
        topLeftSmoothness,
        topRight,
        topRightSmoothness,
        bottomRight,
        bottomRightSmoothness,
        bottomLeft,
        bottomLeftSmoothness
    )

    /**
     * A class representing the points required to draw the curves in a smooth corner.
     * A smooth corner is made up of an arc surrounded by 2 diagonally symmetrical bezier curves.
     * Documentation on the makeup of a smooth curve:
     *
     * @param cornerRadius the size of the corners
     * @param smoothnessAsPercent a percentage representing how smooth the corners will be
     * @param maximumCurveStartDistanceFromVertex the maximum height/width this curve can have
     */
    private class SmoothCorner(
        cornerRadius: Float,
        smoothnessAsPercent: Int,
        maximumCurveStartDistanceFromVertex: Float
    ) {
        private val radius = min(cornerRadius, maximumCurveStartDistanceFromVertex)

        private val smoothness = smoothnessAsPercent / 100f

        // Distance from the first point of the curvature to the vertex of the corner
        private val curveStartDistance =
            min(maximumCurveStartDistanceFromVertex, (1 + smoothness) * radius)

        private val shouldCurveInterpolate = radius <= maximumCurveStartDistanceFromVertex / 2

        // This value is used to start interpolating between smooth corners and
        // round corners
        private val interpolationMultiplier =
            (radius - maximumCurveStartDistanceFromVertex / 2) / (maximumCurveStartDistanceFromVertex / 2)

        // Angle at second control point of the bezier curves
        private val angleAlpha =
            if (shouldCurveInterpolate)
                toRadians(45.0 * smoothness).toFloat()
            else
                toRadians(45.0 * smoothness * (1 - interpolationMultiplier)).toFloat()

        // Angle which dictates how much of the curve is going to be a slice of a circle
        private val angleBeta =
            if (shouldCurveInterpolate)
                toRadians(90.0 * (1.0 - smoothness)).toFloat()
            else
                toRadians(90.0 * (1 - smoothness * (1 - interpolationMultiplier))).toFloat()

        private val angleTheta = ((toRadians(90.0) - angleBeta) / 2.0).toFloat()

        // Distance from second control point to end of Bezier curves
        private val distanceE = radius * tan(angleTheta / 2)

        // Distances in the x and y axis used to position end of Bezier
        // curves relative to it's second control point
        private val distanceC = distanceE * cos(angleAlpha)
        private val distanceD = distanceC * tan(angleAlpha)

        // Distances used to position second control point of Bezier curves
        // relative to their first control point
        private val distanceK = sin(angleBeta / 2) * radius
        private val distanceL = (distanceK * sqrt(2.0)).toFloat()
        private val distanceB =
            ((curveStartDistance - distanceL) - (1 + tan(angleAlpha)) * distanceC) / 3

        // Distance used to position first control point of Bezier curves
        // relative to their origin
        private val distanceA = 2 * distanceB

        // Represents the outer anchor points of the smooth curve
        val anchorPoint1 = PointRelativeToVertex(
            min(curveStartDistance, maximumCurveStartDistanceFromVertex),
            0f
        )

        // Represents the control point for point1
        val controlPoint1 = PointRelativeToVertex(
            anchorPoint1.distanceToFurthestSide - distanceA,
            0f
        )

        // Represents the control point for point2
        val controlPoint2 = PointRelativeToVertex(
            controlPoint1.distanceToFurthestSide - distanceB,
            0f
        )

        // Represents the inner anchor points of the smooth curve
        val anchorPoint2 = PointRelativeToVertex(
            controlPoint2.distanceToFurthestSide - distanceC,
            distanceD
        )

        val arcSection = Arc(
            radius = radius,
            arcStartAngle = angleTheta,
            arcSweepAngle = angleBeta
        )
    }

    /**
     * Represents a point positioned relative to a corner vertex, so that it can be used
     * to calculate a smooth curve independently of which quadrant of the rectangle this
     * curve will be inserted in.
     */
    private data class PointRelativeToVertex(
        val distanceToFurthestSide: Float,
        val distanceToClosestSide: Float
    )

    /**
     * Represents the arc section of a smooth corner curve
     *
     * @param arcStartAngle the start angle of the arc inside the first quadrant of rotation (0º to 90º)
     * @param arcSweepAngle the angle at the center point between the start and end of the arc
     */
    private data class Arc(
        val radius: Float,
        val arcStartAngle: Float,
        val arcSweepAngle: Float
    )
}

fun SquircleShape(
    cornerRadius: Dp,
    smoothness: Int = 85
) = SquircleShape(
    topStart = cornerRadius,
    topEnd = cornerRadius,
    bottomEnd = cornerRadius,
    bottomStart = cornerRadius,
    smoothness = smoothness
)

fun SquircleShape(
    smoothness: Int = 85,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    bottomEnd: Dp = 0.dp
) = SmoothRoundedCornerShape(
    topLeft = topStart,
    topLeftSmoothness = smoothness,
    topRight = topEnd,
    topRightSmoothness = smoothness,
    bottomRight = bottomEnd,
    bottomRightSmoothness = smoothness,
    bottomLeft = bottomStart,
    bottomLeftSmoothness = smoothness
)
