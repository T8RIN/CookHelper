package ru.tech.cookhelper.presentation.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Rounded.Fridge: ImageVector
    get() {
        if (_fridge != null) {
            return _fridge!!
        }
        _fridge = ImageVector.Builder(
            name = "Fridge", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.0f, 2.0f)
                horizontalLineTo(17.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 19.0f,
                    y1 = 4.0f
                )
                verticalLineTo(9.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(4.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 7.0f,
                    y1 = 2.0f
                )
                moveTo(19.0f, 19.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 17.0f,
                    y1 = 21.0f
                )
                verticalLineTo(22.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(22.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(21.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 5.0f,
                    y1 = 19.0f
                )
                verticalLineTo(10.0f)
                horizontalLineTo(19.0f)
                verticalLineTo(19.0f)
                moveTo(8.0f, 5.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(8.0f)
                moveTo(8.0f, 12.0f)
                verticalLineTo(15.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(12.0f)
                horizontalLineTo(8.0f)
                close()
            }
        }
            .build()
        return _fridge!!
    }

private var _fridge: ImageVector? = null

val Icons.Outlined.Fridge: ImageVector
    get() {
        if (_fridgeOutline != null) {
            return _fridgeOutline!!
        }
        _fridgeOutline = ImageVector.Builder(
            name = "FridgeOutline", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(9.0f, 21.0f)
                verticalLineTo(22.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(21.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 5.0f,
                    y1 = 19.0f
                )
                verticalLineTo(4.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 7.0f,
                    y1 = 2.0f
                )
                horizontalLineTo(17.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 19.0f,
                    y1 = 4.0f
                )
                verticalLineTo(19.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 17.0f,
                    y1 = 21.0f
                )
                verticalLineTo(22.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(9.0f)
                moveTo(7.0f, 4.0f)
                verticalLineTo(9.0f)
                horizontalLineTo(17.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(7.0f)
                moveTo(7.0f, 19.0f)
                horizontalLineTo(17.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(19.0f)
                moveTo(8.0f, 12.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(15.0f)
                horizontalLineTo(8.0f)
                verticalLineTo(12.0f)
                moveTo(8.0f, 6.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(8.0f)
                horizontalLineTo(8.0f)
                verticalLineTo(6.0f)
                close()
            }
        }
            .build()
        return _fridgeOutline!!
    }

private var _fridgeOutline: ImageVector? = null


val Icons.Outlined.ProductMeasure: ImageVector
    get() {
        if (_scale != null) {
            return _scale!!
        }
        _scale = ImageVector.Builder(
            name = "ProductMeasure", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.46f, 15.06f)
                lineTo(7.05f, 16.47f)
                lineTo(5.68f, 15.1f)
                curveTo(4.82f, 16.21f, 4.24f, 17.54f, 4.06f, 19.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(20.0f)
                curveTo(2.0f, 15.16f, 5.44f, 11.13f, 10.0f, 10.2f)
                verticalLineTo(8.2f)
                lineTo(2.0f, 5.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(22.0f)
                verticalLineTo(5.0f)
                lineTo(14.0f, 8.2f)
                verticalLineTo(10.2f)
                curveTo(18.56f, 11.13f, 22.0f, 15.16f, 22.0f, 20.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(19.94f)
                curveTo(19.76f, 17.54f, 19.18f, 16.21f, 18.32f, 15.1f)
                lineTo(16.95f, 16.47f)
                lineTo(15.54f, 15.06f)
                lineTo(16.91f, 13.68f)
                curveTo(15.8f, 12.82f, 14.46f, 12.24f, 13.0f, 12.06f)
                verticalLineTo(14.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(12.06f)
                curveTo(9.54f, 12.24f, 8.2f, 12.82f, 7.09f, 13.68f)
                lineTo(8.46f, 15.06f)
                moveTo(12.0f, 18.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 14.0f,
                    y1 = 20.0f
                )
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 22.0f
                )
                curveTo(11.68f, 22.0f, 11.38f, 21.93f, 11.12f, 21.79f)
                lineTo(7.27f, 20.0f)
                lineTo(11.12f, 18.21f)
                curveTo(11.38f, 18.07f, 11.68f, 18.0f, 12.0f, 18.0f)
                close()
            }
        }
            .build()
        return _scale!!
    }

private var _scale: ImageVector? = null

val Icons.Filled.MessageDraw: ImageVector
    get() {
        if (_messageDraw != null) {
            return _messageDraw!!
        }
        _messageDraw = ImageVector.Builder(
            name = "MessageDraw", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(18.0f, 14.0f)
                horizontalLineTo(10.5f)
                lineTo(12.5f, 12.0f)
                horizontalLineTo(18.0f)
                moveTo(6.0f, 14.0f)
                verticalLineTo(11.5f)
                lineTo(12.88f, 4.64f)
                curveTo(13.07f, 4.45f, 13.39f, 4.45f, 13.59f, 4.64f)
                lineTo(15.35f, 6.41f)
                curveTo(15.55f, 6.61f, 15.55f, 6.92f, 15.35f, 7.12f)
                lineTo(8.47f, 14.0f)
                moveTo(20.0f, 2.0f)
                horizontalLineTo(4.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 2.0f,
                    y1 = 4.0f
                )
                verticalLineTo(22.0f)
                lineTo(6.0f, 18.0f)
                horizontalLineTo(20.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 22.0f,
                    y1 = 16.0f
                )
                verticalLineTo(4.0f)
                curveTo(22.0f, 2.89f, 21.1f, 2.0f, 20.0f, 2.0f)
                close()
            }
        }
            .build()
        return _messageDraw!!
    }

private var _messageDraw: ImageVector? = null

val Icons.Filled.Loading: ImageVector
    get() {
        if (_loading != null) {
            return _loading!!
        }
        _loading = ImageVector.Builder(
            name = "Loading", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(4.0f, 12.0f)
                lineToRelative(-2.0f, 0.0f)
                curveToRelative(0.0f, 5.5f, 4.5f, 10.0f, 10.0f, 10.0f)
                lineToRelative(0.0f, -2.0f)
                curveTo(7.6f, 20.0f, 4.0f, 16.4f, 4.0f, 12.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 20.0f)
                lineToRelative(0.0f, 2.0f)
                curveToRelative(5.5f, 0.0f, 10.0f, -4.5f, 10.0f, -10.0f)
                lineToRelative(-2.0f, 0.0f)
                curveTo(20.0f, 16.4f, 16.4f, 20.0f, 12.0f, 20.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.0f, 12.0f)
                lineToRelative(2.0f, 0.0f)
                curveToRelative(0.0f, -5.5f, -4.5f, -10.0f, -10.0f, -10.0f)
                lineToRelative(0.0f, 2.0f)
                curveTo(16.4f, 4.0f, 20.0f, 7.6f, 20.0f, 12.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(4.7f, 8.6f)
                lineTo(2.9f, 7.8f)
                curveToRelative(-2.3f, 5.0f, -0.2f, 11.0f, 4.9f, 13.3f)
                lineToRelative(0.8f, -1.8f)
                curveTo(4.6f, 17.4f, 2.9f, 12.6f, 4.7f, 8.6f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.6f, 19.2f)
                lineTo(7.8f, 21.0f)
                curveToRelative(5.0f, 2.3f, 11.0f, 0.2f, 13.3f, -4.9f)
                lineToRelative(-1.8f, -0.8f)
                curveTo(17.4f, 19.4f, 12.6f, 21.1f, 8.6f, 19.2f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.2f, 15.4f)
                lineToRelative(1.8f, 0.8f)
                curveToRelative(2.3f, -5.0f, 0.2f, -11.0f, -4.9f, -13.3f)
                lineToRelative(-0.8f, 1.8f)
                curveTo(19.4f, 6.6f, 21.1f, 11.4f, 19.2f, 15.4f)
                close()
            }
        }
            .build()
        return _loading!!
    }

private var _loading: ImageVector? = null

