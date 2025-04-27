package ru.effectivem.a4androidsdk.z3customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.effectivem.a4androidsdk.R
import java.util.Random

class RandomProgressRectangle @JvmOverloads constructor(
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
            getContext().obtainStyledAttributes(attrs, R.styleable.RandomProgressRectangle)

        backgroundPaint.color =
            typedArray.getColor(R.styleable.RandomProgressRectangle_background_color, Color.WHITE)
        oneStepValue =
            typedArray.getInteger(R.styleable.RandomProgressRectangle_one_step_value, 10)

        typedArray.recycle()

        setOnClickListener {
            setProgress()
            if (progress != 0) {
                fillPaint.color = getRandomColor()
            }
            invalidate()
        }
    }

    private fun setProgress() {
        progress += oneStepValue
        if (progress > 100) {
            progress = 0
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        val filledWidth = width * (progress / 100f)
        canvas.drawRect(0f, 0f, filledWidth, height.toFloat(), fillPaint)
    }

}