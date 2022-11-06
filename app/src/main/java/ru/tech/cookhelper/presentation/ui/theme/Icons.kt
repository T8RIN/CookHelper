package ru.tech.cookhelper.presentation.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Rounded.Fridge: ImageVector
    get() {
        if (_fridge != null) {
            return _fridge!!
        }
        _fridge = Builder(
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
        _fridgeOutline = Builder(
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
        _scale = Builder(
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
        _messageDraw = Builder(
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
        _loading = Builder(
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
        _forumRemoveOutline = Builder(
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
        _createAlt = Builder(
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
        _sausageOff = Builder(
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

val Icons.Filled.Apple: ImageVector
    get() {
        if (_apple != null) {
            return _apple!!
        }
        _apple = Builder(
            name = "Apple", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.0f, 10.0f)
                curveTo(22.0f, 13.0f, 17.0f, 22.0f, 15.0f, 22.0f)
                curveTo(13.0f, 22.0f, 13.0f, 21.0f, 12.0f, 21.0f)
                curveTo(11.0f, 21.0f, 11.0f, 22.0f, 9.0f, 22.0f)
                curveTo(7.0f, 22.0f, 2.0f, 13.0f, 4.0f, 10.0f)
                curveTo(6.0f, 7.0f, 9.0f, 7.0f, 11.0f, 8.0f)
                verticalLineTo(5.0f)
                curveTo(5.38f, 8.07f, 4.11f, 3.78f, 4.11f, 3.78f)
                curveTo(4.11f, 3.78f, 6.77f, 0.19f, 11.0f, 5.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(8.0f)
                curveTo(15.0f, 7.0f, 18.0f, 7.0f, 20.0f, 10.0f)
                close()
            }
        }
            .build()
        return _apple!!
    }

private var _apple: ImageVector? = null

val Icons.Filled.Baguette: ImageVector
    get() {
        if (_baguette != null) {
            return _baguette!!
        }
        _baguette = Builder(
            name = "Baguette", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(5.0f, 22.0f)
                curveTo(3.68f, 22.0f, 3.15f, 19.64f, 3.04f, 18.7f)
                arcTo(
                    5.56f, 5.56f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 3.36f,
                    y1 = 16.0f
                )
                arcTo(
                    2.5f, 2.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 5.23f,
                    y1 = 14.38f
                )
                curveTo(6.4f, 14.18f, 7.23f, 14.88f, 8.29f, 15.12f)
                arcTo(
                    1.21f, 1.21f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 9.85f,
                    y1 = 13.75f
                )
                curveTo(9.41f, 12.03f, 6.28f, 12.0f, 5.0f, 12.0f)
                curveTo(5.0f, 10.14f, 7.04f, 9.9f, 8.5f, 10.04f)
                arcTo(
                    10.8f, 10.8f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 11.04f,
                    y1 = 10.6f
                )
                curveTo(11.54f, 10.77f, 12.12f, 11.2f, 12.67f, 11.16f)
                curveTo(13.5f, 11.09f, 13.67f, 10.23f, 13.31f, 9.6f)
                curveTo(12.44f, 8.12f, 9.97f, 8.0f, 8.5f, 8.0f)
                curveTo(8.5f, 6.0f, 10.23f, 5.62f, 11.89f, 5.92f)
                arcTo(
                    11.58f, 11.58f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 14.38f,
                    y1 = 6.71f
                )
                curveTo(14.89f, 6.93f, 15.5f, 7.35f, 16.06f, 7.16f)
                curveTo(17.5f, 6.72f, 16.0f, 5.18f, 15.36f, 4.81f)
                arcTo(
                    6.6f, 6.6f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 13.94f,
                    y1 = 4.23f
                )
                curveTo(13.4f, 4.07f, 12.74f, 4.13f, 13.23f, 3.5f)
                arcTo(
                    5.13f, 5.13f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 15.96f,
                    y1 = 2.26f
                )
                curveTo(17.85f, 1.82f, 20.46f, 1.74f, 20.92f, 4.12f)
                arcTo(
                    5.3f, 5.3f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 20.07f,
                    y1 = 7.7f
                )
                arcTo(
                    38.96f, 38.96f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 13.22f,
                    y1 = 16.33f
                )
                arcTo(
                    36.6f, 36.6f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 8.62f,
                    y1 = 20.32f
                )
                curveTo(7.62f, 21.04f, 6.3f, 22.0f, 5.0f, 22.0f)
                close()
            }
        }
            .build()
        return _baguette!!
    }

private var _baguette: ImageVector? = null

val Icons.Filled.Barley: ImageVector
    get() {
        if (_barley != null) {
            return _barley!!
        }
        _barley = Builder(
            name = "Barley", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.33f, 18.33f)
                curveTo(6.5f, 17.17f, 6.5f, 15.83f, 6.5f, 14.5f)
                curveTo(8.17f, 15.5f, 9.83f, 16.5f, 10.67f, 17.67f)
                lineTo(11.0f, 18.23f)
                verticalLineTo(15.95f)
                curveTo(9.5f, 15.05f, 8.08f, 14.13f, 7.33f, 13.08f)
                curveTo(6.5f, 11.92f, 6.5f, 10.58f, 6.5f, 9.25f)
                curveTo(8.17f, 10.25f, 9.83f, 11.25f, 10.67f, 12.42f)
                lineTo(11.0f, 13.0f)
                verticalLineTo(10.7f)
                curveTo(9.5f, 9.8f, 8.08f, 8.88f, 7.33f, 7.83f)
                curveTo(6.5f, 6.67f, 6.5f, 5.33f, 6.5f, 4.0f)
                curveTo(8.17f, 5.0f, 9.83f, 6.0f, 10.67f, 7.17f)
                curveTo(10.77f, 7.31f, 10.86f, 7.46f, 10.94f, 7.62f)
                curveTo(10.77f, 7.0f, 10.66f, 6.42f, 10.65f, 5.82f)
                curveTo(10.64f, 4.31f, 11.3f, 2.76f, 11.96f, 1.21f)
                curveTo(12.65f, 2.69f, 13.34f, 4.18f, 13.35f, 5.69f)
                curveTo(13.36f, 6.32f, 13.25f, 6.96f, 13.07f, 7.59f)
                curveTo(13.15f, 7.45f, 13.23f, 7.31f, 13.33f, 7.17f)
                curveTo(14.17f, 6.0f, 15.83f, 5.0f, 17.5f, 4.0f)
                curveTo(17.5f, 5.33f, 17.5f, 6.67f, 16.67f, 7.83f)
                curveTo(15.92f, 8.88f, 14.5f, 9.8f, 13.0f, 10.7f)
                verticalLineTo(13.0f)
                lineTo(13.33f, 12.42f)
                curveTo(14.17f, 11.25f, 15.83f, 10.25f, 17.5f, 9.25f)
                curveTo(17.5f, 10.58f, 17.5f, 11.92f, 16.67f, 13.08f)
                curveTo(15.92f, 14.13f, 14.5f, 15.05f, 13.0f, 15.95f)
                verticalLineTo(18.23f)
                lineTo(13.33f, 17.67f)
                curveTo(14.17f, 16.5f, 15.83f, 15.5f, 17.5f, 14.5f)
                curveTo(17.5f, 15.83f, 17.5f, 17.17f, 16.67f, 18.33f)
                curveTo(15.92f, 19.38f, 14.5f, 20.3f, 13.0f, 21.2f)
                verticalLineTo(23.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(21.2f)
                curveTo(9.5f, 20.3f, 8.08f, 19.38f, 7.33f, 18.33f)
                close()
            }
        }
            .build()
        return _barley!!
    }

private var _barley: ImageVector? = null

val Icons.Filled.Bean: ImageVector
    get() {
        if (_bean != null) {
            return _bean!!
        }
        _bean = Builder(
            name = "Bean", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF030304)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.8f, 3.0f)
                curveToRelative(2.3f, 0.0f, 4.1f, 1.3f, 4.8f, 3.4f)
                curveToRelative(0.2f, 0.4f, 0.2f, 0.7f, 0.2f, 1.1f)
                curveToRelative(0.1f, 0.5f, 0.3f, 1.0f, 0.6f, 1.4f)
                curveToRelative(0.7f, 1.1f, 1.7f, 1.9f, 3.0f, 2.2f)
                curveToRelative(0.4f, 0.1f, 0.8f, 0.1f, 1.1f, 0.2f)
                curveToRelative(1.8f, 0.6f, 2.9f, 1.8f, 3.4f, 3.6f)
                curveToRelative(0.5f, 2.2f, -0.6f, 4.6f, -2.8f, 5.6f)
                curveTo(17.4f, 20.9f, 16.7f, 21.0f, 16.0f, 21.0f)
                curveToRelative(-2.8f, 0.0f, -5.3f, -1.0f, -7.4f, -2.7f)
                curveToRelative(-1.9f, -1.4f, -3.3f, -3.2f, -4.4f, -5.3f)
                curveTo(3.5f, 11.6f, 3.1f, 10.1f, 3.0f, 8.5f)
                curveTo(2.9f, 6.6f, 3.5f, 5.0f, 5.2f, 3.9f)
                curveTo(6.0f, 3.3f, 6.9f, 3.0f, 7.8f, 3.0f)
                close()
                moveTo(16.0f, 13.1f)
                curveToRelative(0.2f, 0.0f, 0.4f, -0.1f, 0.4f, -0.3f)
                curveToRelative(0.1f, -0.2f, -0.1f, -0.4f, -0.3f, -0.5f)
                curveToRelative(-0.6f, -0.1f, -1.1f, -0.3f, -1.7f, -0.6f)
                curveToRelative(-1.4f, -0.7f, -2.2f, -1.8f, -2.7f, -3.2f)
                curveToRelative(-0.2f, -0.4f, -0.2f, -0.9f, -0.3f, -1.3f)
                curveToRelative(0.0f, -0.2f, -0.2f, -0.4f, -0.5f, -0.3f)
                reflectiveCurveToRelative(-0.4f, 0.2f, -0.3f, 0.4f)
                curveToRelative(0.1f, 0.4f, 0.2f, 0.7f, 0.2f, 1.0f)
                curveToRelative(0.7f, 2.2f, 2.0f, 3.8f, 4.3f, 4.5f)
                curveTo(15.5f, 13.0f, 15.7f, 13.1f, 16.0f, 13.1f)
                close()
            }
        }
            .build()
        return _bean!!
    }

private var _bean: ImageVector? = null

val Icons.Filled.Candy: ImageVector
    get() {
        if (_candy != null) {
            return _candy!!
        }
        _candy = Builder(
            name = "Candy", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.54f, 8.46f)
                curveTo(17.5f, 10.42f, 17.5f, 13.58f, 15.54f, 15.54f)
                reflectiveCurveTo(10.42f, 17.5f, 8.47f, 15.54f)
                reflectiveCurveTo(6.5f, 10.42f, 8.47f, 8.46f)
                reflectiveCurveTo(13.58f, 6.5f, 15.54f, 8.46f)
                moveTo(19.47f, 4.55f)
                curveTo(19.47f, 4.55f, 18.5f, 4.67f, 17.43f, 5.36f)
                curveTo(17.28f, 4.32f, 16.78f, 3.27f, 15.93f, 2.42f)
                curveTo(14.68f, 3.66f, 14.53f, 5.22f, 14.83f, 6.34f)
                curveTo(16.22f, 6.7f, 17.3f, 7.78f, 17.66f, 9.17f)
                curveTo(18.78f, 9.47f, 20.34f, 9.32f, 21.58f, 8.07f)
                curveTo(20.74f, 7.23f, 19.71f, 6.74f, 18.68f, 6.58f)
                curveTo(19.07f, 6.0f, 19.38f, 5.33f, 19.47f, 4.55f)
                moveTo(4.53f, 19.45f)
                curveTo(4.53f, 19.45f, 5.5f, 19.33f, 6.57f, 18.64f)
                curveTo(6.72f, 19.68f, 7.22f, 20.73f, 8.07f, 21.58f)
                curveTo(9.32f, 20.34f, 9.47f, 18.78f, 9.17f, 17.66f)
                curveTo(7.78f, 17.3f, 6.7f, 16.22f, 6.34f, 14.83f)
                curveTo(5.22f, 14.53f, 3.66f, 14.68f, 2.42f, 15.93f)
                curveTo(3.26f, 16.77f, 4.29f, 17.27f, 5.32f, 17.42f)
                curveTo(4.93f, 18.0f, 4.62f, 18.68f, 4.53f, 19.45f)
                close()
            }
        }
            .build()
        return _candy!!
    }

private var _candy: ImageVector? = null

val Icons.Filled.Carrot: ImageVector
    get() {
        if (_carrot != null) {
            return _carrot!!
        }
        _carrot = Builder(
            name = "Carrot", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.0f, 10.0f)
                lineTo(15.8f, 11.0f)
                horizontalLineTo(13.5f)
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 13.0f,
                    y1 = 11.5f
                )
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 13.5f,
                    y1 = 12.0f
                )
                horizontalLineTo(15.6f)
                lineTo(14.6f, 17.0f)
                horizontalLineTo(12.5f)
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 12.0f,
                    y1 = 17.5f
                )
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 12.5f,
                    y1 = 18.0f
                )
                horizontalLineTo(14.4f)
                lineTo(14.0f, 20.0f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 22.0f
                )
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 10.0f,
                    y1 = 20.0f
                )
                lineTo(9.0f, 15.0f)
                horizontalLineTo(10.5f)
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 11.0f,
                    y1 = 14.5f
                )
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 10.5f,
                    y1 = 14.0f
                )
                horizontalLineTo(8.8f)
                lineTo(8.0f, 10.0f)
                curveTo(8.0f, 8.8f, 8.93f, 7.77f, 10.29f, 7.29f)
                lineTo(8.9f, 5.28f)
                curveTo(8.59f, 4.82f, 8.7f, 4.2f, 9.16f, 3.89f)
                curveTo(9.61f, 3.57f, 10.23f, 3.69f, 10.55f, 4.14f)
                lineTo(11.0f, 4.8f)
                verticalLineTo(3.0f)
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 2.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 13.0f,
                    y1 = 3.0f
                )
                verticalLineTo(5.28f)
                lineTo(14.5f, 3.54f)
                curveTo(14.83f, 3.12f, 15.47f, 3.07f, 15.89f, 3.43f)
                curveTo(16.31f, 3.78f, 16.36f, 4.41f, 16.0f, 4.84f)
                lineTo(13.87f, 7.35f)
                curveTo(15.14f, 7.85f, 16.0f, 8.85f, 16.0f, 10.0f)
                close()
            }
        }
            .build()
        return _carrot!!
    }

