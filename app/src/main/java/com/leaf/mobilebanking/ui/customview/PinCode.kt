package com.leaf.mobilebanking.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.extensions.dp
import com.leaf.mobilebanking.extensions.vibrateMe
import kotlinx.coroutines.delay

class PinCode @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {
    private var inputCode = ""
    private val circles = mutableListOf<ImageView>()

    init {

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.orientation = VERTICAL
        weightSum = 3f

        val firstPiece = LinearLayout(context).apply {
            this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0).apply {
                weight = 1f
            }
            this.orientation = VERTICAL
            this.gravity = Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM

            val text = TextView(context).apply {
                this.layoutParams =
                    LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                        setMargins(16.dp.toInt(), 16.dp.toInt(), 16.dp.toInt(), 16.dp.toInt())
                    }
                typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                setTextColor(ContextCompat.getColor(context, R.color.black))
                textSize = 8.dp
                text = context.getString(R.string.enter_your_pin_code)
            }

            val linearCircles = LinearLayout(context).apply {
                this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 36.dp.toInt()).apply {
                    setMargins(16.dp.toInt())
                    setGravity(Gravity.CENTER)
                }

                for (i in 0..3) {
                    val image = ImageView(context).apply {
                        this.layoutParams =
                            LayoutParams(36.dp.toInt(), LayoutParams.MATCH_PARENT).apply {
                                setMargins(4.dp.toInt(), 0, 4.dp.toInt(), 0)
                                setImageResource(R.drawable.circle_empty)
                            }
                        this.isEnabled = false
                    }
                    circles.add(image)
                    this.addView(image)
                }
            }

            this.addView(text)
            this.addView(linearCircles)

        }

        val secondPiece = LinearLayout(context).apply {
            this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0).apply {
                weight = 2.3f
            }

            val linearPadVertical = LinearLayout(context).apply {
                this.layoutParams =
                    LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                        setMargins(0, 16.dp.toInt(), 0, 0)
                        setPadding(16.dp.toInt())
                        setGravity(Gravity.CENTER)
                        weightSum = 6f
                    }

                this.orientation = VERTICAL

                for (i in 1..9 step 3) {
                    val layout = numPad(i)
                    this.addView(layout)
                }
                this.addView(numPad())
            }

            this.addView(linearPadVertical)

        }

        addView(firstPiece)
        addView(secondPiece)

    }

    private fun numPad(): ViewGroup {

        val linearPadHorizontal = LinearLayout(context).apply {
            this.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, 0).apply {
                    setGravity(Gravity.CENTER_VERTICAL)
                    weight = 1f
                }

            val space = TextView(context).apply {
                this.layoutParams =
                    LayoutParams(0, LayoutParams.MATCH_PARENT).apply {
                        setPadding(16.dp.toInt())
                        textSize = 7.dp
                        weight = 1f
                        textAlignment = TEXT_ALIGNMENT_CENTER
                        setTextColor(ContextCompat.getColor(context, R.color.black))
                        gravity = Gravity.CENTER_HORIZONTAL
                        foreground =
                            getDrawable(context, R.drawable.clickable_effect_number_pad)
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                    }
                text = "Ok"
                this.setOnClickListener {
                }
            }

            val numPad = TextView(context).apply {
                this.layoutParams =
                    LayoutParams(0, LayoutParams.MATCH_PARENT).apply {
                        setPadding(16.dp.toInt())
                        textAlignment = TEXT_ALIGNMENT_CENTER
                        textSize = 10.dp
                        weight = 1f
                        gravity = Gravity.CENTER_HORIZONTAL
                        setTextColor(ContextCompat.getColor(context, R.color.black))
                        foreground =
                            getDrawable(context, R.drawable.clickable_effect_number_pad)
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                    }
                text = "0"


                this.setOnClickListener {
                    filling("0")
                }

            }

            val clearPad = ImageView(context).apply {
                this.layoutParams =
                    LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
                        setPadding(20.dp.toInt())
                        weight = 1f
                        gravity = Gravity.CENTER_HORIZONTAL
                        foreground =
                            getDrawable(context, R.drawable.clickable_effect_number_pad)
                        setImageResource(R.drawable.outline_backspace)
                    }

                this.setOnClickListener {
                    unFilling()
                }

                this.setOnLongClickListener {
                    unCirclingAll()
                    inputCode = ""
                    true
                }

            }

            this.apply {
                addView(space)
                addView(numPad)
                addView(clearPad)
            }

        }
        return linearPadHorizontal
    }

    private fun numPad(from: Int): ViewGroup {
        var inc = from

        val linearPadHorizontal = LinearLayout(context).apply {
            this.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, 0).apply {
                    setGravity(Gravity.CENTER_VERTICAL)
                    weight = 1f
                }
            for (i in 0..2) {
                val numPad = TextView(context).apply {
                    this.layoutParams =
                        LayoutParams(0, LayoutParams.MATCH_PARENT).apply {
                            setPadding(16.dp.toInt())
                            textAlignment = TEXT_ALIGNMENT_CENTER
                            textSize = 10.dp
                            weight = 1f
                            setTextColor(ContextCompat.getColor(context, R.color.black))
                            gravity = Gravity.CENTER_HORIZONTAL
                            foreground =
                                getDrawable(context, R.drawable.clickable_effect_number_pad)
                            typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                        }
                    text = (inc++).toString()

                    this.setOnClickListener {
                        filling(text.toString())
                    }

                }
                this.addView(numPad)
            }

        }
        return linearPadHorizontal
    }

    private fun filling(pad: String) {
        inputCode += pad
        circling()
        if (inputCode.length >= 4) {
            if (!inputCode.equals("1234")) {
                Log.d("DDD", inputCode)
                inputCode = ""
                unCirclingAll()
                vibrateMe(context)
            } else {
                Log.d("DDD", "Win")
            }
        }
    }

    private fun unFilling() {
        if (inputCode.isNotEmpty()) {
            inputCode.removeRange(inputCode.length - 1, inputCode.length)
            unCircling()
        }
        requestLayout()
    }

    private fun circling() {
        circles.forEach { circle ->
            if (!circle.isEnabled) {
                circle.setImageResource(R.drawable.circle_filled)
                circle.isEnabled = true
                return
            }
        }
    }

    private fun unCircling() {
        circles.reversed().forEach { circle ->
            if (circle.isEnabled) {
                circle.setImageResource(R.drawable.circle_empty)
                circle.isEnabled = false
                return
            }
        }
    }

    private fun unCirclingAll() {
        circles.reversed().forEach {
            it.setImageResource(R.drawable.circle_empty)
            it.isEnabled = false
        }
        requestLayout()
    }

}