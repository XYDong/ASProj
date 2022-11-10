package com.joker.common.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.joker.common.R

open class InputItemLayout : LinearLayout {


    // 上下分割线的style
    private var bottomLineStyle: LineStyle
    private var topLineStyle: LineStyle

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        // 设置此LinearLayout 的方向
        this.orientation = HORIZONTAL

        val array =
            context.obtainStyledAttributes(attributeSet, R.styleable.InputItemLayout)
        val titleStyleId = array.getResourceId(R.styleable.InputItemLayout_titleTextAppearance, 0)
        val title = array.getString(R.styleable.InputItemLayout_title)
        // 解析title资源
        parseTitleStyle(titleStyleId, title)

        val inputStyleId = array.getResourceId(R.styleable.InputItemLayout_inputType, 0)
        val hint = array.getResourceId(R.styleable.InputItemLayout_hint, 0)
        val inputType = array.getResourceId(R.styleable.InputItemLayout_inputType, 0)
        // 解析input资源
        parseInputStyle(inputStyleId, hint, inputType)

        val topLineStyleId = array.getResourceId(R.styleable.InputItemLayout_topLineAppearance, 0)
        val bottomLineStyleId =
            array.getResourceId(R.styleable.InputItemLayout_bottomLineAppearance, 0)
        // 解析上下分割线的资源
        topLineStyle = parseLineStyle(topLineStyleId)
        bottomLineStyle = parseLineStyle(bottomLineStyleId)
        if (topLineStyle.enable) {
            topPaint.setColor(topLineStyle.color)
            topPaint.style = Paint.Style.FILL_AND_STROKE //实线
            topPaint.strokeWidth = topLineStyle.height.toFloat()
        }
        if (bottomLineStyle.enable) {
            bottomPaint.setColor(bottomLineStyle.color)
            bottomPaint.style = Paint.Style.FILL_AND_STROKE //实线
            bottomPaint.strokeWidth = bottomLineStyle.height.toFloat()
        }
        // 解析完成后回收array
        array.recycle()
    }

    private fun parseLineStyle(resId: Int): LineStyle {
        val lineStyle = LineStyle()
        val array = context.obtainStyledAttributes(resId, R.styleable.lineAppearance)
        lineStyle.color = array.getColor(
            R.styleable.lineAppearance_color,
            resources.getColor(org.devio.hi.ui.R.color.color_d1d2)
        )
        lineStyle.height = array.getDimensionPixelOffset(R.styleable.lineAppearance_height, 0)
        lineStyle.leftMargin =
            array.getDimensionPixelOffset(R.styleable.lineAppearance_leftMargin, 0)
        lineStyle.rightMargin =
            array.getDimensionPixelOffset(R.styleable.lineAppearance_rightMargin, 0)
        lineStyle.enable = array.getBoolean(R.styleable.lineAppearance_enable, false)
        array.recycle()
        return lineStyle
    }

    /**
     * 上线分割线样式
     */
    data class LineStyle(
        var color: Int = 0,
        var height: Int = 0,
        var leftMargin: Int = 0,
        var rightMargin: Int = 0,
        var enable: Boolean = false
    )

    private fun parseInputStyle(resId: Int, hint: Int, inputType: Int) {
        val array =
            context.obtainStyledAttributes(resId, R.styleable.inputTextAppearance)
        val inputColor = array.getColor(
            R.styleable.inputTextAppearance_inputColor,
            resources.getColor(org.devio.hi.ui.R.color.color_565)
        )
        val hintColor = array.getColor(
            R.styleable.inputTextAppearance_hintColor,
            resources.getColor(org.devio.hi.ui.R.color.color_565)
        )
        val textSize = array.getDimensionPixelSize(
            R.styleable.inputTextAppearance_textSize,
            applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f)
        )
        val editText = EditText(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.weight = 1f
        editText.layoutParams = params
        editText.setHint(hint)
        editText.setHintTextColor(hintColor)
        editText.setTextColor(inputColor)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat()) // 告诉控件，已经转成了px值了
        editText.setBackgroundColor(Color.TRANSPARENT) // 去掉默认下划线
        editText.gravity = Gravity.LEFT and (Gravity.CENTER)
        editText.inputType = when (inputType) {
            0 -> InputType.TYPE_TEXT_VARIATION_NORMAL
            1 -> InputType.TYPE_TEXT_VARIATION_PASSWORD
            2 -> InputType.TYPE_NUMBER_VARIATION_NORMAL
            else -> InputType.TYPE_TEXT_VARIATION_NORMAL
        }
        array.recycle()
        addView(editText)
    }

    private fun parseTitleStyle(resId: Int, title: String?) {
        val array = context.obtainStyledAttributes(resId, R.styleable.titleTextAppearance)
        val titleColor = array.getColor(
            R.styleable.titleTextAppearance_titleColor,
            resources.getColor(org.devio.hi.ui.R.color.color_565)
        )
        val titleSize = array.getDimensionPixelSize(
            R.styleable.titleTextAppearance_titleColor,
            applyUnit(TypedValue.COMPLEX_UNIT_SP, 16f)
        )
        val minWidth = array.getDimensionPixelSize(
            R.styleable.titleTextAppearance_minWidth,
            0
        )

        var textView = TextView(context)
        textView.setTextColor(titleColor)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize.toFloat())
        textView.minWidth = minWidth
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.gravity = Gravity.LEFT and (Gravity.CENTER)
        textView.layoutParams = params
        textView.text = title
        array.recycle()
        addView(textView)
    }

    /**
     * dp 转px
     */
    private fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, resources.displayMetrics).toInt()
    }

    val topPaint = Paint(Paint.ANTI_ALIAS_FLAG)  // ANTI_ALIAS_FLAG抗锯齿
    val bottomPaint = Paint(Paint.ANTI_ALIAS_FLAG)  // ANTI_ALIAS_FLAG抗锯齿
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (topLineStyle.enable) {
            canvas!!.drawLine(
                topLineStyle.leftMargin.toFloat(),
                0f,
                (measuredWidth - topLineStyle.rightMargin).toFloat(),
                topLineStyle.height.toFloat(),
                topPaint
            )
        }
        if (bottomLineStyle.enable) {
            canvas!!.drawLine(
                bottomLineStyle.leftMargin.toFloat(),
                height - bottomLineStyle.height.toFloat(),
                (measuredWidth - bottomLineStyle.rightMargin).toFloat(),
                height - bottomLineStyle.height.toFloat(),
                bottomPaint
            )
        }
    }

}