private var _carrot: ImageVector? = null

val Icons.Filled.Cheese: ImageVector
    get() {
        if (_cheese != null) {
            return _cheese!!
        }
        _cheese = Builder(
            name = "Cheese", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.0f, 17.5f)
                curveTo(11.0f, 16.67f, 11.67f, 16.0f, 12.5f, 16.0f)
                curveTo(12.79f, 16.0f, 13.06f, 16.09f, 13.29f, 16.23f)
                lineTo(20.75f, 11.93f)
                curveTo(20.35f, 11.22f, 19.9f, 10.55f, 19.41f, 9.9f)
                curveTo(19.29f, 9.96f, 19.15f, 10.0f, 19.0f, 10.0f)
                curveTo(18.45f, 10.0f, 18.0f, 9.55f, 18.0f, 9.0f)
                curveTo(18.0f, 8.8f, 18.08f, 8.62f, 18.18f, 8.46f)
                curveTo(16.45f, 6.64f, 14.34f, 5.2f, 12.0f, 4.25f)
                curveTo(11.85f, 5.24f, 11.0f, 6.0f, 10.0f, 6.0f)
                curveTo(8.9f, 6.0f, 8.0f, 5.11f, 8.0f, 4.0f)
                curveTo(8.0f, 3.72f, 8.06f, 3.45f, 8.16f, 3.21f)
                curveTo(7.3f, 3.08f, 6.41f, 3.0f, 5.5f, 3.0f)
                curveTo(5.33f, 3.0f, 5.17f, 3.0f, 5.0f, 3.03f)
                verticalLineTo(9.05f)
                curveTo(6.14f, 9.28f, 7.0f, 10.29f, 7.0f, 11.5f)
                reflectiveCurveTo(6.14f, 13.72f, 5.0f, 13.95f)
                verticalLineTo(21.0f)
                lineTo(11.0f, 17.54f)
                curveTo(11.0f, 17.53f, 11.0f, 17.5f, 11.0f, 17.5f)
                moveTo(14.0f, 9.0f)
                curveTo(15.11f, 9.0f, 16.0f, 9.9f, 16.0f, 11.0f)
                reflectiveCurveTo(15.11f, 13.0f, 14.0f, 13.0f)
                reflectiveCurveTo(12.0f, 12.11f, 12.0f, 11.0f)
                reflectiveCurveTo(12.9f, 9.0f, 14.0f, 9.0f)
                moveTo(9.0f, 16.0f)
                curveTo(8.45f, 16.0f, 8.0f, 15.55f, 8.0f, 15.0f)
                reflectiveCurveTo(8.45f, 14.0f, 9.0f, 14.0f)
                reflectiveCurveTo(10.0f, 14.45f, 10.0f, 15.0f)
                reflectiveCurveTo(9.55f, 16.0f, 9.0f, 16.0f)
                moveTo(9.0f, 10.0f)
                curveTo(8.45f, 10.0f, 8.0f, 9.55f, 8.0f, 9.0f)
                reflectiveCurveTo(8.45f, 8.0f, 9.0f, 8.0f)
                reflectiveCurveTo(10.0f, 8.45f, 10.0f, 9.0f)
                reflectiveCurveTo(9.55f, 10.0f, 9.0f, 10.0f)
                close()
            }
        }
            .build()
        return _cheese!!
    }

