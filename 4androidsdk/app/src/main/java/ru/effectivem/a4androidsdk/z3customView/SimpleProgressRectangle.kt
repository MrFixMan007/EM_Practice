package ru.effectivem.a4androidsdk.z3customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.effectivem.a4androidsdk.R

class SimpleProgressRectangle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(
    context,
    attrs,
    defStyleAttr,
) {

    private var progress = 0
    private val oneStepValue: Int

    private val fillPaint = Paint()
    private val backgroundPaint = Paint()

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.SimpleProgressRectangle)

        backgroundPaint.color =
            typedArray.getColor(R.styleable.SimpleProgressRectangle_background_color, Color.WHITE)
        oneStepValue =
            typedArray.getInteger(R.styleable.SimpleProgressRectangle_one_step_value, 10)
        fillPaint.color =
            typedArray.getColor(R.styleable.SimpleProgressRectangle_fill_color, Color.RED)

        typedArray.recycle()

        setOnClickListener {
            setProgress()
            invalidate()
        }
    }

    private fun setProgress() {
        progress += oneStepValue
        if (progress > 100) {
            progress = 0
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        val filledWidth = width * (progress / 100f)
        canvas.drawRect(0f, 0f, filledWidth, height.toFloat(), fillPaint)
    }

}