package com.leaf.mobilebanking.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.getFontOrThrow
import androidx.core.view.setPadding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.extensions.dp

class Button @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attributeSet, defStyleAttr) {

    private val text = TextView(context)
    private var appIsEnabled = false

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Button)
        appIsEnabled = typedArray.getBoolean(R.styleable.Button_isEnabled, false)
        val inputText = typedArray.getString(R.styleable.Button_text)
        val font = typedArray.getFontOrThrow(R.styleable.Button_fontText)
        typedArray.recycle()

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            radius = 8.dp
            cardElevation = 4.dp
        }

        text.apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setTextColor(context.resources.getColor(R.color.white))
                    gravity = Gravity.CENTER
                    textSize = 14f
                    isAllCaps = false
                    setPadding(12.dp.toInt())
                }
        }

        text.text = inputText
        text.typeface = font

        checking(appIsEnabled)

        addView(text)

    }

    fun view(view: (TextView) -> Unit) {
        view.invoke(text)
        requestLayout()
    }

    fun checking(isEnabled: Boolean) {
        appIsEnabled = isEnabled
        this.isEnabled = isEnabled
        if (appIsEnabled) {
            this.setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary_blue))
            text.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            this.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
            text.setTextColor(ContextCompat.getColor(context, R.color.medium_gray))
        }
        requestLayout()
    }
}