private var _cheese: ImageVector? = null

val Icons.Filled.Cherry: ImageVector
    get() {
        if (_cherry != null) {
            return _cherry!!
        }
        _cherry = Builder(
            name = "Cherry", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.0f, 13.0f)
                horizontalLineTo(15.5f)
                curveTo(14.8f, 11.7f, 14.3f, 10.0f, 13.8f, 8.3f)
                lineTo(14.7f, 9.2f)
                curveTo(17.4f, 11.3f, 19.8f, 10.9f, 19.8f, 10.9f)
                reflectiveCurveTo(20.5f, 7.1f, 17.8f, 5.1f)
                curveTo(15.5f, 3.3f, 13.4f, 3.3f, 12.8f, 3.4f)
                curveTo(12.7f, 2.8f, 12.7f, 2.3f, 12.6f, 1.9f)
                lineTo(11.2f, 2.0f)
                curveTo(11.2f, 5.2f, 8.5f, 11.1f, 7.6f, 13.0f)
                curveTo(5.6f, 13.2f, 4.0f, 14.9f, 4.0f, 17.0f)
                curveTo(4.0f, 19.2f, 5.8f, 21.0f, 8.0f, 21.0f)
                curveTo(9.1f, 21.0f, 10.0f, 20.6f, 10.7f, 19.9f)
                curveTo(10.3f, 19.0f, 10.0f, 18.0f, 10.0f, 17.0f)
                reflectiveCurveTo(10.3f, 15.0f, 10.7f, 14.1f)
                curveTo(10.3f, 13.7f, 9.7f, 13.4f, 9.2f, 13.2f)
                curveTo(9.9f, 11.7f, 11.1f, 9.0f, 11.9f, 6.4f)
                curveTo(12.3f, 8.7f, 13.1f, 11.4f, 14.1f, 13.5f)
                curveTo(12.9f, 14.2f, 12.0f, 15.5f, 12.0f, 17.0f)
                curveTo(12.0f, 19.2f, 13.8f, 21.0f, 16.0f, 21.0f)
                reflectiveCurveTo(20.0f, 19.2f, 20.0f, 17.0f)
                reflectiveCurveTo(18.2f, 13.0f, 16.0f, 13.0f)
                moveTo(8.0f, 15.5f)
                curveTo(7.2f, 15.5f, 6.5f, 16.2f, 6.5f, 17.0f)
                horizontalLineTo(5.5f)
                curveTo(5.5f, 15.6f, 6.6f, 14.5f, 8.0f, 14.5f)
                verticalLineTo(15.5f)
                moveTo(16.0f, 15.5f)
                curveTo(15.2f, 15.5f, 14.5f, 16.2f, 14.5f, 17.0f)
                horizontalLineTo(13.5f)
                curveTo(13.5f, 15.6f, 14.6f, 14.5f, 16.0f, 14.5f)
                verticalLineTo(15.5f)
                close()
            }
        }
            .build()
        return _cherry!!
    }

private var _cherry: ImageVector? = null

val Icons.Filled.Cup: ImageVector
    get() {
        if (_cup != null) {
            return _cup!!
        }
        _cup = Builder(
            name = "Cup", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
            = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(18.32f, 8.0f)
                horizontalLineTo(5.67f)
                lineTo(5.23f, 4.0f)
                horizontalLineTo(18.77f)
                moveTo(3.0f, 2.0f)
                lineTo(5.0f, 20.23f)
                curveTo(5.13f, 21.23f, 5.97f, 22.0f, 7.0f, 22.0f)
                horizontalLineTo(17.0f)
                curveTo(18.0f, 22.0f, 18.87f, 21.23f, 19.0f, 20.23f)
                lineTo(21.0f, 2.0f)
                horizontalLineTo(3.0f)
                close()
            }
        }
            .build()
        return _cup!!
    }

private var _cup: ImageVector? = null

val Icons.Filled.FriedEgg: ImageVector
    get() {
        if (_eggfried != null) {
            return _eggfried!!
        }
        _eggfried = Builder(
            name = "FriedEgg", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 4.5f)
                curveTo(14.17f, 4.5f, 14.58f, 5.07f, 15.5f, 6.32f)
                curveTo(15.88f, 6.85f, 16.32f, 7.44f, 16.94f, 8.06f)
                curveTo(17.43f, 8.55f, 17.86f, 8.93f, 18.21f, 9.24f)
                curveTo(19.3f, 10.19f, 19.5f, 10.36f, 19.5f, 12.0f)
                curveTo(19.5f, 14.93f, 19.5f, 15.38f, 17.94f, 16.94f)
                curveTo(16.0f, 18.86f, 15.38f, 19.5f, 13.0f, 19.5f)
                curveTo(11.88f, 19.5f, 11.5f, 19.18f, 10.89f, 18.69f)
                curveTo(10.27f, 18.19f, 9.43f, 17.5f, 8.0f, 17.5f)
                curveTo(4.96f, 17.5f, 4.5f, 14.05f, 4.5f, 12.0f)
                curveTo(4.5f, 10.65f, 5.0f, 8.91f, 6.47f, 8.42f)
                curveTo(8.25f, 7.83f, 9.2f, 6.71f, 9.96f, 5.81f)
                curveTo(10.75f, 4.88f, 11.11f, 4.5f, 12.0f, 4.5f)
                moveTo(12.0f, 3.0f)
                curveTo(9.0f, 3.0f, 9.0f, 6.0f, 6.0f, 7.0f)
                curveTo(3.88f, 7.71f, 3.0f, 10.0f, 3.0f, 12.0f)
                curveTo(3.0f, 15.0f, 4.0f, 19.0f, 8.0f, 19.0f)
                curveTo(10.0f, 19.0f, 10.0f, 21.0f, 13.0f, 21.0f)
                reflectiveCurveTo(17.0f, 20.0f, 19.0f, 18.0f)
                reflectiveCurveTo(21.0f, 15.0f, 21.0f, 12.0f)
                reflectiveCurveTo(20.0f, 9.0f, 18.0f, 7.0f)
                reflectiveCurveTo(16.0f, 3.0f, 12.0f, 3.0f)
                moveTo(12.0f, 8.0f)
                curveTo(9.79f, 8.0f, 8.0f, 9.79f, 8.0f, 12.0f)
                reflectiveCurveTo(9.79f, 16.0f, 12.0f, 16.0f)
                reflectiveCurveTo(16.0f, 14.21f, 16.0f, 12.0f)
                reflectiveCurveTo(14.21f, 8.0f, 12.0f, 8.0f)
                moveTo(12.0f, 10.0f)
                curveTo(10.9f, 10.0f, 10.0f, 10.9f, 10.0f, 12.0f)
                horizontalLineTo(9.0f)
                curveTo(9.0f, 10.35f, 10.35f, 9.0f, 12.0f, 9.0f)
                verticalLineTo(10.0f)
                close()
            }
        }
            .build()
        return _eggfried!!
    }

