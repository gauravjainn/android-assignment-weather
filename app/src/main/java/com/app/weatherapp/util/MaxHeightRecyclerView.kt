package com.app.weatherapp.util

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.R

class MaxHeightRecyclerView : RecyclerView {
    private var maxHeight = 0
    private val defaultHeight = 200

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (!isInEditMode) {
            init(context, attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        if (!isInEditMode) {
            init(context, attrs)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val styledAttrs =
                context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
            //200 is a defualt value
            maxHeight = styledAttrs.getDimensionPixelSize(
                R.styleable.MaxHeightRecyclerView_rvMaxHeight,
                defaultHeight
            )
            styledAttrs.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}