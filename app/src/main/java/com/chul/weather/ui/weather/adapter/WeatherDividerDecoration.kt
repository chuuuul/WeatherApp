package com.chul.weather.ui.weather.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chul.weather.data.model.WeatherAdapterModel


class WeatherDividerDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    private val paint1dp = Paint()
    private val paint2dp = Paint()


    init {
        paint1dp.style = Paint.Style.STROKE
        paint1dp.color = Color.GRAY
        paint1dp.strokeWidth = dpToPx(1)

        paint2dp.style = Paint.Style.STROKE
        paint2dp.color = Color.BLACK
        paint2dp.strokeWidth = dpToPx(2)
    }

    private fun dpToPx(dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (index in 0 until parent.childCount) {
            val view = parent.getChildAt(index)
            val position = parent.getChildAdapterPosition(view)
            val viewType = parent.adapter?.getItemViewType(position)
            if (viewType == WeatherAdapterModel.category) {
                drawTop(view, canvas, paint2dp)
                drawBottom(view, canvas, paint2dp)
            } else {
                drawBottom(view, canvas, paint1dp)
            }
        }
    }

    private fun drawBottom(view: View, canvas: Canvas, paint: Paint) {
        canvas.drawLine(
            view.left.toFloat(),
            view.bottom.toFloat(),
            view.right.toFloat(),
            view.bottom.toFloat(),
            paint
        )
    }

    private fun drawTop(view: View, canvas: Canvas, paint: Paint) {
        canvas.drawLine(
            view.left.toFloat(),
            view.top.toFloat(),
            view.right.toFloat(),
            view.top.toFloat(),
            paint
        )
    }
}