private var _eggfried: ImageVector? = null

val Icons.Filled.Fish: ImageVector
    get() {
        if (_fish != null) {
            return _fish!!
        }
        _fish = Builder(
            name = "Fish", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 20.0f)
                lineTo(12.76f, 17.0f)
                curveTo(9.5f, 16.79f, 6.59f, 15.4f, 5.75f, 13.58f)
                curveTo(5.66f, 14.06f, 5.53f, 14.5f, 5.33f, 14.83f)
                curveTo(4.67f, 16.0f, 3.33f, 16.0f, 2.0f, 16.0f)
                curveTo(3.1f, 16.0f, 3.5f, 14.43f, 3.5f, 12.5f)
                curveTo(3.5f, 10.57f, 3.1f, 9.0f, 2.0f, 9.0f)
                curveTo(3.33f, 9.0f, 4.67f, 9.0f, 5.33f, 10.17f)
                curveTo(5.53f, 10.5f, 5.66f, 10.94f, 5.75f, 11.42f)
                curveTo(6.4f, 10.0f, 8.32f, 8.85f, 10.66f, 8.32f)
                lineTo(9.0f, 5.0f)
                curveTo(11.0f, 5.0f, 13.0f, 5.0f, 14.33f, 5.67f)
                curveTo(15.46f, 6.23f, 16.11f, 7.27f, 16.69f, 8.38f)
                curveTo(19.61f, 9.08f, 22.0f, 10.66f, 22.0f, 12.5f)
                curveTo(22.0f, 14.38f, 19.5f, 16.0f, 16.5f, 16.66f)
                curveTo(15.67f, 17.76f, 14.86f, 18.78f, 14.17f, 19.33f)
                curveTo(13.33f, 20.0f, 12.67f, 20.0f, 12.0f, 20.0f)
                moveTo(17.0f, 11.0f)
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 16.0f,
                    y1 = 12.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 17.0f,
                    y1 = 13.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 18.0f,
                    y1 = 12.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 17.0f,
                    y1 = 11.0f
                )
                close()
            }
        }
            .build()
        return _fish!!
    }

private var _fish: ImageVector? = null

val Icons.Filled.Flavour: ImageVector
    get() {
        if (_flavour != null) {
            return _flavour!!
        }
        _flavour = Builder(
            name = "Flavour", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.5f, 5.2f)
                lineToRelative(-1.4f, -1.7f)
                curveTo(18.9f, 3.2f, 18.5f, 3.0f, 18.0f, 3.0f)
                horizontalLineTo(6.0f)
                curveTo(5.5f, 3.0f, 5.1f, 3.2f, 4.9f, 3.5f)
                lineTo(3.5f, 5.2f)
                curveTo(3.2f, 5.6f, 3.0f, 6.0f, 3.0f, 6.5f)
                verticalLineTo(19.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(6.5f)
                curveTo(21.0f, 6.0f, 20.8f, 5.6f, 20.5f, 5.2f)
                close()
                moveTo(12.8f, 13.3f)
                curveToRelative(0.5f, -0.7f, 1.5f, -1.3f, 2.5f, -1.9f)
                curveToRelative(0.0f, 0.8f, 0.0f, 1.6f, -0.5f, 2.3f)
                curveToRelative(-0.4f, 0.6f, -1.3f, 1.2f, -2.2f, 1.7f)
                verticalLineToRelative(1.3f)
                lineToRelative(0.2f, -0.3f)
                curveToRelative(0.5f, -0.7f, 1.5f, -1.3f, 2.5f, -1.9f)
                curveToRelative(0.0f, 0.8f, 0.0f, 1.6f, -0.5f, 2.3f)
                curveToRelative(-0.4f, 0.6f, -1.3f, 1.2f, -2.2f, 1.7f)
                verticalLineToRelative(1.1f)
                horizontalLineToRelative(-1.2f)
                verticalLineToRelative(-1.1f)
                curveToRelative(-0.9f, -0.7f, -1.8f, -1.3f, -2.2f, -1.9f)
                curveToRelative(-0.5f, -0.7f, -0.5f, -1.5f, -0.5f, -2.3f)
                curveToRelative(1.0f, 0.6f, 2.0f, 1.2f, 2.5f, 1.9f)
                lineToRelative(0.2f, 0.3f)
                verticalLineToRelative(-1.3f)
                curveToRelative(-0.9f, -0.5f, -1.8f, -1.1f, -2.2f, -1.7f)
                curveToRelative(-0.5f, -0.7f, -0.5f, -1.5f, -0.5f, -2.3f)
                curveToRelative(1.0f, 0.6f, 2.0f, 1.2f, 2.5f, 1.9f)
                lineToRelative(0.2f, 0.3f)
                verticalLineTo(12.0f)
                curveToRelative(-0.9f, -0.5f, -1.8f, -1.1f, -2.2f, -1.7f)
                curveTo(8.7f, 9.7f, 8.7f, 8.9f, 8.7f, 8.2f)
                curveToRelative(1.0f, 0.6f, 2.0f, 1.2f, 2.5f, 1.9f)
                curveToRelative(0.1f, 0.1f, 0.1f, 0.2f, 0.2f, 0.3f)
                curveToRelative(-0.1f, -0.4f, -0.2f, -0.7f, -0.2f, -1.1f)
                curveToRelative(0.0f, -0.9f, 0.4f, -1.8f, 0.8f, -2.7f)
                curveToRelative(0.4f, 0.9f, 0.8f, 1.7f, 0.8f, 2.6f)
                curveToRelative(0.0f, 0.4f, -0.1f, 0.7f, -0.2f, 1.1f)
                curveToRelative(0.0f, -0.1f, 0.1f, -0.2f, 0.2f, -0.2f)
                curveToRelative(0.5f, -0.7f, 1.5f, -1.3f, 2.5f, -1.9f)
                curveToRelative(0.0f, 0.8f, 0.0f, 1.6f, -0.5f, 2.3f)
                curveToRelative(-0.4f, 0.6f, -1.3f, 1.2f, -2.2f, 1.7f)
                verticalLineToRelative(1.4f)
                lineTo(12.8f, 13.3f)
                close()
                moveTo(5.1f, 5.0f)
                lineTo(6.0f, 4.0f)
                horizontalLineToRelative(12.0f)
                lineToRelative(0.9f, 1.0f)
                horizontalLineTo(5.1f)
                close()
            }
        }
            .build()
        return _flavour!!
    }

private var _flavour: ImageVector? = null

