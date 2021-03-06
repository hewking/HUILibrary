package com.hewking.uikit.bizier

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.hewking.uikit.BuildConfig
import com.hewking.uikit.R
import com.hewking.utils.DrawHelper
import com.hewking.utils.dp2px
import com.hewking.utils.getColor

/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2019/1/6
 * 修改人员：hewking
 * 修改时间：2019/1/6
 * 修改备注：
 * Version: 1.0.0
 */
class BezierView3(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private val defaultHeight = dp2px(60f)

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = getColor(R.color.app_color_theme_6)
            strokeWidth = dp2px(1f).toFloat()
        }
    }

    private var offset = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paint.shader = LinearGradient(0f, 0f, w.toFloat().div(4), 0f
                , intArrayOf(getColor(R.color.app_color_theme_4), getColor(R.color.pink_f5b8c2), getColor(R.color.app_color_theme_6))
                , floatArrayOf(0.1f, 0.8f, 0.9f), Shader.TileMode.CLAMP)


        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                offset = it.animatedValue as Float
                postInvalidateOnAnimation()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationRepeat(animation: Animator?) {
                    super.onAnimationRepeat(animation)
                }
            })
            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(defaultHeight, MeasureSpec.EXACTLY))
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        if (BuildConfig.DEBUG) {
            DrawHelper.drawCoordinate(canvas, width, height)
        }

        canvas.translate(width.div(2f), height.div(2f))
        val waveWidth = width.div(4f)
        val waveHeight = defaultHeight.div(2f)

        val path = Path()
        path.reset()

        val start = -4 * waveWidth + 2 * offset * waveWidth
        path.moveTo(start, 0f)

        // 绘制2段完整波形
        for (i in 0 until 4) {
            path.quadTo(start + (4 * i + 1) * 0.5f * waveWidth, waveHeight
                    , start + (2 * i + 1) * waveWidth, 0f)

            path.quadTo(start + (4 * i + 3) * 0.5f * waveWidth, -waveHeight
                    , start + (i + 1) * 2 * waveWidth, 0f)
        }

        canvas.drawPath(path, paint)
    }

}