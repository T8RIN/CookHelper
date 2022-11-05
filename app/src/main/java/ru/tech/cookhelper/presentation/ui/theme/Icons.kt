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

val Icons.Outlined.ForumRemove: ImageVector
    get() {
        if (_forumRemoveOutline != null) {
            return _forumRemoveOutline!!
        }
        _forumRemoveOutline = ImageVector.Builder(
            name = "Forum-remove-outline", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(21.0f, 6.0f)
                horizontalLineTo(19.0f)
                verticalLineTo(12.1f)
                curveTo(20.2f, 12.3f, 21.2f, 12.8f, 22.0f, 13.5f)
                verticalLineTo(7.0f)
                curveTo(22.0f, 6.5f, 21.5f, 6.0f, 21.0f, 6.0f)
                moveTo(6.0f, 17.0f)
                curveTo(6.0f, 17.5f, 6.5f, 18.0f, 7.0f, 18.0f)
                horizontalLineTo(12.0f)
                curveTo(12.0f, 16.9f, 12.3f, 15.9f, 12.8f, 15.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(17.0f)
                moveTo(16.0f, 2.0f)
                horizontalLineTo(3.0f)
                curveTo(2.5f, 2.0f, 2.0f, 2.5f, 2.0f, 3.0f)
                verticalLineTo(17.0f)
                lineTo(6.0f, 13.0f)
                horizontalLineTo(14.7f)
                curveTo(15.4f, 12.5f, 16.2f, 12.2f, 17.0f, 12.1f)
                verticalLineTo(3.0f)
                curveTo(17.0f, 2.5f, 16.5f, 2.0f, 16.0f, 2.0f)
                moveTo(15.0f, 11.0f)
                horizontalLineTo(5.2f)
                lineTo(4.0f, 12.2f)
                verticalLineTo(4.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(11.0f)
                moveTo(20.12f, 14.46f)
                lineTo(21.54f, 15.88f)
                lineTo(19.41f, 18.0f)
                lineTo(21.54f, 20.12f)
                lineTo(20.12f, 21.54f)
                lineTo(18.0f, 19.41f)
                lineTo(15.88f, 21.54f)
                lineTo(14.47f, 20.12f)
                lineTo(16.59f, 18.0f)
                lineTo(14.47f, 15.88f)
                lineTo(15.88f, 14.47f)
                lineTo(18.0f, 16.59f)
                lineTo(20.12f, 14.46f)
                close()
            }
        }
            .build()
        return _forumRemoveOutline!!
    }

private var _forumRemoveOutline: ImageVector? = null

val Icons.Rounded.CreateAlt: ImageVector
    get() {
        if (_createAlt != null) {
            return _createAlt!!
        }
        _createAlt = ImageVector.Builder(
            name = "CreateAlt", defaultWidth = 18.2.dp, defaultHeight = 18.2.dp,
            viewportWidth = 18.2f, viewportHeight = 18.2f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(2.0f, 16.2f)
                horizontalLineToRelative(1.4f)
                lineToRelative(10.0f, -10.0f)
                lineToRelative(-0.7f, -0.7f)
                lineToRelative(-0.8f, -0.7f)
                lineTo(2.0f, 14.9f)
                verticalLineTo(16.2f)
                close()
                moveTo(0.0f, 18.2f)
                verticalLineTo(14.0f)
                lineTo(13.4f, 0.6f)
                curveTo(13.7f, 0.2f, 14.2f, 0.0f, 14.8f, 0.0f)
                curveToRelative(0.6f, 0.0f, 1.0f, 0.2f, 1.4f, 0.6f)
                lineTo(17.6f, 2.0f)
                curveToRelative(0.4f, 0.4f, 0.6f, 0.8f, 0.6f, 1.4f)
                reflectiveCurveToRelative(-0.2f, 1.0f, -0.6f, 1.4f)
                lineTo(4.2f, 18.2f)
                horizontalLineTo(0.0f)
                close()
                moveTo(16.1f, 3.4f)
                lineTo(14.8f, 2.0f)
                lineTo(16.1f, 3.4f)
                close()
                moveTo(13.4f, 6.2f)
                lineToRelative(-0.7f, -0.7f)
                lineToRelative(-0.8f, -0.7f)
                lineTo(13.4f, 6.2f)
                close()
            }
        }
            .build()
        return _createAlt!!
    }

private var _createAlt: ImageVector? = null

val Icons.Filled.SausageOff: ImageVector
    get() {
        if (_sausageOff != null) {
            return _sausageOff!!
        }
        _sausageOff = ImageVector.Builder(
            name = "Sausage-off", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.8f, 22.7f)
                lineTo(16.3f, 18.2f)
                curveTo(14.2f, 19.9f, 11.4f, 21.0f, 8.5f, 21.0f)
                curveTo(7.1f, 21.0f, 5.9f, 20.2f, 5.3f, 19.0f)
                lineTo(3.0f, 20.5f)
                verticalLineTo(14.5f)
                lineTo(5.3f, 16.0f)
                curveTo(5.8f, 14.8f, 7.1f, 14.0f, 8.5f, 14.0f)
                curveTo(9.5f, 14.0f, 10.5f, 13.7f, 11.3f, 13.2f)
                lineTo(1.1f, 3.0f)
                lineTo(2.4f, 1.7f)
                lineTo(22.1f, 21.4f)
                lineTo(20.8f, 22.7f)
                moveTo(21.0f, 8.5f)
                curveTo(21.0f, 7.1f, 20.2f, 5.9f, 19.0f, 5.3f)
                lineTo(20.5f, 3.0f)
                horizontalLineTo(14.5f)
                lineTo(16.0f, 5.3f)
                curveTo(14.8f, 5.8f, 14.0f, 7.1f, 14.0f, 8.5f)
                curveTo(14.0f, 9.2f, 13.9f, 9.8f, 13.6f, 10.4f)
                lineTo(18.7f, 15.6f)
                curveTo(20.2f, 13.6f, 21.0f, 11.1f, 21.0f, 8.5f)
                close()
            }
        }
            .build()
        return _sausageOff!!
    }

private var _sausageOff: ImageVector? = null