val Icons.Filled.Jellyfish: ImageVector
    get() {
        if (_jellyfish != null) {
            return _jellyfish!!
        }
        _jellyfish = Builder(
            name = "Jellyfish", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.5f, 14.5f)
                curveTo(18.92f, 14.43f, 18.43f, 14.92f, 18.5f, 15.5f)
                curveTo(18.5f, 16.17f, 17.5f, 16.17f, 17.5f, 15.5f)
                verticalLineTo(13.2f)
                lineTo(19.2f, 12.7f)
                curveTo(19.92f, 12.36f, 20.41f, 11.68f, 20.5f, 10.9f)
                curveTo(20.5f, 5.5f, 16.7f, 2.0f, 12.0f, 2.0f)
                curveTo(7.3f, 2.0f, 3.5f, 5.5f, 3.5f, 10.9f)
                curveTo(3.56f, 11.7f, 4.06f, 12.4f, 4.8f, 12.7f)
                lineTo(6.5f, 13.2f)
                verticalLineTo(15.5f)
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 6.0f,
                    y1 = 16.0f
                )
                arcTo(
                    0.5f, 0.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 5.5f,
                    y1 = 15.5f
                )
                curveTo(5.57f, 14.92f, 5.08f, 14.43f, 4.5f, 14.5f)
                curveTo(3.92f, 14.43f, 3.43f, 14.92f, 3.5f, 15.5f)
                curveTo(3.44f, 16.91f, 4.59f, 18.06f, 6.0f, 18.0f)
                curveTo(7.41f, 18.06f, 8.56f, 16.91f, 8.5f, 15.5f)
                verticalLineTo(13.7f)
                horizontalLineTo(9.5f)
                verticalLineTo(19.4f)
                curveTo(9.5f, 20.07f, 8.5f, 20.07f, 8.5f, 19.4f)
                curveTo(8.57f, 18.82f, 8.08f, 18.33f, 7.5f, 18.4f)
                curveTo(6.92f, 18.33f, 6.43f, 18.82f, 6.5f, 19.4f)
                curveTo(6.38f, 20.84f, 7.55f, 22.07f, 9.0f, 22.0f)
                curveTo(10.41f, 22.06f, 11.56f, 20.91f, 11.5f, 19.5f)
                verticalLineTo(14.0f)
                horizontalLineTo(12.5f)
                verticalLineTo(19.5f)
                curveTo(12.44f, 20.91f, 13.59f, 22.06f, 15.0f, 22.0f)
                curveTo(16.41f, 22.06f, 17.56f, 20.91f, 17.5f, 19.5f)
                curveTo(17.5f, 18.17f, 15.5f, 18.17f, 15.5f, 19.5f)
                curveTo(15.5f, 20.17f, 14.5f, 20.17f, 14.5f, 19.5f)
                verticalLineTo(13.8f)
                horizontalLineTo(15.5f)
                verticalLineTo(15.6f)
                curveTo(15.5f, 16.96f, 16.63f, 18.06f, 18.0f, 18.0f)
                curveTo(19.41f, 18.06f, 20.56f, 16.91f, 20.5f, 15.5f)
                curveTo(20.57f, 14.92f, 20.08f, 14.43f, 19.5f, 14.5f)
                moveTo(10.6f, 4.7f)
                curveTo(9.09f, 5.03f, 7.79f, 5.97f, 7.0f, 7.3f)
                curveTo(6.83f, 7.5f, 6.5f, 7.57f, 6.3f, 7.4f)
                curveTo(6.08f, 7.23f, 6.04f, 6.92f, 6.2f, 6.7f)
                curveTo(7.16f, 5.19f, 8.67f, 4.12f, 10.4f, 3.7f)
                curveTo(10.67f, 3.68f, 10.91f, 3.85f, 11.0f, 4.1f)
                curveTo(11.06f, 4.37f, 10.88f, 4.65f, 10.6f, 4.7f)
                close()
            }
        }
            .build()
        return _jellyfish!!
    }

private var _jellyfish: ImageVector? = null

val Icons.Filled.Mushroom: ImageVector
    get() {
        if (_mushroom != null) {
            return _mushroom!!
        }
        _mushroom = Builder(
            name = "Mushroom", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 2.0f)
                curveToRelative(5.5f, 0.0f, 10.0f, 4.5f, 10.0f, 10.0f)
                curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                curveTo(2.0f, 6.5f, 6.5f, 2.0f, 12.0f, 2.0f)
                moveTo(12.0f, 8.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveTo(10.9f, 8.0f, 12.0f, 8.0f)
                moveTo(17.0f, 12.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveTo(15.9f, 12.0f, 17.0f, 12.0f)
                moveTo(7.0f, 12.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveTo(8.1f, 8.0f, 7.0f, 8.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveTo(5.9f, 12.0f, 7.0f, 12.0f)
                moveTo(15.0f, 15.0f)
                lineToRelative(1.3f, 4.5f)
                lineToRelative(0.1f, 0.5f)
                curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
                horizontalLineTo(9.6f)
                curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                lineToRelative(0.1f, -0.5f)
                lineTo(9.0f, 15.0f)
                horizontalLineTo(15.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(15.5f, 13.1f)
                horizontalLineToRelative(-7.0f)
                curveToRelative(-2.5f, 0.0f, -4.6f, -2.0f, -4.6f, -4.6f)
                verticalLineToRelative(0.0f)
                curveTo(3.9f, 6.0f, 6.0f, 4.0f, 8.5f, 4.0f)
                horizontalLineToRelative(7.0f)
                curveToRelative(2.5f, 0.0f, 4.6f, 2.0f, 4.6f, 4.6f)
                verticalLineToRelative(0.0f)
                curveTo(20.1f, 11.1f, 18.0f, 13.1f, 15.5f, 13.1f)
                close()
            }
        }
            .build()
        return _mushroom!!
    }

private var _mushroom: ImageVector? = null

val Icons.Filled.Oil: ImageVector
    get() {
        if (_oil != null) {
            return _oil!!
        }
        _oil = Builder(
            name = "Oil", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth
            = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.0f, 13.0f)
                curveTo(20.55f, 13.0f, 21.0f, 12.55f, 21.0f, 12.0f)
                reflectiveCurveTo(20.55f, 11.0f, 20.0f, 11.0f)
                horizontalLineTo(19.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(20.0f)
                curveTo(20.55f, 5.0f, 21.0f, 4.55f, 21.0f, 4.0f)
                reflectiveCurveTo(20.55f, 3.0f, 20.0f, 3.0f)
                horizontalLineTo(4.0f)
                curveTo(3.45f, 3.0f, 3.0f, 3.45f, 3.0f, 4.0f)
                reflectiveCurveTo(3.45f, 5.0f, 4.0f, 5.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(4.0f)
                curveTo(3.45f, 11.0f, 3.0f, 11.45f, 3.0f, 12.0f)
                reflectiveCurveTo(3.45f, 13.0f, 4.0f, 13.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(4.0f)
                curveTo(3.45f, 19.0f, 3.0f, 19.45f, 3.0f, 20.0f)
                reflectiveCurveTo(3.45f, 21.0f, 4.0f, 21.0f)
                horizontalLineTo(20.0f)
                curveTo(20.55f, 21.0f, 21.0f, 20.55f, 21.0f, 20.0f)
                reflectiveCurveTo(20.55f, 19.0f, 20.0f, 19.0f)
                horizontalLineTo(19.0f)
                verticalLineTo(13.0f)
                horizontalLineTo(20.0f)
                moveTo(12.0f, 16.0f)
                curveTo(10.34f, 16.0f, 9.0f, 14.68f, 9.0f, 13.05f)
                curveTo(9.0f, 11.75f, 9.5f, 11.38f, 12.0f, 8.5f)
                curveTo(14.47f, 11.36f, 15.0f, 11.74f, 15.0f, 13.05f)
                curveTo(15.0f, 14.68f, 13.66f, 16.0f, 12.0f, 16.0f)
                close()
            }
        }
            .build()
        return _oil!!
    }

private var _oil: ImageVector? = null

val Icons.Filled.Peanut: ImageVector
    get() {
        if (_peanut != null) {
            return _peanut!!
        }
        _peanut = Builder(
            name = "Peanut", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.0f, 12.77f)
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 15.86f,
                    y1 = 10.12f
                )
                arcTo(
                    5.0f, 5.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 13.04f,
                    y1 = 2.12f
                )
                arcTo(
                    5.74f, 5.74f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 12.0f,
                    y1 = 2.0f
                )
                arcTo(
                    5.0f, 5.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 7.0f,
                    y1 = 7.0f
                )
                arcTo(
                    5.0f, 5.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 8.11f,
                    y1 = 10.12f
                )
                arcTo(
                    2.0f, 2.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 8.0f,
                    y1 = 12.72f
                )
                arcTo(
                    5.5f, 5.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 10.81f,
                    y1 = 21.87f
                )
                arcTo(
                    5.42f, 5.42f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 12.0f,
                    y1 = 22.0f
                )
                arcTo(
                    5.5f, 5.5f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 16.0f,
                    y1 = 12.77f
                )
                moveTo(13.0f, 5.0f)
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 6.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 13.0f,
                    y1 = 5.0f
                )
                moveTo(11.0f, 18.0f)
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 17.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 11.0f,
                    y1 = 18.0f
                )
                moveTo(12.0f, 15.0f)
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    x1 = 13.0f,
                    y1 = 16.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 12.0f,
                    y1 = 15.0f
                )
                moveTo(14.0f, 19.0f)
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    x1 = 15.0f,
                    y1 = 18.0f
                )
                arcTo(
                    1.0f, 1.0f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    x1 = 14.0f,
                    y1 = 19.0f
                )
                close()
            }
        }
            .build()
        return _peanut!!
    }

