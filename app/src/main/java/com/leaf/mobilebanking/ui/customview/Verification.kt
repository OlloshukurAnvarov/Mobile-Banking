package com.leaf.mobilebanking.ui.customview

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.extensions.dp

class Verification @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    val list = mutableListOf<TextInputEditText>()

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 60.dp.toInt())

        for (i in 1..6) {

            val textInput = TextInput(context).apply {
                layoutParams = LayoutParams(40.dp.toInt(), LayoutParams.MATCH_PARENT).apply {
                    marginEnd = 6.dp.toInt()
                    boxStrokeColor = getColor(context, R.color.green)

                }
            }

            if (i == 6)
                textInput.setImeDone()

            addView(textInput)
            list.add(textInput.view())

        }
    }

    fun pinCode(): String{
        var s = ""
        list.forEach {
            s += it.text
        }
        return s
    }

}

class TextInput @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attributeSet, defStyleAttr) {
    private val input = TextInputEditText(context)

    init {
        layoutParams = LayoutParams(40.dp.toInt(), LayoutParams.MATCH_PARENT).apply {
            boxBackgroundMode = BOX_BACKGROUND_OUTLINE
            boxStrokeColor = getColor(context, R.color.medium_gray)
            boxBackgroundColor = getColor(context, R.color.light_gray_box)
            setBoxCornerRadii(8.dp, 8.dp, 8.dp, 8.dp)
        }

        input.apply {
            layoutParams = LayoutParams(40.dp.toInt(), LayoutParams.WRAP_CONTENT).apply {
                setPaddingRelative(0, 16.dp.toInt(), 0, 0)
                isCursorVisible = false
                textAlignment = TEXT_ALIGNMENT_CENTER
                textSize = 10.dp
                typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                background = null
                setTextColor(getColor(context, R.color.black))
                inputType = InputType.TYPE_CLASS_NUMBER
                filters = arrayOf(InputFilter.LengthFilter(1))
                imeOptions = EditorInfo.IME_ACTION_NEXT
            }
        }

        addView(input)

    }

    fun view() = input
    fun setImeDone() {
        input.imeOptions = EditorInfo.IME_ACTION_DONE
        requestLayout()
    }

}