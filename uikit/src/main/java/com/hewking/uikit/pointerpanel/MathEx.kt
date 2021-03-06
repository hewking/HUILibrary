package com.hewking.pointerpanel

import android.graphics.PointF
import kotlin.math.*

/**
 *
 * @author hewking
 * @date 2019/12/28
 */

/**计算两点之间的距离*/
fun distance(point1: PointF, point2: PointF): Double {
    return c(point1.x, point1.y, point2.x, point2.y)
}

/**勾股定理 C边的长度*/
fun c(x1: Float, y1: Float, x2: Float, y2: Float): Double {
    val a = (x2 - x1).absoluteValue
    val b = (y2 - y1).absoluteValue
    return sqrt(a.toDouble().pow(2.0) + b.toDouble().pow(2.0))
}

/**根据半径[radius],原点[pivotX,pivotY]坐标, 计算出角度[degrees]对应的圆上坐标点*/
fun dotDegrees(radius: Float, degrees: Int, pivotX: Int, pivotY: Int): PointF {
    val x = pivotX + radius * cos(degrees * Math.PI / 180)
    val y = pivotY + radius * sin(degrees * Math.PI / 180)
    return PointF(x.toFloat(), y.toFloat())
}

/**弧度[radians]*/
fun dotRadians(radius: Float, radians: Float, pivotX: Int, pivotY: Int): PointF {
    val x = pivotX + radius * cos(radians)
    val y = pivotY + radius * sin(radians)
    return PointF(x, y)
}

/**角的度数。[0-360]*/
fun degrees(x: Float, y: Float, pivotX: Float, pivotY: Float): Int {
    return Math.toDegrees(radians(x, y, pivotX, pivotY)).toInt()
}

/**
 * 以弧度表示的角度的测量。[0-2PI]
 *
 * 计算点 (x,y) 相对于原点 (pivotX,pivotY) 的弧度
 * */
fun radians(x: Float, y: Float, pivotX: Float, pivotY: Float): Double {

    val deltaX = x - pivotX
    val deltaY = y - pivotY

    val radians = atan2(deltaY.toDouble(), deltaX.toDouble())

    return if (radians < 0) {
        2 * Math.PI + radians
    } else {
        radians
    }
}

/**
 * 保留浮点数, 小数点后几位 .x0 -> .x
 * @param digit 需要保留的小数位数
 * @param fadedUp 是否四舍五入
 * */
fun Double.decimal(digit: Int = 2, fadedUp: Boolean = false): Float {
    val f = 10f.pow(digit)
    return if (fadedUp) {
        (this * f).roundToInt()
    } else {
        (this * f).toInt()
    } / f
}

fun Float.decimal(digit: Int = 2, fadedUp: Boolean = false): Float {
    return this.toDouble().decimal(digit, fadedUp)
}