private var _peanut: ImageVector? = null

val Icons.Filled.Shaker: ImageVector
    get() {
        if (_shaker != null) {
            return _shaker!!
        }
        _shaker = Builder(
            name = "Shaker", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.0f, 16.0f)
                curveTo(7.0f, 16.55f, 6.55f, 17.0f, 6.0f, 17.0f)
                reflectiveCurveTo(5.0f, 16.55f, 5.0f, 16.0f)
                curveTo(5.0f, 15.45f, 5.45f, 15.0f, 6.0f, 15.0f)
                reflectiveCurveTo(7.0f, 15.45f, 7.0f, 16.0f)
                moveTo(9.0f, 16.0f)
                curveTo(8.45f, 16.0f, 8.0f, 16.45f, 8.0f, 17.0f)
                reflectiveCurveTo(8.45f, 18.0f, 9.0f, 18.0f)
                reflectiveCurveTo(10.0f, 17.55f, 10.0f, 17.0f)
                reflectiveCurveTo(9.55f, 16.0f, 9.0f, 16.0f)
                moveTo(4.0f, 18.0f)
                curveTo(3.45f, 18.0f, 3.0f, 18.45f, 3.0f, 19.0f)
                reflectiveCurveTo(3.45f, 20.0f, 4.0f, 20.0f)
                reflectiveCurveTo(5.0f, 19.55f, 5.0f, 19.0f)
                reflectiveCurveTo(4.55f, 18.0f, 4.0f, 18.0f)
                moveTo(7.0f, 19.0f)
                curveTo(6.45f, 19.0f, 6.0f, 19.45f, 6.0f, 20.0f)
                reflectiveCurveTo(6.45f, 21.0f, 7.0f, 21.0f)
                reflectiveCurveTo(8.0f, 20.55f, 8.0f, 20.0f)
                reflectiveCurveTo(7.55f, 19.0f, 7.0f, 19.0f)
                moveTo(15.33f, 2.72f)
                lineTo(9.8f, 9.65f)
                lineTo(13.34f, 13.19f)
                lineTo(20.28f, 7.67f)
                curveTo(21.18f, 6.91f, 21.25f, 5.54f, 20.41f, 4.7f)
                lineTo(18.3f, 2.59f)
                curveTo(17.46f, 1.75f, 16.09f, 1.82f, 15.33f, 2.72f)
                moveTo(8.39f, 12.5f)
                lineTo(10.5f, 14.6f)
                curveTo(10.9f, 15.0f, 11.54f, 15.0f, 11.93f, 14.6f)
                lineTo(12.63f, 13.9f)
                lineTo(9.1f, 10.36f)
                lineTo(8.39f, 11.07f)
                curveTo(8.0f, 11.46f, 8.0f, 12.09f, 8.39f, 12.5f)
                close()
            }
        }
            .build()
        return _shaker!!
    }

private var _shaker: ImageVector? = null

val Icons.Filled.Steak: ImageVector
    get() {
        if (_steak != null) {
            return _steak!!
        }
        _steak = Builder(
            name = "Steak", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.9f, 2.0f)
                curveTo(8.0f, 2.0f, 4.9f, 5.4f, 5.0f, 9.3f)
                curveTo(5.1f, 11.9f, 6.6f, 14.1f, 8.7f, 15.2f)
                curveTo(10.1f, 15.9f, 11.0f, 17.3f, 11.0f, 18.8f)
                verticalLineTo(19.0f)
                curveTo(11.0f, 20.7f, 12.3f, 22.0f, 14.0f, 22.0f)
                curveTo(18.0f, 22.0f, 19.0f, 17.0f, 19.0f, 9.0f)
                curveTo(19.0f, 9.0f, 19.0f, 2.0f, 11.9f, 2.0f)
                moveTo(14.0f, 20.0f)
                curveTo(13.4f, 20.0f, 13.0f, 19.6f, 13.0f, 19.0f)
                verticalLineTo(18.8f)
                curveTo(13.0f, 16.6f, 11.7f, 14.5f, 9.7f, 13.4f)
                curveTo(8.1f, 12.6f, 7.1f, 11.0f, 7.0f, 9.2f)
                curveTo(7.0f, 7.9f, 7.5f, 6.5f, 8.4f, 5.5f)
                curveTo(9.3f, 4.5f, 10.6f, 4.0f, 11.8f, 4.0f)
                curveTo(16.7f, 4.0f, 17.0f, 8.2f, 17.0f, 9.0f)
                curveTo(17.0f, 18.9f, 15.3f, 20.0f, 14.0f, 20.0f)
                moveTo(15.8f, 7.6f)
                lineTo(8.3f, 10.3f)
                curveTo(8.1f, 10.0f, 8.0f, 9.6f, 8.0f, 9.1f)
                curveTo(8.0f, 8.4f, 8.2f, 7.8f, 8.5f, 7.1f)
                lineTo(13.7f, 5.2f)
                curveTo(14.9f, 5.8f, 15.5f, 6.7f, 15.8f, 7.6f)
                moveTo(12.9f, 15.1f)
                lineTo(15.7f, 14.1f)
                curveTo(15.6f, 15.6f, 15.3f, 16.7f, 15.1f, 17.4f)
                lineTo(13.8f, 17.9f)
                curveTo(13.8f, 16.9f, 13.5f, 16.0f, 12.9f, 15.1f)
                moveTo(16.0f, 9.2f)
                curveTo(16.0f, 10.4f, 16.0f, 11.5f, 15.9f, 12.4f)
                lineTo(11.9f, 13.9f)
                curveTo(11.4f, 13.4f, 10.8f, 12.9f, 10.1f, 12.6f)
                curveTo(9.7f, 12.4f, 9.3f, 12.1f, 9.0f, 11.8f)
                lineTo(16.0f, 9.2f)
                close()
            }
        }
            .build()
        return _steak!!
    }

private var _steak: ImageVector? = null

val Icons.Filled.BorderRadius: ImageVector
    get() {
        if (_borderradius != null) {
            return _borderradius!!
        }
        _borderradius = Builder(
            name = "Borderradius", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(3.0f, 16.0f)
                curveTo(3.0f, 18.8f, 5.2f, 21.0f, 8.0f, 21.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(8.0f)
                curveTo(6.3f, 19.0f, 5.0f, 17.7f, 5.0f, 16.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(3.0f)
                verticalLineTo(16.0f)
                moveTo(21.0f, 8.0f)
                curveTo(21.0f, 5.2f, 18.8f, 3.0f, 16.0f, 3.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(16.0f)
                curveTo(17.7f, 5.0f, 19.0f, 6.3f, 19.0f, 8.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(21.0f)
                verticalLineTo(8.0f)
                moveTo(16.0f, 21.0f)
                curveTo(18.8f, 21.0f, 21.0f, 18.8f, 21.0f, 16.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(19.0f)
                verticalLineTo(16.0f)
                curveTo(19.0f, 17.7f, 17.7f, 19.0f, 16.0f, 19.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(16.0f)
                moveTo(8.0f, 3.0f)
                curveTo(5.2f, 3.0f, 3.0f, 5.2f, 3.0f, 8.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(8.0f)
                curveTo(5.0f, 6.3f, 6.3f, 5.0f, 8.0f, 5.0f)
                horizontalLineTo(10.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(8.0f)
                close()
            }
        }
            .build()
        return _borderradius!!
    }

private var _borderradius: ImageVector? = null

val Icons.Filled.Milk: ImageVector
    get() {
        if (_milk != null) {
            return _milk!!
        }
        _milk = Builder(
            name = "Milk", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF030303)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.2f, 7.2f)
                curveToRelative(0.3f, 0.3f, 0.5f, 0.6f, 0.7f, 0.8f)
                curveToRelative(0.1f, 0.1f, 0.0f, 0.2f, 0.0f, 0.3f)
                curveToRelative(-0.5f, 0.5f, -0.9f, 1.0f, -1.4f, 1.4f)
                curveToRelative(-0.1f, 0.1f, -0.2f, 0.3f, -0.2f, 0.5f)
                curveToRelative(0.0f, 2.9f, 0.0f, 5.8f, 0.0f, 8.7f)
                curveToRelative(0.0f, 1.3f, -0.7f, 2.2f, -1.8f, 2.5f)
                curveToRelative(-0.2f, 0.0f, -0.4f, 0.0f, -0.6f, 0.0f)
                curveToRelative(-2.6f, 0.0f, -5.3f, 0.0f, -7.9f, 0.0f)
                curveToRelative(-1.5f, 0.0f, -2.4f, -1.0f, -2.4f, -2.5f)
                curveToRelative(0.0f, -2.9f, 0.0f, -5.8f, 0.0f, -8.7f)
                curveToRelative(0.0f, -0.2f, -0.1f, -0.4f, -0.2f, -0.5f)
                curveTo(5.0f, 9.2f, 4.6f, 8.8f, 4.1f, 8.3f)
                curveTo(4.0f, 8.2f, 4.0f, 8.1f, 4.1f, 7.9f)
                curveToRelative(0.2f, -0.2f, 0.4f, -0.6f, 0.7f, -0.6f)
                curveToRelative(0.2f, 0.0f, 0.4f, 0.4f, 0.7f, 0.6f)
                curveTo(5.6f, 8.1f, 5.8f, 8.3f, 6.0f, 8.5f)
                curveToRelative(0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.0f)
                curveToRelative(0.5f, -1.0f, 1.0f, -1.9f, 1.6f, -2.9f)
                curveToRelative(0.1f, -0.1f, 0.0f, -0.2f, -0.1f, -0.2f)
                curveTo(7.3f, 4.9f, 7.2f, 4.4f, 7.3f, 3.9f)
                curveToRelative(0.2f, -0.5f, 0.6f, -0.8f, 1.2f, -0.8f)
                curveToRelative(2.3f, 0.0f, 4.6f, 0.0f, 6.9f, 0.0f)
                curveToRelative(0.6f, 0.0f, 1.0f, 0.3f, 1.2f, 0.8f)
                curveToRelative(0.2f, 0.5f, 0.1f, 1.0f, -0.4f, 1.4f)
                curveToRelative(-0.1f, 0.1f, -0.1f, 0.2f, -0.1f, 0.3f)
                curveToRelative(0.5f, 0.9f, 1.0f, 1.9f, 1.5f, 2.8f)
                curveToRelative(0.1f, 0.2f, 0.2f, 0.1f, 0.3f, 0.0f)
                curveTo(18.4f, 8.0f, 18.8f, 7.6f, 19.2f, 7.2f)
                close()
                moveTo(12.0f, 10.3f)
                curveToRelative(-1.6f, 0.0f, -3.2f, 0.0f, -4.8f, 0.0f)
                curveToRelative(-0.2f, 0.0f, -0.3f, 0.1f, -0.3f, 0.3f)
                curveToRelative(0.0f, 2.8f, 0.0f, 5.5f, 0.0f, 8.3f)
                curveToRelative(0.0f, 0.8f, 0.4f, 1.3f, 1.2f, 1.3f)
                curveToRelative(1.0f, 0.0f, 1.9f, 0.0f, 2.9f, 0.0f)
                curveToRelative(1.7f, 0.0f, 3.3f, 0.0f, 5.0f, 0.0f)
                curveToRelative(0.8f, 0.0f, 1.2f, -0.4f, 1.2f, -1.3f)
                curveToRelative(0.0f, -2.8f, 0.0f, -5.5f, 0.0f, -8.3f)
                curveToRelative(0.0f, -0.3f, -0.1f, -0.3f, -0.3f, -0.3f)
                curveTo(15.2f, 10.3f, 13.6f, 10.3f, 12.0f, 10.3f)
                close()
                moveTo(16.7f, 9.0f)
                curveToRelative(0.0f, -0.1f, 0.0f, -0.1f, 0.0f, -0.1f)
                curveToRelative(-0.6f, -1.0f, -1.1f, -2.1f, -1.7f, -3.1f)
                curveToRelative(-0.1f, -0.2f, -0.2f, -0.2f, -0.3f, -0.2f)
                curveToRelative(-1.7f, 0.0f, -3.5f, 0.0f, -5.2f, 0.0f)
                curveTo(9.2f, 5.6f, 9.1f, 5.7f, 9.0f, 5.8f)
                curveTo(8.6f, 6.7f, 8.1f, 7.5f, 7.7f, 8.3f)
                curveTo(7.5f, 8.5f, 7.4f, 8.8f, 7.3f, 9.0f)
                curveTo(10.4f, 9.0f, 13.5f, 9.0f, 16.7f, 9.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF030303)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 13.3f)
                curveToRelative(1.2f, 0.0f, 2.3f, 0.0f, 3.5f, 0.0f)
                curveToRelative(0.2f, 0.0f, 0.3f, 0.1f, 0.3f, 0.3f)
                curveToRelative(-0.1f, 0.6f, -0.2f, 1.3f, -0.6f, 1.8f)
                curveToRelative(-0.1f, 0.2f, -0.1f, 0.3f, 0.0f, 0.4f)
                curveToRelative(0.1f, 0.1f, 0.2f, 0.2f, 0.2f, 0.3f)
                curveToRelative(0.2f, 0.4f, 0.2f, 0.8f, -0.1f, 1.0f)
                curveToRelative(-0.3f, 0.2f, -0.8f, 0.2f, -1.0f, -0.1f)
                curveToRelative(-0.2f, -0.3f, -0.4f, -0.3f, -0.7f, -0.1f)
                curveToRelative(-0.2f, 0.2f, -0.5f, 0.2f, -0.7f, 0.3f)
                curveToRelative(-0.2f, 0.0f, -0.2f, 0.1f, -0.2f, 0.3f)
                curveToRelative(0.0f, 0.1f, 0.0f, 0.2f, 0.0f, 0.3f)
                curveToRelative(0.0f, 0.4f, -0.3f, 0.7f, -0.7f, 0.7f)
                curveToRelative(-0.4f, 0.0f, -0.7f, -0.3f, -0.7f, -0.7f)
                curveToRelative(0.0f, -0.1f, 0.0f, -0.1f, 0.0f, -0.2f)
                curveToRelative(0.0f, -0.2f, 0.0f, -0.3f, -0.2f, -0.3f)
                curveToRelative(-0.3f, -0.1f, -0.6f, -0.2f, -0.9f, -0.4f)
                curveToRelative(-0.1f, -0.1f, -0.2f, -0.1f, -0.3f, 0.1f)
                curveToRelative(-0.1f, 0.2f, -0.3f, 0.4f, -0.5f, 0.5f)
                curveToRelative(-0.3f, 0.1f, -0.6f, 0.0f, -0.8f, -0.3f)
                curveToRelative(-0.2f, -0.3f, -0.2f, -0.6f, 0.0f, -0.8f)
                curveToRelative(0.4f, -0.4f, 0.3f, -0.7f, 0.1f, -1.2f)
                curveToRelative(-0.2f, -0.4f, -0.3f, -0.9f, -0.4f, -1.3f)
                curveToRelative(0.0f, -0.2f, 0.0f, -0.3f, 0.3f, -0.3f)
                curveTo(9.7f, 13.3f, 10.8f, 13.3f, 12.0f, 13.3f)
                close()
            }
        }
            .build()
        return _milk!!
    }

private var _milk: ImageVector? = null

val Icons.Filled.DriedGrape: ImageVector
    get() {
        if (_driedGrape != null) {
            return _driedGrape!!
        }
        _driedGrape = Builder(
            name = "DriedGrape", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF010101)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.9f, 19.9f)
                curveToRelative(-1.1f, 0.0f, -2.1f, -0.4f, -3.2f, -0.9f)
                curveToRelative(-0.2f, 0.0f, -0.2f, 0.0f, -0.4f, 0.0f)
                curveToRelative(-0.5f, 0.2f, -1.1f, 0.4f, -1.6f, 0.4f)
                curveToRelative(-0.4f, 0.0f, -0.5f, -0.2f, -0.9f, -0.4f)
                curveToRelative(-1.1f, -0.7f, -1.6f, -1.5f, -2.1f, -2.8f)
                curveToRelative(-0.2f, -0.4f, -0.2f, -0.7f, 0.0f, -1.3f)
                curveToRelative(0.0f, -0.4f, 0.2f, -0.9f, 0.2f, -1.3f)
                curveToRelative(0.0f, -0.2f, 0.0f, -0.2f, 0.0f, -0.4f)
                curveToRelative(-0.4f, -0.4f, -0.5f, -0.9f, -0.5f, -1.5f)
                reflectiveCurveToRelative(0.2f, -1.1f, 0.5f, -1.5f)
                curveToRelative(0.4f, -0.4f, 0.7f, -0.7f, 1.2f, -1.1f)
                curveToRelative(0.2f, 0.0f, 0.2f, -0.2f, 0.4f, -0.4f)
                curveToRelative(0.9f, -1.3f, 1.8f, -2.6f, 3.2f, -3.4f)
                curveToRelative(0.5f, 0.0f, 1.2f, -0.4f, 1.9f, -0.4f)
                curveToRelative(0.0f, 0.0f, 0.2f, 0.0f, 0.2f, -0.2f)
                lineTo(11.0f, 4.6f)
                curveToRelative(1.6f, -0.9f, 3.4f, -0.9f, 5.1f, -0.2f)
                curveToRelative(0.7f, 0.2f, 1.2f, 0.7f, 1.6f, 1.1f)
                curveToRelative(0.0f, 0.0f, 0.2f, 0.0f, 0.2f, 0.2f)
                curveToRelative(1.4f, 0.4f, 2.1f, 1.5f, 2.5f, 2.8f)
                curveToRelative(0.4f, 1.5f, 0.2f, 2.8f, -0.4f, 4.1f)
                curveTo(19.5f, 13.9f, 19.0f, 15.0f, 17.9f, 16.0f)
                curveToRelative(-0.4f, 0.4f, -0.7f, 0.6f, -1.2f, 0.7f)
                curveToRelative(0.0f, 0.0f, -0.2f, 0.0f, -0.2f, 0.2f)
                curveToRelative(-0.5f, 1.3f, -1.4f, 2.2f, -2.8f, 2.6f)
                curveToRelative(-0.4f, 0.2f, -0.7f, 0.2f, -1.1f, 0.2f)
                curveTo(12.4f, 19.9f, 12.3f, 19.9f, 11.9f, 19.9f)
                close()
                moveTo(7.3f, 15.2f)
                curveTo(7.3f, 15.2f, 7.5f, 15.2f, 7.3f, 15.2f)
                curveToRelative(0.9f, -0.2f, 1.6f, -0.6f, 2.1f, -0.9f)
                curveToRelative(1.2f, -0.7f, 1.9f, -1.9f, 2.7f, -3.2f)
                curveToRelative(0.4f, -0.7f, 0.9f, -1.5f, 1.6f, -2.1f)
                reflectiveCurveToRelative(1.6f, -1.1f, 2.5f, -1.3f)
                curveToRelative(0.2f, 0.0f, 0.4f, -0.2f, 0.4f, -0.4f)
                curveToRelative(0.0f, -0.2f, 0.0f, -0.4f, -0.2f, -0.6f)
                curveToRelative(-0.2f, -0.2f, -0.4f, -0.2f, -0.5f, 0.0f)
                curveToRelative(-1.6f, 0.4f, -2.8f, 1.3f, -3.9f, 2.6f)
                curveToRelative(-0.4f, 0.6f, -0.7f, 1.1f, -1.1f, 1.7f)
                curveToRelative(-0.9f, 1.5f, -2.1f, 2.6f, -3.9f, 3.0f)
                curveToRelative(-0.2f, 0.0f, -0.4f, 0.4f, -0.4f, 0.6f)
                curveTo(6.8f, 15.0f, 6.9f, 15.2f, 7.3f, 15.2f)
                close()
                moveTo(12.1f, 16.3f)
                curveToRelative(0.7f, 0.0f, 1.2f, -0.4f, 1.8f, -0.9f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.6f, 0.0f, -0.7f)
                curveToRelative(-0.2f, -0.2f, -0.4f, -0.2f, -0.7f, 0.0f)
                curveToRelative(-0.2f, 0.2f, -0.5f, 0.4f, -0.9f, 0.6f)
                curveToRelative(-0.2f, 0.0f, -0.2f, 0.0f, -0.4f, 0.0f)
                curveToRelative(-0.2f, -0.2f, -0.5f, 0.0f, -0.5f, 0.2f)
                curveToRelative(-0.2f, 0.2f, 0.0f, 0.6f, 0.2f, 0.7f)
                curveTo(11.7f, 16.1f, 11.9f, 16.3f, 12.1f, 16.3f)
                close()
                moveTo(10.5f, 8.9f)
                curveToRelative(0.2f, 0.0f, 0.4f, 0.0f, 0.5f, -0.2f)
                curveToRelative(0.5f, -0.2f, 0.9f, -0.7f, 1.2f, -1.1f)
                curveToRelative(0.2f, -0.2f, 0.2f, -0.6f, 0.0f, -0.7f)
                curveToRelative(-0.2f, -0.2f, -0.5f, -0.2f, -0.7f, 0.0f)
                curveToRelative(-0.2f, 0.4f, -0.5f, 0.6f, -0.9f, 0.7f)
                curveToRelative(0.0f, 0.0f, -0.2f, 0.2f, -0.4f, 0.2f)
                curveToRelative(-0.4f, 0.0f, -0.5f, 0.2f, -0.5f, 0.6f)
                curveTo(10.0f, 8.7f, 10.3f, 8.9f, 10.5f, 8.9f)
                close()
                moveTo(8.5f, 10.0f)
                horizontalLineTo(8.4f)
                curveToRelative(-0.7f, 0.2f, -1.2f, 0.6f, -1.2f, 1.3f)
                curveToRelative(0.0f, 0.4f, 0.2f, 0.6f, 0.4f, 0.6f)
                curveToRelative(0.4f, 0.0f, 0.5f, -0.2f, 0.5f, -0.4f)
                curveToRelative(0.0f, -0.2f, 0.2f, -0.4f, 0.4f, -0.4f)
                curveToRelative(0.2f, 0.0f, 0.2f, 0.0f, 0.4f, -0.2f)
                curveToRelative(0.4f, 0.0f, 0.5f, -0.2f, 0.5f, -0.6f)
                curveTo(9.1f, 10.2f, 8.9f, 10.0f, 8.5f, 10.0f)
                close()
                moveTo(14.7f, 12.2f)
                curveToRelative(0.2f, 0.0f, 0.4f, -0.2f, 0.4f, -0.6f)
                curveToRelative(0.0f, -0.2f, 0.0f, -0.4f, 0.0f, -0.4f)
                curveToRelative(0.0f, -0.2f, 0.0f, -0.2f, 0.2f, -0.4f)
                curveToRelative(0.2f, 0.0f, 0.4f, -0.4f, 0.4f, -0.6f)
                curveToRelative(0.0f, -0.4f, -0.4f, -0.4f, -0.5f, -0.4f)
                curveToRelative(-0.4f, 0.2f, -0.7f, 0.4f, -0.9f, 0.7f)
                curveToRelative(-0.2f, 0.4f, 0.0f, 0.6f, 0.0f, 0.9f)
                curveTo(14.2f, 12.0f, 14.6f, 12.2f, 14.7f, 12.2f)
                close()
            }
        }
            .build()
        return _driedGrape!!
    }

private var _driedGrape: ImageVector? = null